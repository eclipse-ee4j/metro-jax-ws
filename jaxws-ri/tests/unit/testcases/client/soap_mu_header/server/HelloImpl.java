/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.soap_mu_header.server;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.*;
import java.io.ByteArrayInputStream;

@WebServiceProvider(
    wsdlLocation="WEB-INF/wsdl/hello_literal.wsdl",
    targetNamespace="urn:test",
    serviceName="Hello",
    portName="HelloPort")
@ServiceMode(value= Service.Mode.MESSAGE)
public class HelloImpl implements Provider<SOAPMessage> {

    public SOAPMessage invoke(SOAPMessage m) {
        try {
            //String outheader = m.getSOAPHeader().getChildNodes().item(0).getTextContent();
            String soapbody = m.getSOAPBody().getChildNodes().item(0).getTextContent();
            if(soapbody.equals("extraMU")) {
                return getMessage2();
            } else {
                return getMessage1();
            }
        } catch (SOAPException e) {
            throw new WebServiceException(e);
        }
    }
    
    private SOAPMessage getMessage1() throws SOAPException {
        String msg = "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'>"+
            "<S:Header><ns1:Extra xmlns:ns1='urn:test:types' S:mustUnderstand=\"1\">outheader</ns1:Extra></S:Header>"+
            "<S:Body>"+
            "<ns1:HelloResponse xmlns:ns1='urn:test:types'>Hello Duke</ns1:HelloResponse>"+
	        "</S:Body>"+
            "</S:Envelope>";

        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        Source src = new StreamSource(new ByteArrayInputStream(msg.getBytes()));
        message.getSOAPPart().setContent(src);
        return message;
    }
    private SOAPMessage getMessage2() throws SOAPException {
        String msg = "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'>"+
            "<S:Header><ns1:Extra xmlns:ns1='urn:test:types' S:mustUnderstand=\"1\">outheader</ns1:Extra>"+
            "<ns1:ExtraExtra xmlns:ns1='urn:test:types' S:mustUnderstand=\"1\">outheader</ns1:ExtraExtra></S:Header>"+    
            "<S:Body>"+
            "<ns1:HelloResponse xmlns:ns1='urn:test:types'>Hello Duke</ns1:HelloResponse>"+
	        "</S:Body>"+
            "</S:Envelope>";

        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        Source src = new StreamSource(new ByteArrayInputStream(msg.getBytes()));
        message.getSOAPPart().setContent(src);
        return message;
    }
}
