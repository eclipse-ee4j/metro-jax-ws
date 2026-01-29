/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

import com.sun.xml.ws.api.handler.MessageHandler;
import com.sun.xml.ws.handler.HandlerException;

import javax.xml.namespace.QName;

import com.sun.xml.ws.resources.HandlerMessages;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.LogicalHandler;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import java.util.*;

/**
 * This class holds the handler information and roles on the Binding (mutable info in the binding).
 *
 * HandlerConfiguration is immutable, and a new object is created when the BindingImpl is created or User calls
 * Binding.setHandlerChain() or SOAPBinding.setRoles().
 *
 * During invocation in Stub.process(), snapshot of the handler configuration is set in Packet.handlerConfig. The
 * information in the HandlerConfiguration is used by MUPipe and HandlerTube implementations.
 * 
 * @author Rama Pulavarthi
 */
public class HandlerConfiguration {
    private final Set<String> roles;
    /**
     * This chain may contain both soap and logical handlers.
     */
    private final List<Handler> handlerChain;
    private final List<LogicalHandler> logicalHandlers;
    private final List<SOAPHandler> soapHandlers;
    private final List<MessageHandler> messageHandlers;
    private final Set<QName> handlerKnownHeaders;

    /**
     * @param roles               This contains the roles assumed by the Binding implementation.
     * @param handlerChain        This contains the handler chain set on the Binding
     */
    public HandlerConfiguration(Set<String> roles, List<Handler> handlerChain) {
        this.roles = roles;
        this.handlerChain = handlerChain;
        logicalHandlers = new ArrayList<>();
        soapHandlers = new ArrayList<>();
        messageHandlers = new ArrayList<>();
        Set<QName> modHandlerKnownHeaders = new HashSet<>();

        for (Handler handler : handlerChain) {
            if (handler instanceof LogicalHandler) {
                logicalHandlers.add((LogicalHandler) handler);
            } else if (handler instanceof SOAPHandler) {
                soapHandlers.add((SOAPHandler) handler);
                Set<QName> headers = ((SOAPHandler<?>) handler).getHeaders();
                if (headers != null) {
                    modHandlerKnownHeaders.addAll(headers);
                }
            } else if (handler instanceof MessageHandler) {
                messageHandlers.add((MessageHandler) handler);
                Set<QName> headers = ((MessageHandler<?>) handler).getHeaders();
                if (headers != null) {
                    modHandlerKnownHeaders.addAll(headers);
                }
            }else {
                throw new HandlerException(HandlerMessages.localizableHANDLER_NOT_VALID_TYPE(handler.getClass()));
            }
        }
        
        handlerKnownHeaders = Collections.unmodifiableSet(modHandlerKnownHeaders);
    }

    /**
     * This is called when roles as reset on binding using SOAPBinding#setRoles(), to save reparsing the handlers again.
     */
    public HandlerConfiguration(Set<String> roles, HandlerConfiguration oldConfig) {
        this.roles = roles;
        this.handlerChain = oldConfig.handlerChain;
        this.logicalHandlers = oldConfig.logicalHandlers;
        this.soapHandlers = oldConfig.soapHandlers;
        this.messageHandlers = oldConfig.messageHandlers;
        this.handlerKnownHeaders = oldConfig.handlerKnownHeaders;
    }

    public Set<String> getRoles() {
        return roles;
    }

    /**
     *
     * @return return a copy of handler chain
     */
    public List<Handler> getHandlerChain() {
        if(handlerChain == null)
            return Collections.emptyList();
        return new ArrayList<>(handlerChain);

    }

    public List<LogicalHandler> getLogicalHandlers() {
        return logicalHandlers;
    }

    public List<SOAPHandler> getSoapHandlers() {
        return soapHandlers;
    }

    public List<MessageHandler> getMessageHandlers() {
        return messageHandlers;
    }

    public Set<QName> getHandlerKnownHeaders() {
        return handlerKnownHeaders;
    }

}
