/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.header.doclit.client;

import junit.framework.TestCase;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Binding;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.bind.JAXBContext;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.sax.SAXSource;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.MessageFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.namespace.QName;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.*;

import client.dispatch.header.doclit.client.DispatchHeaderTest;
import testutil.ClientServerTestUtil;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;

public class TestCaseBase extends TestCase {
    private Service service;
    private Service serviceWithPorts;
    static final String BINDING_ID_STRING = SOAPBinding.SOAP11HTTP_BINDING;
    protected String endpointAddress;
    protected QName portQName;
    protected QName serviceQName;
    protected JAXBContext context;
    protected OutputStream log;

    public void init(String endpointAddress,
                     QName portQName, QName serviceQName,
                     JAXBContext context) {
        this.endpointAddress = endpointAddress;
        this.portQName = portQName;
        this.serviceQName = serviceQName;
        this.context = context;
    }

    protected void setLog(OutputStream log){
      this.log = log;
    }

    public TestCaseBase(String string) {
        super(string);
    }

    private void createService() {
        try {
        service = Service.create(serviceQName);
        //does service.addPort(portQName, bindingIdString, endpointAddress
        addPort(service, portQName, BINDING_ID_STRING, endpointAddress);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error creating service");
        }
    }


    protected void addPort( Service service, QName portQName, String bindingIdString, String endpointAddress) throws URISyntaxException {
          service.addPort(portQName, bindingIdString, endpointAddress);
    }

    private void createServiceWithWSDL(String endpointAddress, QName serviceQName) {
        //still need local transport for this
            URL serviceURL = null;
        try {
            serviceURL = new URL(endpointAddress + "?wsdl");
        } catch (MalformedURLException e) {
            fail("Error creating service with wsdl");
        }
        try {
            serviceWithPorts = Service.create(serviceURL,serviceQName);
        } catch (WebServiceException e) {
            e.printStackTrace();
        }
        }

    public Dispatch createDispatchJAXB() {
       return  service.createDispatch(portQName, context, Service.Mode.PAYLOAD);
    }

    public Dispatch createDispatchJAXBPortsAvailable() {
         return serviceWithPorts.createDispatch(portQName, context, Service.Mode.PAYLOAD);
    }

    public Dispatch createDispatchSource() {
        return service.createDispatch(portQName, Source.class, Service.Mode.PAYLOAD);
    }

    public Dispatch<SOAPMessage> createDispatchSOAPMessage() {
        return service.createDispatch(portQName, SOAPMessage.class, Service.Mode.MESSAGE);
    }

    public Dispatch<Source> createDispatchSOAPMessageSource() {
         return service.createDispatch(portQName, Source.class, Service.Mode.MESSAGE);

    }

    public Dispatch getDispatchJAXB() {
        createService();
        return createDispatchJAXB();
    }

    public Dispatch getDispatchSource() {
        createService();
        return createDispatchSource();
    }

    public Dispatch getDispatchSOAPMessage() {
        createService();
        return createDispatchSOAPMessage();
    }

    public Dispatch getDispatchSOAPMessageSource() {
        createService();
        return createDispatchSOAPMessageSource();
    }

    private Source makeSaxSource(String msg) {

            byte[] bytes = msg.getBytes();
            ByteArrayInputStream saxinputStream = new ByteArrayInputStream(bytes);
            InputSource inputSource = new InputSource(saxinputStream);
            return new SAXSource(inputSource);
        }

    protected Source makeStreamSource(String msg) {

        byte[] bytes = msg.getBytes();
        ByteArrayInputStream sinputStream = new ByteArrayInputStream(bytes);
        return new StreamSource(sinputStream);
    }

    private Source makeDOMSource(String msg) {

         byte[] bytes = msg.getBytes();
         ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

         return new DOMSource(createDOMNode(inputStream));
     }

    public Node createDOMNode(InputStream inputStream) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setValidating(false);
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            try {
                return builder.parse(inputStream);
            } catch (SAXException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } catch (ParserConfigurationException pce) {
            IllegalArgumentException iae = new IllegalArgumentException(pce.getMessage());
            iae.initCause(pce);
            throw iae;
        }
        return null;
    }

    SOAPMessage getSOAPMessage(Source msg) throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        message.getSOAPPart().setContent((Source) msg);
        message.saveChanges();
        return message;
    }

    private String sourceToXMLString(Source result) {

        String xmlResult = null;
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            OutputStream out = new ByteArrayOutputStream();
            StreamResult streamResult = new StreamResult();
            streamResult.setOutputStream(out);
            transformer.transform(result, streamResult);
            xmlResult = streamResult.getOutputStream().toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return xmlResult;
    }

    
}
