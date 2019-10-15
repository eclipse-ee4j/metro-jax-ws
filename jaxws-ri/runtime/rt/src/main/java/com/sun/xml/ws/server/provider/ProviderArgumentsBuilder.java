/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server.provider;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.fault.SOAPFaultBuilder;

import javax.xml.ws.soap.SOAPBinding;

/**
 * @author Jitendra Kotamraju
 */

public // TODO need this in the factory
abstract class ProviderArgumentsBuilder<T> {

    /**
     * Creates a fault {@link Message} from method invocation's exception
     */
    protected abstract Message getResponseMessage(Exception e);

    /**
     * Creates {@link Message} from method invocation's return value
     */
    protected Packet getResponse(Packet request, Exception e, WSDLPort port, WSBinding binding) {
        Message message = getResponseMessage(e);
        Packet response = request.createServerResponse(message,port,null,binding);
        return response;
    }

    /**
     * Binds {@link com.sun.xml.ws.api.message.Message} to method invocation parameter
     * @param packet
     */
    /*protected*/ public abstract T getParameter(Packet packet); // TODO public for DISI pluggable Provider

    protected abstract Message getResponseMessage(T returnValue);

    /**
     * Creates {@link Packet} from method invocation's return value
     */
    protected Packet getResponse(Packet request, @Nullable T returnValue, WSDLPort port, WSBinding binding) {
        Message message = null;
        if (returnValue != null) {
            message = getResponseMessage(returnValue);
        }
        Packet response = request.createServerResponse(message,port,null,binding);
        return response;
    }

    public static ProviderArgumentsBuilder<?> create(ProviderEndpointModel model, WSBinding binding) {
    	if (model.datatype == Packet.class)
    		return new PacketProviderArgumentsBuilder(binding.getSOAPVersion());
        return (binding instanceof SOAPBinding) ? SOAPProviderArgumentBuilder.create(model, binding.getSOAPVersion())
                : XMLProviderArgumentBuilder.createBuilder(model, binding);
    }
    
    private static class PacketProviderArgumentsBuilder extends ProviderArgumentsBuilder<Packet> {
                private final SOAPVersion soapVersion;

                public PacketProviderArgumentsBuilder(SOAPVersion soapVersion) {
                    this.soapVersion = soapVersion;
                }

		@Override
		protected Message getResponseMessage(Exception e) {
		    // Will be called by AsyncProviderCallbackImpl.sendError
		    return SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, e);
		}

		@Override
		/*protected*/ public Packet getParameter(Packet packet) {
			return packet;
		}

		@Override
		protected Message getResponseMessage(Packet returnValue) {
			// Should never be called
			throw new IllegalStateException();
		}

		@Override
	    protected Packet getResponse(Packet request, @Nullable Packet returnValue, WSDLPort port, WSBinding binding) {
			return returnValue;
	    }
    }
}
