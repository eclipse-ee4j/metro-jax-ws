/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.soap12.hello.client;

import junit.framework.TestCase;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import testutil.ClientServerTestUtil;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.SOAPBinding;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import client.common.client.DispatchTestCase;

/**
 * @author JAX-RPC RI Development Team
 */
public class DispatchHelloLiteralTest extends DispatchTestCase {

    //private String helloSM= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><Hello xmlns=\"urn:test:types\"><argument>Dispatch </argument><extra>Test </extra></Hello></soapenv:Body></soapenv:Envelope>";
    private String helloResponseSM = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><HelloResponse xmlns=\"urn:test:types\"><argument>Dispatch </argument><extra>Test </extra></HelloResponse></soapenv:Body></soapenv:Envelope>";
    private String helloSM = "<soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\"><soapenv:Body><Hello xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></Hello></soapenv:Body></soapenv:Envelope>";
    private String helloMsg =
            "<Hello xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></Hello>";
    private String bad3helloMsg = "<Hello xmlns=\"urn:test\"><argument>foo</argument><extra>bar</extra></Hello>";
    //private String bad2helloMsg = "<Hello xmlns=\"urn:test:types\"><argument></argument><argument></extra></Hello>";
    //should give RemoteException with jaxbException cause
    private String badhelloMsg = "<Hello xmlns=\"urn:test:types\">.....<bar></Hello>";
    private String helloResponse = "<HelloResponse xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></HelloResponse>";
    private String helloResponseRequest = "<HelloResponse xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></HelloResponse>";

    private String voidMsg = "</VoidTest>";
    //should give RemoteException with jaxbException cause
    private String badvoidMsg = "<VoidTest xmlns=\"\"urn:test\"/>";
    private String voidResponse = "<VoidTestResponse xmlns=\"urn:test:types\"/>";

    //private String bugTest = <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><soapenv:Body><GetUserByID1Response xmlns="http://example.com/auctiontraq/wsdl/rpclit"><Body xmlns="" userRating="5"><ns1:name xmlns:ns1="http://example.com/auctiontraq/schemas/rpclit"><ns1:fname>Rama</ns1:fname><ns1:lname>Pulavarthi</ns1:lname></ns1:name><ns2:dob xmlns:ns2="http://example.com/auctiontraq/schemas/rpclit">1978-08-30-07:00</ns2:dob><ns3:age xmlns:ns3="http://example.com/auctiontraq/schemas/rpclit">25</ns3:age><ns4:ssn xmlns:ns4="http://example.com/auctiontraq/schemas/rpclit">123 456 7890</ns4:ssn><ns5:address xmlns:ns5="http://example.com/auctiontraq/schemas/rpclit"><ns5:street>988 Henderson Ave</ns5:street><ns5:city>Sunnyvale</ns5:city><ns5:state>CA</ns5:state><ns5:country>USA</ns5:country><ns5:zipcode>94086</ns5:zipcode></ns5:address><ns6:email xmlns:ns6="http://example.com/auctiontraq/schemas/rpclit">rama.pulavarthi@sun.com</ns6:email><ns7:password xmlns:ns7="http://example.com/auctiontraq/schemas/rpclit">cGFzc3dvcmQ=</ns7:password><ns8:memberSince xmlns:ns8="http://example.com/auctiontraq/schemas/rpclit">2000-08-24-07:00</ns8:memberSince><ns9:authenticated xmlns:ns9="http://example.com/auctiontraq/schemas/rpclit">true</ns9:authenticated></Body></GetUserByID1Response></soapenv:Body></soapenv:Envelope>
    private String bugTest = "<GetUserByID1Response xmlns=\"http://example.com/auctiontraq/wsdl/rpclit\"><Body xmlns=\"\" userRating=\"5\"><ns1:name xmlns:ns1=\"http://example.com/auctiontraq/schemas/rpclit\"><ns1:fname>Rama</ns1:fname><ns1:lname>Pulavarthi</ns1:lname></ns1:name><ns2:dob xmlns:ns2=\"http://example.com/auctiontraq/schemas/rpclit\">1978-08-30-07:00</ns2:dob><ns3:age xmlns:ns3=\"http://example.com/auctiontraq/schemas/rpclit\">25</ns3:age><ns4:ssn xmlns:ns4=\"http://example.com/auctiontraq/schemas/rpclit\">123 456 7890</ns4:ssn><ns5:address xmlns:ns5=\"http://example.com/auctiontraq/schemas/rpclit\"><ns5:street>988 Henderson Ave</ns5:street><ns5:city>Sunnyvale</ns5:city><ns5:state>CA</ns5:state><ns5:country>USA</ns5:country><ns5:zipcode>94086</ns5:zipcode></ns5:address><ns6:email xmlns:ns6=\"http://example.com/auctiontraq/schemas/rpclit\">rama.pulavarthi@sun.com</ns6:email><ns7:password xmlns:ns7=\"http://example.com/auctiontraq/schemas/rpclit\">cGFzc3dvcmQ=</ns7:password><ns8:memberSince xmlns:ns8=\"http://example.com/auctiontraq/schemas/rpclit\">2000-08-24-07:00</ns8:memberSince><ns9:authenticated xmlns:ns9=\"http://example.com/auctiontraq/schemas/rpclit\">true</ns9:authenticated></Body></GetUserByID1Response>";
    private String ramaTest = "<Person xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://example.com/auctiontraq/schemas/doclit\" xsi:type=\"UserType\" userRating=\"5\"><name><fname>Rama</fname><lname>Pulavarthi</lname></name><ssn>123</ssn><ssn>456</ssn><ssn>7890</ssn><email>rama.pulavarthi@sun.com</email><password>cGFzc3dvcmQ=</password><memberSince>2000-08-24T11:05:35.000-07:00</memberSince><authenticated>true</authenticated></Person>";

    private QName serviceQName = new QName("urn:test", "Hello");
    private QName portQName = new QName("urn:test", "HelloPort");
    ;

    private String bindingIdString = SOAPBinding.SOAP12HTTP_BINDING;
    private String endpointAddress;

    private Service service;


    public DispatchHelloLiteralTest(String name) {
        super(name);

        // we'll fix the test harness correctly later,
        // so that test code won't have to hard code any endpoint address nor transport,
        // but for now let's just support local and HTTP to make unit tests happier.
        // this is not a good code, but it's just a bandaid solutino that works for now.
        if(ClientServerTestUtil.useLocal())
            endpointAddress = "local://"+new File(System.getProperty("tempdir")).getAbsolutePath().replace('\\','/')+'?'+portQName.getLocalPart();
        else
            endpointAddress = "http://localhost:8080/jaxrpc-client_dispatch_soap12_hello/hello";
    }

    private void createService() {

        try {
            service = Service.create(serviceQName);
            //does service.addPort(portQName, bindingIdString, endpointAddress
            addPort(service, portQName, bindingIdString, endpointAddress);
        } catch (WebServiceException e) {
            e.printStackTrace();
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

        return service.createDispatch(portQName, SOAPMessage.class, Service.Mode.MESSAGE);
    }

    private Dispatch createDispatchSOAPMessageSource() {

        return service.createDispatch(portQName, Source.class, Service.Mode.MESSAGE);
    }

    private Dispatch getDispatchJAXB() {
        createService();
        return createDispatchJAXB();
    }

    private Dispatch getDispatchSource() {
        createService();
        return createDispatchSource();
    }

    private Dispatch getDispatchSOAPMessage() {
        createService();
        return createDispatchSOAPMessage();
    }

    private Dispatch getDispatchSOAPMessageSource() {
        createService();
        return createDispatchSOAPMessageSource();
    }


    private static jakarta.xml.bind.JAXBContext createJAXBContext() {
        try {
            return jakarta.xml.bind.JAXBContext.newInstance(client.dispatch.soap12.hello.client.ObjectFactory.class);
        } catch (jakarta.xml.bind.JAXBException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }


    /*
    * for debugging
    */
    public static void main(String [] args) throws Exception {
        try {
            System.setProperty("uselocal", "true");
            DispatchHelloLiteralTest dht = new DispatchHelloLiteralTest("");
            dht.testHelloRequestResponseJAXB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testHelloRequestResponseJAXB() throws Exception {

        JAXBContext jc = null;

        HelloResponse helloResult = null;
        Hello_Type hello = new Hello_Type();

        jc = createJAXBContext();
        try {
            hello.setExtra("Test ");
            hello.setArgument("Dispatch ");

            Dispatch dispatch = getDispatchJAXB();
            Object result = dispatch.invoke(hello);

            assertEquals(((HelloResponse) result).getExtra(), hello.getExtra());
            assertEquals(((HelloResponse) result).getArgument(), hello.getArgument());

        } catch (WebServiceException jex) {
            jex.printStackTrace();
            assertTrue(jex instanceof WebServiceException);
            Object jbe = jex.getCause();
            assertTrue(jbe instanceof JAXBException);
            System.out.println(jex.getMessage());
        }

    }

    public void testHelloRequestResponseSOAPMessage() throws Exception {

        Dispatch dispatch = getDispatchSOAPMessage();
        byte[] bytes = helloSM.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Source source = makeStreamSource(helloSM);

        SOAPMessage message = getSOAPMessage12(source);
        Object result = dispatch.invoke(message);
        assertTrue(result instanceof SOAPMessage);

    }

    public void testHelloRequestResponseSOAPMessageSource() throws Exception {

        Dispatch dispatch = getDispatchSOAPMessageSource();
        Source source = makeStreamSource(helloSM);
        Object result = dispatch.invoke(source);
        assertTrue(result instanceof Source);

    }

}
