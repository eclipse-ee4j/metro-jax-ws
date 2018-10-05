/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.asyncprovider_ns_800.server;

import com.sun.xml.ws.api.server.AsyncProvider;
import com.sun.xml.ws.api.server.AsyncProviderCallback;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.*;
import java.io.ByteArrayInputStream;

/**
 * @author Jitendra Kotamraju
 */
@WebServiceProvider(targetNamespace="urn:test", serviceName="Hello",
    portName="HelloAsyncPort")
@ServiceMode(value= Service.Mode.MESSAGE)
public class HelloAsyncImpl implements AsyncProvider<SOAPMessage> {

    public void invoke(SOAPMessage source, AsyncProviderCallback cbak, WebServiceContext ctxt) {
        try {
            cbak.send(getMessage());
        } catch(Exception e) {
            throw new WebServiceException("Endpoint failed", e);
        }
    }

    private SOAPMessage getMessage() throws SOAPException {
        String msg = "<SOAP-ENV:Envelope xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/'>"+
            "<SOAP-ENV:Body>"+
            "<helloObj xmlns:msgns='http://j2ee.netbeans.org/wsdl/QNameAssignment_WithStructuredPart' xmlns='http://xml.netbeans.org/schema/dataTypes.xsd'>"+
	        "<Element20 xmlns:ns0='http://j2ee.netbeans.org/wsdl/QNameAssignment_WithStructuredPart'>msgns:message</Element20>"+
            "</helloObj>"+
            "</SOAP-ENV:Body>"+
            "</SOAP-ENV:Envelope>";

        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        Source src = new StreamSource(new ByteArrayInputStream(msg.getBytes()));
        message.getSOAPPart().setContent(src);
        return message;
    }


}
