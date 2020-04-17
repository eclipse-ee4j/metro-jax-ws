/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.handler_simple.common;

import java.util.Map;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import javax.xml.namespace.QName;
import jakarta.xml.ws.ProtocolException;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.soap.*;

public class SOAPTestHandler implements SOAPHandler<SOAPMessageContext> {
    
    public static final int THROW_RUNTIME_EXCEPTION = -100;
    public static final int THROW_PROTOCOL_EXCEPTION = -101;
    public static final int THROW_SOAPFAULT_EXCEPTION = -102;
    
    @PostConstruct
    public void initMeNoArg() {
        // just here for debugging
    }
    
    public Set<QName> getHeaders() {
        return null;
    }
    
    public boolean handleMessage(SOAPMessageContext smc) {
        try {
            SOAPMessage message = smc.getMessage();
            SOAPBody body = message.getSOAPBody();
            
            SOAPElement paramElement =
                (SOAPElement) body.getFirstChild().getFirstChild();
            int number = Integer.parseInt(paramElement.getValue());
            
            if (number == THROW_RUNTIME_EXCEPTION) {
                throw new RuntimeException("EXPECTED EXCEPTION");
            } else if (number == THROW_PROTOCOL_EXCEPTION) {
                throw new ProtocolException("EXPECTED EXCEPTION");
            } else if (number == THROW_SOAPFAULT_EXCEPTION) {
                //todo
            }
            
            paramElement.setValue(String.valueOf(++number));
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }
    
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }
    
    @PreDestroy
    public void destroyMe() {
        // just here for debugging
    }
    
    public void close(MessageContext context) {}
    
}
