/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.handler_singlepipe.common;

import static fromwsdl.handler_singlepipe.common.TestConstants.*;

import jakarta.xml.ws.ProtocolException;
import jakarta.xml.ws.handler.LogicalHandler;
import jakarta.xml.ws.handler.LogicalMessageContext;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/*
 * This handler needs to be in the same package as the
 * client code so that it is compiled by the test harness
 * at the same time as the client code (so that it can
 * pick up the generated beans).
 */
public class LogicalTestHandler implements LogicalHandler<LogicalMessageContext> {
    HandlerTracker tracker;
    String name;
    HandlerUtil util;
    public boolean handleMessage(LogicalMessageContext messageContext) {
        if (HandlerTracker.VERBOSE_HANDLERS) {
            System.out.println("handler " + name + " (action: " +
                tracker.getHandlerAction(name) + ")");
        }
        tracker.registerCalledHandler(name);
        switch (tracker.getHandlerAction(name)) {
            case HA_RETURN_TRUE :
                return true;
            case HA_RETURN_FALSE :
                return false;
            case HA_RETURN_FALSE_INBOUND :
                return util.returnFalseInbound(messageContext, name);
            case HA_RETURN_FALSE_OUTBOUND :
                return util.returnFalseOutbound(messageContext, name);
            case HA_CHECK_SMC :
                throw new UnsupportedOperationException(name +
                    " can't check soap message context");
            case HA_ADD_ONE :
                return util.addIntToLogicalMessage(messageContext, 1);
            case HA_THROW_RUNTIME_EXCEPTION_OUTBOUND :
                return util.throwRuntimeException(
                    messageContext, name, OUTBOUND);
            case HA_THROW_RUNTIME_EXCEPTION_INBOUND :
                return util.throwRuntimeException(
                    messageContext, name, INBOUND);
            case HA_ADD_HEADER_OUTBOUND :
                throw new UnsupportedOperationException(name +
                    " can't check soap message context");
            case HA_CHECK_FOR_ADDED_HEADER_OUTBOUND :
                throw new UnsupportedOperationException(name +
                    " can't check soap message context");
            case HA_THROW_PROTOCOL_EXCEPTION_OUTBOUND :
                return util.throwSimpleProtocolException(messageContext,
                    name, OUTBOUND);
            case HA_THROW_PROTOCOL_EXCEPTION_INBOUND :
                return util.throwSimpleProtocolException(messageContext,
                    name, INBOUND);
            case HA_THROW_SOAP_FAULT_EXCEPTION_OUTBOUND :
                throw new UnsupportedOperationException(name +
                    " shouldn't throw SOAP exceptions");
            }
        throw new RuntimeException(name + " didn't understand action: " +
            tracker.getHandlerAction(name));
    }

    @PostConstruct
    public void init() {
        if (name == null || name.startsWith(CLIENT_PREFIX)) {
            tracker = HandlerTracker.getClientInstance();
        } else if (name.startsWith(SERVER_PREFIX)) {
            tracker = HandlerTracker.getServerInstance();
        } else {
            throw new RuntimeException("unrecognized prefix in name: " + name);
        }

        tracker.registerHandler(name);
        util = new HandlerUtil();
    }

    public String getName() {
        return name;
    }

    public void close(MessageContext messageContext) {
        tracker.registerClose(name);
    }

    @PreDestroy
    public void destroy() {
        tracker.registerDestroy(name);
    }

    // always register that handleFault was called and then check action
    public boolean handleFault(LogicalMessageContext messageContext) {
        tracker.registerCalledHandler(name + "_FAULT");
        switch (tracker.getHandleFaultAction(name)) {
            case HA_RETURN_TRUE :
                return true;
            case HA_RETURN_FALSE :
                return false;
            case HA_REGISTER_HANDLE_XYZ : // already registered
                return true;
            case HF_RETURN_FALSE :
                return false;
            case HF_GET_FAULT_IN_MESSAGE:
                return util.getFaultInMessage(messageContext);
            case HF_THROW_PROTOCOL_EXCEPTION :
                throw new ProtocolException(name +
                    " throwing ProtocolException from handleFault");
        }
        throw new RuntimeException(name + " didn't understand action: " +
            tracker.getHandleFaultAction(name));
    }
    
}
