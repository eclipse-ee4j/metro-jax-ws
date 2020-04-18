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

import testutil.ClientServerTestUtil;
import java.io.File;
import jakarta.xml.ws.Service;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.soap.SOAPBinding;
import junit.framework.TestCase;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.http.HTTPBinding;
import jakarta.xml.ws.soap.SOAPBinding;
import java.net.URI;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    private String helloSM= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><tns:Hello xmlns:tns=\"urn:test:types\"><argument>Dispatch</argument><extra>Test</extra></tns:Hello></soapenv:Body></soapenv:Envelope>";

    private QName serviceQName = new QName("urn:test", "Hello");
    private QName portQName = new QName("urn:test", "HelloPort");;
    private String endpointAddress;

    private ClientServerTestUtil util = new ClientServerTestUtil();

    private static final JAXBContext jaxbContext = createJAXBContext();

    public static void main(String [] args) {
        try {
            System.setProperty("uselocal", "true");
            HelloLiteralTest test = new HelloLiteralTest("HelloLiteralTest");
            test.testHelloHandlerSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JAXBContext getJAXBContext(){
        return jaxbContext;
    }

    private static jakarta.xml.bind.JAXBContext createJAXBContext(){
        try {
            return JAXBContext.newInstance(ObjectFactory.class);
        } catch(jakarta.xml.bind.JAXBException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    public HelloLiteralTest(String name) {
        super(name);

        // we'll fix the test harness correctly later,
        // so that test code won't have to hard code any endpoint address nor transport,
        // but for now let's just support local and HTTP to make unit tests happier.
        // this is not a good code, but it's just a bandaid solutino that works for now.
        if(ClientServerTestUtil.useLocal())
            endpointAddress = "local://"+new File(System.getProperty("tempdir")).getAbsolutePath().replace('\\','/')+'?'+portQName.getLocalPart();
        else
            endpointAddress = "http://localhost:8080/jaxrpc-provider_tests_xmlbind_jaxb/hello";
    }

    Service createService () {
        Service service = Service.create(serviceQName);

        return service;
    }

    private Dispatch createDispatch() throws Exception {
        Service service = createService();
        service.addPort(portQName,
            HTTPBinding.HTTP_BINDING, setTransport(endpointAddress));
        Dispatch<Object> dispatch = service.createDispatch(portQName, createJAXBContext(), Service.Mode.PAYLOAD);
        return dispatch;
    }

    protected String setTransport(String endpoint) {
           try {

               if (ClientServerTestUtil.useLocal()) {
                  URI uri = new URI(endpoint);
                  return uri.resolve(new URI("local", uri.getPath(), uri.getFragment())).toString();
               }

           } catch (Exception ex) {
               ex.printStackTrace();
           }
        return endpoint;
       }
    public void testHelloRequestResponse() throws Exception {
        Dispatch<Object> dispatch = createDispatch();

        Hello_Type hello = new Hello_Type();
        hello.setArgument("Dispatch");
        hello.setExtra("Test");

        Object result = dispatch.invoke(hello);

        assertTrue(result instanceof HelloResponse);
        assertTrue("foo".equals(((HelloResponse)result).getArgument()));
        assertTrue("bar".equals(((HelloResponse)result).getExtra()));
    }

    /*
     * Version of testHelloRequestResponseSource with a handler
     * added dynamically. Handler will change "foo" to
     * "hellofromhandler." Service will respond to that argument
     * with "hellotohandler." Handler then checks incoming message
     * for that text and changes to "handlerworks" to show that handler
     * worked on incoming and outgoing message.
     */
    public void testHelloHandlerJAXB() throws Exception {
        Dispatch<Object> dispatch = createDispatch();

        TestLogicalHandler handler = new TestLogicalHandler();
        handler.setHandleMode(TestLogicalHandler.HandleMode.JAXB);
        ClientServerTestUtil.addHandlerToBinding(handler, dispatch);

        Hello_Type hello = new Hello_Type();
        hello.setArgument("Dispatch");
        hello.setExtra("Test");

        Object result = dispatch.invoke(hello);

        assertTrue(result instanceof HelloResponse);
        assertTrue("handlerworks".equals(((HelloResponse)result).getArgument()));
        assertTrue("bar".equals(((HelloResponse)result).getExtra()));
    }

    /*
     * Version of testHelloRequestResponseSource with a handler
     * added dynamically. Handler will change "foo" to
     * "hellofromhandler." Service will respond to that argument
     * with "hellotohandler." Handler then checks incoming message
     * for that text and changes to "handlerworks" to show that handler
     * worked on incoming and outgoing message.
     */
    public void testHelloHandlerSource() throws Exception {
        Dispatch<Object> dispatch = createDispatch();

        TestLogicalHandler handler = new TestLogicalHandler();
        handler.setHandleMode(TestLogicalHandler.HandleMode.SOURCE);
        ClientServerTestUtil.addHandlerToBinding(handler, dispatch);

        Hello_Type hello = new Hello_Type();
        hello.setArgument("Dispatch");
        hello.setExtra("Test");

        Object result = dispatch.invoke(hello);

        assertTrue(result instanceof HelloResponse);
        assertTrue("handlerworks".equals(((HelloResponse)result).getArgument()));
        assertTrue("bar".equals(((HelloResponse)result).getExtra()));
    }

}
