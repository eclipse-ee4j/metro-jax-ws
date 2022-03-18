/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.doclit_fault.client;

import client.common.client.DispatchTestCase;
import testutil.ClientServerTestUtil;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.SOAPBinding;
import java.io.File;

/**
 * @author JAX-RPC RI Development Team
 */
public class DispatchTestApp extends DispatchTestCase {

    private QName serviceQName = new QName("urn:test", "FaultService");
    private QName portQName = new QName("urn:test", "FaultPort");
    private String bindingIdString = SOAPBinding.SOAP11HTTP_BINDING;

    private String endpointAddress = "http://localhost:8080/jaxrpc-fromwsdl_doclit_fault/hello";

    private Service service;
    private Dispatch dispatch;


    public DispatchTestApp(String name) {
        super(name);

        // we'll fix the test harness correctly later,
        // so that test code won't have to hard code any endpoint address nor transport,
        // but for now let's just support local and HTTP to make unit tests happier.
        // this is not a good code, but it's just a bandaid solutino that works for now.
        if(ClientServerTestUtil.useLocal())
             endpointAddress = "local://"+new File(System.getProperty("tempdir")).getAbsolutePath().replace('\\','/')+'?'+portQName.getLocalPart();
        else
            endpointAddress = "http://localhost:8080/jaxrpc-client_dispatch_wsdl_hello/hello";
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
        try {
            JAXBContext context = DispatchTestApp.createJAXBContext();
            dispatch = service.createDispatch(portQName, context, Service.Mode.PAYLOAD);

        } catch (WebServiceException e) {
            e.printStackTrace();
        }
        return dispatch;
    }


    private Dispatch createDispatchSource() {
        try {
            dispatch = service.createDispatch(portQName, Source.class,
                Service.Mode.PAYLOAD);

        } catch (WebServiceException e) {
            e.printStackTrace();
        }
        return dispatch;
    }


    private Dispatch createDispatchSOAPMessage() {
        try {
            dispatch = service.createDispatch(portQName, SOAPMessage.class,
                Service.Mode.MESSAGE);

        } catch (WebServiceException e) {
            e.printStackTrace();
        }
        return dispatch;
    }

    private Dispatch createDispatchSOAPMessageSource() {
        try {

            dispatch = service.createDispatch(portQName, Source.class,
                Service.Mode.MESSAGE);

        } catch (WebServiceException e) {
            e.printStackTrace();
        }
        return dispatch;
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


    private static JAXBContext createJAXBContext() {
        try {
            return JAXBContext.newInstance(fromwsdl.doclit_fault.client.ObjectFactory.class);
        } catch (jakarta.xml.bind.JAXBException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }

   public void testFault1JAXB()
            throws Exception {
        try {
             java.lang.String in = "Fault1";
            JAXBElement<String> echoElement = new ObjectFactory().createEchoElement(in);

            Dispatch dispatch = getDispatchJAXB();
            Object ret = dispatch.invoke(echoElement);
            fail("did not receive an exception");
        } catch (Exception e) {
            System.out.println(e.getClass());
            e.printStackTrace();
            //assertTrue(e instanceof Fault1Exception);
            //System.out.println("Expected exception received: " + e.getMessage());
            //e.printStackTrace();
            //assertTrue(true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void tkkestFault1Source()
            throws Exception {
        try {
             java.lang.String in = "Fault1";
            //JAXBElement<String> echoElement = new ObjectFactory().createEchoElement(in);
            Source echoElement = null;

            Dispatch dispatch = getDispatchSource();
            Object ret = dispatch.invoke(echoElement);
            fail("did not receive an exception");
        } catch (Exception e) {
            System.out.println(e.getClass());
            e.printStackTrace();
            //assertTrue(e instanceof Fault1Exception);
            //System.out.println("Expected exception received: " + e.getMessage());
            //e.printStackTrace();
            //assertTrue(true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void kkktestFault2() {
        /*try {
            java.lang.String in = "Fault2";
            java.lang.String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (Fault2Exception e) {
            System.out.println("Expected exception received: " + e.getMessage());
            assertTrue(true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
        */
    }

    public void kkktestFault3() {
       /* try {
            String in = "Fault3";
            String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (Fault3Exception e) {
            System.out.println("Expected exception received: " + e.getMessage());
            assertTrue(true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
        */
    }

    public void kkktestFault4() {
       /* try {
            String in = "Fault4";
            String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (Fault4Exception e) {
            System.out.println("Expected exception received: " + e.getMessage());
            assertTrue(true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
        */
    }

    public void kkktestHelloRequestResponseJAXB() throws Exception {

        JAXBContext jc = null;
      /*
        HelloResponse helloResult = null;
        Hello_Type hello = new Hello_Type();

        jc = DispatchTestApp.createJAXBContext();
        try {
            hello.setArgument("foo");
            hello.setExtra("Test ");

            Dispatch dispatch = getDispatchJAXB();

            helloResult = (HelloResponse) dispatch.invoke(hello);

            //assertEquals(((HelloResponse) result).getExtra(), hello.getExtra());
            //assertEquals(((HelloResponse) result).getArgument(), hello.getArgument());
        } catch (WebServiceException jex) {
            fail("testHelloRequestResponseJAXB FAILED");
        }
        */
    }




  /*
    public void testHelloRequestResponseXML() throws Exception {

      /  Dispatch dispatch = getDispatchSource();
        assertTrue(dispatch != null);
        assertTrue(dispatch instanceof com.sun.xml.ws.client.dispatch.SOAPSourceDispatch);

        //Source request = makeStreamSource(helloMsg);
        //Object result = dispatch.invoke(request);
        //assertTrue(result instanceof Source);
        //String xmlResult = sourceToXMLString((Source) result);
        //System.out.println("Got result : " + xmlResult);

        Collection<Source> sourceList = makeMsgSource(helloMsg);
        Collection<Source> responseList = makeMsgSource(helloResponse);
        try {
            for (Iterator iter = sourceList.iterator(); iter.hasNext();) {

                Object sourceObject = iter.next();
                Object result = dispatch.invoke(sourceObject);
                assertTrue(result instanceof Source);
                String xmlResult = sourceToXMLString((Source) result);
                System.out.println("Got result : " + xmlResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("testHelloRequestResponseXML FAILED");
        }
    }


    public void testHelloRequestResponseSOAPMessage() throws Exception {

        Dispatch dispatch = getDispatchSOAPMessage();
        assertTrue(dispatch != null);
        assertTrue(dispatch instanceof com.sun.xml.ws.client.dispatch.SOAPMessageDispatch);
        byte[] bytes = helloSM.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Source source = makeStreamSource(helloSM);

        SOAPMessage message = getSOAPMessage(source);

        Object result = dispatch.invoke(message);
        //todo:need to check contents
        assertTrue(result instanceof SOAPMessage);
        ((SOAPMessage) result).writeTo(System.out);

    }

    public void testHelloRequestResponseSOAPMessageSource() throws Exception {

        Dispatch dispatch = getDispatchSOAPMessageSource();
        Source source = makeStreamSource(helloSM);
        Object result = dispatch.invoke(source);
        System.out.println("Result class is " + result.getClass().getName());
        //todo: need to check contents
        assertTrue(result instanceof Source);
        String xmlResult = sourceToXMLString((Source) result);
        System.out.println(xmlResult);
    }

    */

    /*
     * for debugging
     */
    public static void main(String [] args) {
        try {
            if (ClientServerTestUtil.useLocal()) {
                System.out.println("http transport only exiting");
                return;
            }
            System.setProperty("uselocal", "true");
            System.setProperty("com.sun.xml.ws.client.ContentNegotiation", "optimistic");
            DispatchTestApp testor = new DispatchTestApp("TestClient");
            //testor.testHelloRequestResponseSOAPMessageSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
