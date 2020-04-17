/*
 * Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.mime.simple_doclit.client;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.util.Set;
import java.util.Map;

import javax.xml.namespace.QName;

import jakarta.xml.ws.LogicalMessage;
import jakarta.xml.ws.handler.LogicalHandler;
import jakarta.xml.ws.handler.LogicalMessageContext;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.WebServiceException;

import jakarta.activation.DataHandler;

/**
 * Used to test message context properties on the client side.
 */
public class ClientLogicalHandler implements LogicalHandler<LogicalMessageContext> {

    /*
     * The method that does the testing. If a context property is
     * missing or invalid, throw a RuntimeException and it will
     * cause the test to fail.
     */
    public boolean handleMessage(LogicalMessageContext context) {
        LogicalMessage msg = context.getMessage();
//        try {
//            SOAPBody sb = sm.getSOAPBody();
//
//            Node n = sb.getFirstChild();
//            if(n != null){
//                if(!n.getLocalName().equals("picType") ||
//                        !n.getNamespaceURI().equals("http://www.ws-i.org/SampleApplications/SupplyChainManagement/2003-07/Catalog.xsd")){
//                    return true;
//                }
//            }else{
//                return true;
//            }
//        } catch (SOAPException e) {
//            throw new WebServiceException(e);
//        }
        if ((Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {
            System.out.println("Client handler processing echoImageWithInfo() request!");
            Map<String, DataHandler> attachs = (Map<String, DataHandler>) context.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);
            if(attachs.size() != 1)
                throw new WebServiceException("Expected 1 attacment, received :"+attachs.size());
        } else {
            System.out.println("Client handler processing echoImageWithInfo() response!");
            Map<String, DataHandler> attachs = (Map<String, DataHandler>) context.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);
            if(attachs.size() != 1)
                throw new WebServiceException("Expected 1 attacment, received :"+attachs.size());
        }
        return true;
    }
    
    
    // empty methods below here //
    public boolean handleFault(LogicalMessageContext context) {
        return true;
    }
    
    public void close(MessageContext context) {}
    
    
}
