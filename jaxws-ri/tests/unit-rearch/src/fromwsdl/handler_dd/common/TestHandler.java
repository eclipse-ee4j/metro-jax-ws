/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.handler_dd.common;

import java.util.Set;

import javax.xml.namespace.QName;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.soap.*;

public class TestHandler implements SOAPHandler<SOAPMessageContext> {
    
    public Set<QName> getHeaders() {
        return null;
    }
    
    public boolean handleMessage(SOAPMessageContext context) {
        try {
            SOAPMessageContext smc = (SOAPMessageContext) context;
            SOAPMessage message = smc.getMessage();
            SOAPBody body = message.getSOAPBody();
            
            SOAPElement paramElement =
                (SOAPElement) body.getFirstChild().getFirstChild();
            int number = Integer.parseInt(paramElement.getValue());
            paramElement.setValue(String.valueOf(++number));
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }
    
    public void close(MessageContext context) {}
    
}
