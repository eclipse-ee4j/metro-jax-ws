/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.handler;

import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.handler.MessageHandler;
import com.sun.xml.ws.api.message.Attachment;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.client.HandlerConfiguration;
import com.sun.xml.ws.message.DataHandlerAttachment;

import jakarta.activation.DataHandler;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.Handler;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Rama Pulavarthi
 */
public class ServerMessageHandlerTube extends HandlerTube{
    private SEIModel seiModel;
    private Set<String> roles;

    public ServerMessageHandlerTube(SEIModel seiModel, WSBinding binding, Tube next, HandlerTube cousinTube) {
        super(next, cousinTube, binding);
        this.seiModel = seiModel;
        setUpHandlersOnce();
    }

    /**
     * Copy constructor for {@link com.sun.xml.ws.api.pipe.Tube#copy(com.sun.xml.ws.api.pipe.TubeCloner)}.
     */
    private ServerMessageHandlerTube(ServerMessageHandlerTube that, TubeCloner cloner) {
        super(that, cloner);
        this.seiModel = that.seiModel;
        this.handlers = that.handlers;
        this.roles = that.roles;
    }

    private void setUpHandlersOnce() {
        handlers = new ArrayList<Handler>();
        HandlerConfiguration handlerConfig = ((BindingImpl) getBinding()).getHandlerConfig();
        List<MessageHandler> msgHandlersSnapShot= handlerConfig.getMessageHandlers();
        if (!msgHandlersSnapShot.isEmpty()) {
            handlers.addAll(msgHandlersSnapShot);
            roles = new HashSet<String>();
            roles.addAll(handlerConfig.getRoles());
        }
    }

    void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
        //Lets copy all the MessageContext.OUTBOUND_ATTACHMENT_PROPERTY to the message
        Map<String, DataHandler> atts = (Map<String, DataHandler>) context.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);        
        AttachmentSet attSet = context.packet.getMessage().getAttachments();
        for (Entry<String, DataHandler> entry : atts.entrySet()) {
            String cid = entry.getKey();
            if (attSet.get(cid) == null) { // Otherwise we would be adding attachments twice
                Attachment att = new DataHandlerAttachment(cid, atts.get(cid));
                attSet.add(att);
            }
        }

        try {
            //SERVER-SIDE
            processor.callHandlersResponse(HandlerProcessor.Direction.OUTBOUND, context, handleFault);

        } catch (WebServiceException wse) {
            //no rewrapping
            throw wse;
        } catch (RuntimeException re) {
            throw re;

        }
    }

    boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
        boolean handlerResult;
        try {
            //SERVER-SIDE
            handlerResult = processor.callHandlersRequest(HandlerProcessor.Direction.INBOUND, context, !isOneWay);

        } catch (RuntimeException re) {
            remedyActionTaken = true;
            throw re;

        }
        if (!handlerResult) {
            remedyActionTaken = true;
        }
        return handlerResult;
    }

    protected void resetProcessor() {
    	processor = null;
    }
    
    void setUpProcessor() {
        if(!handlers.isEmpty() && processor == null) {
            processor = new SOAPHandlerProcessor(false, this, getBinding(), handlers);
        }
    }

    void closeHandlers(MessageContext mc) {
        closeServersideHandlers(mc);

    }
    MessageUpdatableContext getContext(Packet packet) {
       MessageHandlerContextImpl context = new MessageHandlerContextImpl(seiModel, getBinding(), port, packet, roles);
       return context;
    }

    //should be overridden by DriverHandlerTubes
    @Override
    protected void initiateClosing(MessageContext mc) {
      close(mc);
      super.initiateClosing(mc);  
    }

   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
        return new ServerMessageHandlerTube(this, cloner);
    }
}
