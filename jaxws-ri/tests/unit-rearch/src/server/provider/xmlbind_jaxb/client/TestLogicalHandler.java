/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.xmlbind_jaxb.client;

import java.util.Map;

import javax.xml.bind.JAXBContext;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;

import org.w3c.dom.Node;

/*
 * This handler needs to be in the same package as the
 * client code so that it is compiled by the test harness
 * at the same time as the client code (so that it can
 * pick up the generated beans).
 */
public class TestLogicalHandler implements LogicalHandler<LogicalMessageContext> {
    
    public static enum HandleMode { SOURCE, JAXB }
    
    private HandleMode mode = HandleMode.SOURCE;
    
    public void setHandleMode(HandleMode mode) {
        this.mode = mode;
    }
    
    public boolean handleMessage(LogicalMessageContext context) {
        try {
            if (mode == HandleMode.SOURCE) {
                return handleMessageSource(context);
            } else if (mode == HandleMode.JAXB) {
                return handleMessageJAXB(context);
            } else {
                throw new Exception("unknown command");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private boolean handleMessageJAXB(LogicalMessageContext context)
        throws Exception {
        
        JAXBContext jaxbContext =
            JAXBContext.newInstance(ObjectFactory.class);
        LogicalMessage msg = context.getMessage();
        if (context.get(context.MESSAGE_OUTBOUND_PROPERTY).equals(
            Boolean.TRUE)) {
            Hello_Type hello = (Hello_Type) msg.getPayload(jaxbContext);
            hello.setArgument("hellofromhandler");
            msg.setPayload(hello, jaxbContext);
        } else {
            HelloResponse hello = (HelloResponse) msg.getPayload(jaxbContext);
            String arg = hello.getArgument();
            if (arg.equals("hellotohandler")) {
                hello.setArgument("handlerworks");
                msg.setPayload(hello, jaxbContext);
            } else {
                throw new RuntimeException("incorrect argument value in " +
                    "message. expected \"hellotohandler\", received: " +
                    arg);
            }
        }
        return true;
    }
    
    private boolean handleMessageSource(LogicalMessageContext context)
        throws Exception {
        
        LogicalMessage msg = context.getMessage();
        Source resultSource = msg.getPayload();
        if (resultSource == null) {
            throw new RuntimeException("payload is null in LogicalMessage");
        }
        DOMResult dResult = createDOMResult(resultSource);
        
        Node documentNode = dResult.getNode();
        Node requestResponseNode = documentNode.getFirstChild();
        Node textNode = requestResponseNode.getFirstChild().getFirstChild();
        if (context.get(context.MESSAGE_OUTBOUND_PROPERTY).equals(
            Boolean.TRUE)) {
            textNode.setNodeValue("hellofromhandler");
        } else {
            String arg = textNode.getNodeValue();
            if (arg.equals("hellotohandler")) {
                textNode.setNodeValue("handlerworks");
            } else {
                throw new RuntimeException("incorrect argument value in " +
                    "message. expected \"hellotohandler\", received: " +
                    arg);
            }
        }
        DOMSource source = new DOMSource(documentNode);
        msg.setPayload(source);
            
        int removeme = 0;
        return true;
    }
    
    private DOMResult createDOMResult(Source source) throws Exception {
        Transformer xFormer =
            TransformerFactory.newInstance().newTransformer();
        xFormer.setOutputProperty("omit-xml-declaration", "yes");
        DOMResult result = new DOMResult();
        xFormer.transform(source, result);
        return result;
    }
    
    /**** empty methods below ****/
    public void close(MessageContext context) {}

    public boolean handleFault(LogicalMessageContext context) {
        return true;
    }
    
}
