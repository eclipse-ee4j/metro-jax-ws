/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.handler;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.handler.MessageHandler;
import com.sun.xml.ws.api.message.Attachment;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.client.HandlerConfiguration;
import com.sun.xml.ws.message.DataHandlerAttachment;

import javax.activation.DataHandler;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.Handler;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Rama Pulavarthi
 */
public class ClientMessageHandlerTube extends HandlerTube {
    private SEIModel seiModel;
    private Set<String> roles;
    
    /**
     * Creates a new instance of MessageHandlerTube
     */
    public ClientMessageHandlerTube(@Nullable SEIModel seiModel, WSBinding binding, WSDLPort port, Tube next) {
        super(next, port, binding);
        this.seiModel = seiModel;
    }

    /**
     * Copy constructor for {@link com.sun.xml.ws.api.pipe.Tube#copy(com.sun.xml.ws.api.pipe.TubeCloner)}.
     */
    private ClientMessageHandlerTube(ClientMessageHandlerTube that, TubeCloner cloner) {
        super(that, cloner);
        this.seiModel = that.seiModel;
    }

    public AbstractFilterTubeImpl copy(TubeCloner cloner) {
        return new ClientMessageHandlerTube(this, cloner);
    }
    
    void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
        try {
            //CLIENT-SIDE
            processor.callHandlersResponse(HandlerProcessor.Direction.INBOUND, context, handleFault);

        } catch (WebServiceException wse) {
            //no rewrapping
            throw wse;
        } catch (RuntimeException re) {
            throw new WebServiceException(re);
        }
    }

    boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
        boolean handlerResult;

        //Lets copy all the MessageContext.OUTBOUND_ATTACHMENT_PROPERTY to the message
        Map<String, DataHandler> atts = (Map<String, DataHandler>) context.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);
        AttachmentSet attSet = context.packet.getMessage().getAttachments();
        for (Entry<String, DataHandler> entry : atts.entrySet()) {
            String cid = entry.getKey();
            if (attSet.get(cid) == null) {  // Otherwise we would be adding attachments twice
                Attachment att = new DataHandlerAttachment(cid, atts.get(cid));
                attSet.add(att);
            }
        }

        try {
            //CLIENT-SIDE
            handlerResult = processor.callHandlersRequest(HandlerProcessor.Direction.OUTBOUND, context, !isOneWay);
        } catch (WebServiceException wse) {
            remedyActionTaken = true;
            //no rewrapping
            throw wse;
        } catch (RuntimeException re) {
            remedyActionTaken = true;

            throw new WebServiceException(re);

        }
        if (!handlerResult) {
            remedyActionTaken = true;
        }
        return handlerResult;
    }

    void closeHandlers(MessageContext mc) {
        closeClientsideHandlers(mc);

    }
    
    void setUpProcessor() {
    	if (handlers == null) {
	        // Take a snapshot, User may change chain after invocation, Same chain
	        // should be used for the entire MEP
	        handlers = new ArrayList<Handler>();
	        HandlerConfiguration handlerConfig = ((BindingImpl) getBinding()).getHandlerConfig();
	        List<MessageHandler> msgHandlersSnapShot= handlerConfig.getMessageHandlers();
	        if (!msgHandlersSnapShot.isEmpty()) {
	            handlers.addAll(msgHandlersSnapShot);
	            roles = new HashSet<String>();
	            roles.addAll(handlerConfig.getRoles());
	            processor = new SOAPHandlerProcessor(true, this, getBinding(), handlers);
	        }
    	}
    }



    MessageUpdatableContext getContext(Packet p) {
        MessageHandlerContextImpl context = new MessageHandlerContextImpl(seiModel, getBinding(), port, p, roles);
        return context;
    }


}
