/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.soap12.fault.client.handlers;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.ProtocolException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * Simple handler that throws an exception during handleMessage.
 */
public class ExceptionThrowingHandler implements SOAPHandler<SOAPMessageContext> {
    
    private String action;
    
    public ExceptionThrowingHandler(String action) {
        this.action = action;
    }
    
    public boolean handleMessage(SOAPMessageContext smc) {
        if (action.equals("ProtocolException")) {
            throw new ProtocolException();
        }
        return true;
    }
    
    /***** other handler methods stubbed out *****/
    public Set<QName> getHeaders() {
        return null;
    }

    public void close(MessageContext messageContext) {}

    public boolean handleFault(SOAPMessageContext smc) {
        return true;
    }
    
}
