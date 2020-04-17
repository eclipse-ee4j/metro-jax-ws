/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.wsdl_hello_lit_oneway.client;


import junit.framework.TestCase;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import testutil.ClientServerTestUtil;

import jakarta.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.MessageFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import client.common.client.DispatchTestCase;

/**
 * @author JAX-RPC RI Development Team
 */
public class DispatchHelloLiteralTest extends DispatchTestCase {
    private String helloSM = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><Hello xmlns=\"urn:test:types\"><argument>foo</argument><extra>Test </extra></Hello></soapenv:Body></soapenv:Envelope>";
    //private String helloSM = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><Hello xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></Hello></soapenv:Body></soapenv:Envelope>";
    private String helloMsg =
            "<Hello xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></Hello>";
    private String voidMsg =
            "<VoidTest xmlns=\"urn:test:types\"/>";
    private String voidSM = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><VoidTest xmlns=\"urn:test:types\"/></soapenv:Body></soapenv:Envelope>";
    private QName serviceQName = new QName("urn:test", "Hello");
    private QName portQName = new QName("urn:test", "HelloPort");
    ;
    private String bindingIdString = SOAPBinding.SOAP11HTTP_BINDING;
    private String endpointAddress;
    private static final String ENDPOINT_IMPL = "client.dispatch.wsdl_hello_lit_oneway.server.Hello_PortType_Impl";
    private Service service;
    private Dispatch dispatch;


    public DispatchHelloLiteralTest(String name) {
        super(name);

        // we'll fix the test harness correctly later,
        // so that test code won't have to hard code any endpoint address nor transport,
        // but for now let's just support local and HTTP to make unit tests happier.
        // this is not a good code, but it's just a bandaid solutino that works for now.
        if(ClientServerTestUtil.useLocal())
            endpointAddress = "local://"+new File(System.getProperty("tempdir")).getAbsolutePath().replace('\\','/')+'?'+portQName.getLocalPart();
        else
            endpointAddress = "http://localhost:8080/jaxrpc-client_dispatch_wsdl_hello_lit_oneway/hello";
    }

    private void createService() {

        service = Service.create(serviceQName);
        //does service.addPort(portQName, bindingIdString, endpointAddress
        addPort(service, portQName, bindingIdString, endpointAddress);
    }

    private static jakarta.xml.bind.JAXBContext createJAXBContext() {
        try {
            return jakarta.xml.bind.JAXBContext.newInstance(client.dispatch.wsdl_hello_lit_oneway.client.ObjectFactory.class);
        } catch (jakarta.xml.bind.JAXBException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }


    private Dispatch createDispatchJAXB() {

        JAXBContext context = createJAXBContext();
        return service.createDispatch(portQName, context, Service.Mode.PAYLOAD);
    }

    private Dispatch createDispatchSource() {
        return service.createDispatch(portQName, Source.class, Service.Mode.PAYLOAD);
    }

    private Dispatch createDispatchSOAPMessage() {

        return service.createDispatch(portQName, SOAPMessage.class,
                Service.Mode.MESSAGE);
    }

    private Dispatch createDispatchSOAPMessageSource() {

        return service.createDispatch(portQName, Source.class,
                Service.Mode.MESSAGE);
    }


    private Dispatch getDispatchSOAPMessage() {
        createService();
        return createDispatchSOAPMessage();
    }

    private Dispatch getDispatchSOAPMessageSource() {
        createService();
        return createDispatchSOAPMessageSource();
    }

    private Dispatch getDispatchJAXB() {
        createService();
        return createDispatchJAXB();
    }

    private Dispatch getDispatchSource() {
        createService();
        return createDispatchSource();
    }

    //TODo: add assertion for oneway check to all tests
    public void testHelloOnewayRequestJAXB() {

        JAXBContext jc = null;
        Hello_Type hello = null;

        try {
            jc = createJAXBContext();
            hello = new Hello_Type();
        } catch (Exception jbe) {
            jbe.printStackTrace();
        }

        try {
            hello.setExtra("Test ");
            hello.setArgument("Dispatch ");

            Dispatch dispatch = getDispatchJAXB();
            dispatch.invokeOneWay(hello);

        } catch (Exception e) {
            fail("testHelloOnewayRequestJAXB with exception " + e.getMessage());
        }
    }


    public void testHelloOnewayRequestXML() {

        Dispatch dispatch = getDispatchSource();
        Collection<Source> sourceList = makeMsgSource(helloMsg);

        for (Iterator iter = sourceList.iterator(); iter.hasNext();) {
            try {
                Object sourceObject = iter.next();
                dispatch.invokeOneWay(sourceObject);
            } catch (Exception e) {
                fail("testHelloOnewayRequestXML with exception " + e.getMessage());
            }
        }
    }

    public void testVoidOnewayRequestJAXB() {

        JAXBContext jc = null;
        VoidTest voidTest = null;

        try {
            jc = createJAXBContext();
            voidTest = new VoidTest();
        } catch (Exception jbe) {
            jbe.printStackTrace();
        }

        Dispatch dispatch = getDispatchJAXB();

        try {
            dispatch.invokeOneWay(voidTest);
        } catch (Exception e) {
            fail("testVoidOnewayRequestJAXB with exception " + e.getMessage());
        }
    }

    public void testVoidOnewayRequestXML() {

        Dispatch dispatch = getDispatchSource();
        Collection<Source> sourceList = makeMsgSource(voidMsg);

        for (Iterator iter = sourceList.iterator(); iter.hasNext();) {
            try {
                Object sourceObject = iter.next();
                dispatch.invokeOneWay(sourceObject);
            } catch (Exception e) {
                fail("testVoidOnewayRequestXML with exception " + e.getMessage());
            }
        }
    }

    public void testHelloRequestOnewaySOAPMessage() throws Exception {

        try {
            Dispatch dispatch = getDispatchSOAPMessage();
            assertTrue(dispatch != null);
            assertTrue(dispatch instanceof com.sun.xml.ws.client.dispatch.SOAPMessageDispatch);

            Source source = makeStreamSource(helloSM);
            SOAPMessage message = getSOAPMessage(source);
            dispatch.invokeOneWay(message);
        } catch (Exception e) {
            fail("testHelloRequestOnewaySOAPMessage with exception " + e.getMessage());
        }
    }

    public void testHelloRequestOnewaySOAPMessageSource() throws Exception {

        Dispatch dispatch = getDispatchSOAPMessageSource();
        Source source = makeStreamSource(helloSM);
        dispatch.invokeOneWay(source);
    }

}
