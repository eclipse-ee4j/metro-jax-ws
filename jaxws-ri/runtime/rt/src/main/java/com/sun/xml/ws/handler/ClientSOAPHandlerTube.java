/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.handler;

import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.message.Attachment;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.client.HandlerConfiguration;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.message.DataHandlerAttachment;

import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.WebServiceException;
import jakarta.activation.DataHandler;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author WS Development Team
 */
public class ClientSOAPHandlerTube extends HandlerTube {

    private Set<String> roles;

    /**
     * Creates a new instance of SOAPHandlerTube
     */
    public ClientSOAPHandlerTube(WSBinding binding, WSDLPort port, Tube next) {
        super(next, port, binding);
        if (binding.getSOAPVersion() != null) {
            // SOAPHandlerTube should n't be used for bindings other than SOAP.
            // TODO: throw Exception
        }
    }

    // Handle to LogicalHandlerTube means its used on SERVER-SIDE

    /**
     * This constructor is used on client-side where, LogicalHandlerTube is created
     * first and then a SOAPHandlerTube is created with a handler to that
     * LogicalHandlerTube.
     * With this handle, SOAPHandlerTube can call LogicalHandlerTube.closeHandlers()
     */
    public ClientSOAPHandlerTube(WSBinding binding, Tube next, HandlerTube cousinTube) {
        super(next, cousinTube, binding);
    }

    /**
     * Copy constructor for {@link com.sun.xml.ws.api.pipe.Tube#copy(com.sun.xml.ws.api.pipe.TubeCloner)}.
     */
    private ClientSOAPHandlerTube(ClientSOAPHandlerTube that, TubeCloner cloner) {
        super(that, cloner);
    }

   @Override
   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
        return new ClientSOAPHandlerTube(this, cloner);
    }

    @Override
    void setUpProcessor() {
    	if (handlers == null) {
	        // Take a snapshot, User may change chain after invocation, Same chain
	        // should be used for the entire MEP
	        handlers = new ArrayList<>();
	        HandlerConfiguration handlerConfig = ((BindingImpl) getBinding()).getHandlerConfig();
	        List<SOAPHandler> soapSnapShot= handlerConfig.getSoapHandlers();
	        if (!soapSnapShot.isEmpty()) {
	            handlers.addAll(soapSnapShot);
	            roles = new HashSet<>();
	            roles.addAll(handlerConfig.getRoles());
	            processor = new SOAPHandlerProcessor(true, this, getBinding(), handlers);
	        }
    	}
    }

    @Override
    MessageUpdatableContext getContext(Packet packet) {
        SOAPMessageContextImpl context = new SOAPMessageContextImpl(getBinding(), packet,roles);
        return context;
    }

    @Override
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

    @Override
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

    @Override
    void closeHandlers(MessageContext mc) {
        closeClientsideHandlers(mc);

    }
}
