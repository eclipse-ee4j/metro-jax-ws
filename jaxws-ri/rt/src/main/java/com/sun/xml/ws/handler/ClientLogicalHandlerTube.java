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

import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.model.AbstractSEIModelImpl;
import com.sun.xml.ws.spi.db.BindingContext;

import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.WebServiceException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author WS Development Team
 */
public class ClientLogicalHandlerTube extends HandlerTube {

    private SEIModel seiModel;

    /**
     * Creates a new instance of LogicalHandlerTube
     */
    public ClientLogicalHandlerTube(WSBinding binding, SEIModel seiModel, WSDLPort port, Tube next) {
        super(next, port, binding);
        this.seiModel = seiModel;
    }

    /**
     * This constructor is used on client-side where, SOAPHandlerTube is created
     * first and then a LogicalHandlerTube is created with a handler to that
     * SOAPHandlerTube.
     * With this handle, LogicalHandlerTube can call
     * SOAPHandlerTube.closeHandlers()
     */
    public ClientLogicalHandlerTube(WSBinding binding, SEIModel seiModel, Tube next, HandlerTube cousinTube) {
        super(next, cousinTube, binding);
        this.seiModel = seiModel;
    }

    /**
     * Copy constructor for {@link com.sun.xml.ws.api.pipe.Tube#copy(com.sun.xml.ws.api.pipe.TubeCloner)}.
     */

    private ClientLogicalHandlerTube(ClientLogicalHandlerTube that, TubeCloner cloner) {
        super(that, cloner);
        this.seiModel = that.seiModel;
    }

    //should be overridden by DriverHandlerTubes
    @Override
    protected void initiateClosing(MessageContext mc) {
      close(mc);
      super.initiateClosing(mc);  
    }

    public AbstractFilterTubeImpl copy(TubeCloner cloner) {
        return new ClientLogicalHandlerTube(this, cloner);
    }

    void setUpProcessor() {
    	if (handlers == null) {
	        // Take a snapshot, User may change chain after invocation, Same chain
	        // should be used for the entire MEP
	        handlers = new ArrayList<Handler>();
	        WSBinding binding = getBinding();
	        List<LogicalHandler> logicalSnapShot= ((BindingImpl) binding).getHandlerConfig().getLogicalHandlers();
	        if (!logicalSnapShot.isEmpty()) {
	            handlers.addAll(logicalSnapShot);
	            if (binding.getSOAPVersion() == null) {
	                processor = new XMLHandlerProcessor(this, binding,
	                        handlers);
	            } else {
	                processor = new SOAPHandlerProcessor(true, this, binding,
	                        handlers);
	            }
	        }
    	}
    }


    MessageUpdatableContext getContext(Packet packet) {
        return new LogicalMessageContextImpl(getBinding(), getBindingContext(), packet);
    }    

    private BindingContext getBindingContext() {
        return (seiModel!= null && seiModel instanceof AbstractSEIModelImpl) ?
        	((AbstractSEIModelImpl)seiModel).getBindingContext() : null;
	}

	boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {

        boolean handlerResult;
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
    void closeHandlers(MessageContext mc) {
        closeClientsideHandlers(mc);

    }
}
