/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.wsdl_hello_lit.client.handlers;

import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import jakarta.xml.soap.*;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;

/**
 * Simple handler to add headers to an outgoing message.
 */
public class MUHelperHandler implements SOAPHandler<SOAPMessageContext> {

    private QName headerToAdd;
    private String roleToTarget;
    
    /*
     * This qname will be set on the outgoing message with
     * MU set to true, targeted at the role.
     */
    public void setMUHeader(QName qname, String role) {
        headerToAdd = qname;
        roleToTarget = role;
    }

    /*
     * The real work happens here.
     */
    public boolean handleMessage(SOAPMessageContext smc) {
        if (smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY) == Boolean.FALSE ||
            headerToAdd == null) {
            return true;
        }
        
        try {
            SOAPMessage message = smc.getMessage();
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            SOAPHeader header = envelope.getHeader();
            if (header == null) { // should be null originally
                header = envelope.addHeader();
            }
            SOAPHeaderElement element = header.addHeaderElement(headerToAdd);
            element.setActor(roleToTarget);
            element.setMustUnderstand(true);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /***** other handler methods stubbed out *****/
    public Set<QName> getHeaders() {
        return null;
    }

    public void close(MessageContext messageContext) {
    }

    public boolean handleFault(SOAPMessageContext smc) {
        return true;
    }

}
