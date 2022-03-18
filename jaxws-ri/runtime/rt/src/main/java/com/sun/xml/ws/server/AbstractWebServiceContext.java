/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.api.server.WSWebServiceContext;
import com.sun.xml.ws.server.provider.AsyncProviderInvokerTube;
import com.sun.istack.NotNull;
import org.w3c.dom.Element;

import jakarta.xml.ws.EndpointReference;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.wsaddressing.W3CEndpointReference;
import java.security.Principal;

/**
 * Partial {@link WSWebServiceContext} implementation. This implementation depends on
 * {@link Packet} and concrete implementations provide it via
 * {@link #getRequestPacket()}.
 *
 * @see InvokerTube
 * @see AsyncProviderInvokerTube
 *
 * @author Jitendra Kotamraju
 */
public abstract class AbstractWebServiceContext implements WSWebServiceContext {

    private final WSEndpoint endpoint;

    public AbstractWebServiceContext(@NotNull WSEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public MessageContext getMessageContext() {
        Packet packet = getRequestPacket();
        if (packet == null) {
            throw new IllegalStateException("getMessageContext() can only be called while servicing a request");
        }
        return new EndpointMessageContextImpl(packet);
    }

    @Override
    public Principal getUserPrincipal() {
        Packet packet = getRequestPacket();
        if (packet == null) {
            throw new IllegalStateException("getUserPrincipal() can only be called while servicing a request");
        }
        return packet.webServiceContextDelegate.getUserPrincipal(packet);
    }

    @Override
    public boolean isUserInRole(String role) {
        Packet packet = getRequestPacket();
        if (packet == null) {
            throw new IllegalStateException("isUserInRole() can only be called while servicing a request");
        }
        return packet.webServiceContextDelegate.isUserInRole(packet,role);
    }

    @Override
    public EndpointReference getEndpointReference(Element...referenceParameters) {
        return getEndpointReference(W3CEndpointReference.class, referenceParameters);
    }

    @Override
    public <T extends EndpointReference> T getEndpointReference(Class<T> clazz, Element...referenceParameters) {
        Packet packet = getRequestPacket();
        if (packet == null) {
            throw new IllegalStateException("getEndpointReference() can only be called while servicing a request");
        }
        String address = packet.webServiceContextDelegate.getEPRAddress(packet, endpoint);
        String wsdlAddress = null;
        if(endpoint.getServiceDefinition() != null) {
            wsdlAddress = packet.webServiceContextDelegate.getWSDLAddress(packet,endpoint);
        }
        return clazz.cast(endpoint.getEndpointReference(clazz,address,wsdlAddress, referenceParameters));
    }
    
}
