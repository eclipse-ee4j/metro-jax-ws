/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.xmlbind_handler.common;

import java.io.ByteArrayOutputStream;

import javax.xml.namespace.QName;
import javax.xml.ws.ProtocolException;
import javax.xml.ws.http.HTTPException;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.LogicalMessage;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

public class TestHandler implements LogicalHandler<LogicalMessageContext> {
    
    public static final int THROW_HTTP_EXCEPTION = -100;
    public static final int THROW_RUNTIME_EXCEPTION = -101;
    public static final int THROW_PROTOCOL_EXCEPTION = -102;
    
    public boolean handleMessage(LogicalMessageContext context) {
        try {
            LogicalMessage message = context.getMessage();
            Source msgSource = message.getPayload();
            Source newMessage = incrementArgument(msgSource);
            message.setPayload(newMessage);
            //verifySource(msgSource); // source already read
            return true;
        } catch (TransformerException e) {
            System.err.println("handler received: " + e);
            throw new HTTPException(502);
        }
    }

    private void verifySource(Source source) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer trans = factory.newTransformer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        trans.transform(source, new StreamResult(baos));
        baos.close();
    }
    
    private Source incrementArgument(Source source)
        throws TransformerException {
        Transformer xFormer =
            TransformerFactory.newInstance().newTransformer();
        xFormer.setOutputProperty("omit-xml-declaration", "yes");
        DOMResult dResult = new DOMResult();
        xFormer.transform(source, dResult);
        Node documentNode = dResult.getNode();
        Node envelopeNode = documentNode.getFirstChild();
        Node requestResponseNode = envelopeNode.getLastChild().getFirstChild();
        Node textNode = requestResponseNode.getFirstChild().getFirstChild();
        int orig = Integer.parseInt(textNode.getNodeValue());
        
        // check for error tests
        if (orig == THROW_HTTP_EXCEPTION) {
            throw new HTTPException(500);
        } else if (orig == THROW_RUNTIME_EXCEPTION) {
            throw new RuntimeException("EXPECTED EXCEPTION");
        } else if (orig == THROW_PROTOCOL_EXCEPTION) {
            throw new ProtocolException("TEST EXCEPTION FROM HANDLER");
        }
        
        textNode.setNodeValue(String.valueOf(++orig));
        return new DOMSource(documentNode);
    }
    
    public boolean handleFault(LogicalMessageContext context) {
        return true;
    }
    
    public void close(MessageContext context) {}
    
}
