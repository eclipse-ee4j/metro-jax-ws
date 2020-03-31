/*
 * Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.wsdl_hello_lit_context.server;

import java.util.Iterator;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.ws.Service.Mode;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.namespace.QName;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.ServiceMode;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import javax.annotation.Resource;

/**
 * @author Jitendra Kotamraju 
 */
@WebServiceProvider(targetNamespace="urn:test", portName="HelloMsgPort", serviceName="HelloMsg")
@ServiceMode(value=Service.Mode.MESSAGE)
public class SOAPMsgProvider implements Provider<SOAPMessage> {

    public SOAPMessage invoke(SOAPMessage msg) {
        try {
            // keeping white space in the string is intentional
            String content = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body>  <VoidTestResponse xmlns=\"urn:test:types\"></VoidTestResponse></soapenv:Body></soapenv:Envelope>";
            Source source = new StreamSource(new ByteArrayInputStream(content.getBytes()));
            MessageFactory fact = MessageFactory.newInstance();
            SOAPMessage soap = fact.createMessage();
            soap.getSOAPPart().setContent(source);
            soap.getMimeHeaders().addHeader("foo", "bar");
            return soap;
        } catch(Exception e) {
            throw new WebServiceException(e);
        }
    }

}
