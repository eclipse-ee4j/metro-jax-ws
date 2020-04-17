/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.wsdl_hello_lit_asynchandler.client;

import com.sun.xml.ws.streaming.XMLReaderException;
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
import jakarta.xml.ws.*;
import jakarta.xml.ws.soap.SOAPBinding;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Future;

import client.common.client.DispatchTestCase;

/**
 * @author JAX-RPC RI Development Team
 */
public class
    DispatchHelloLiteralExceptionTest extends DispatchTestCase {

    private String helloMsg = "<Hello xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></Hello>";
    private String badhelloMsg = "<Hello xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</Hello>";


    private String helloResponse = "<HelloOutput xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></HelloOutput>";
    private String badhelloResponse = "<HelloOutput xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></HelloOutput>";
    private String voidMsg = "<VoidTest xmlns=\"urn:test:types\"/>";
    private String voidResponse = "<VoidTestResponse xmlns=\"urn:test:types\"/>";

    private QName serviceQName = new QName("urn:test", "Hello");
    private QName portQName = new QName("urn:test", "HelloPort");;
    private String bindingIdString = SOAPBinding.SOAP11HTTP_BINDING;
    private String endpointAddress;
    private static final String ENDPOINT_IMPL = "client.dispatch.wsdl_hello_lit_asynchandler.server.Hello_PortType_Impl";
    private Service service;
    private Dispatch dispatch;


    public DispatchHelloLiteralExceptionTest(String name) {
        super(name);

        // we'll fix the test harness correctly later,
        // so that test code won't have to hard code any endpoint address nor transport,
        // but for now let's just support local and HTTP to make unit tests happier.
        // this is not a good code, but it's just a bandaid solutino that works for now.
        if(ClientServerTestUtil.useLocal())
            endpointAddress = "local://"+new File(System.getProperty("tempdir")).getAbsolutePath().replace('\\','/')+'?'+portQName.getLocalPart();
        else
            endpointAddress = "http://localhost:8080/jaxrpc-client_dispatch_wsdl_hello_lit_asynchandler/hello";
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

    private Dispatch getDispatchJAXB() {
        createService();
        return createDispatchJAXB();
    }

    private Dispatch getDispatchSource() {
        createService();
        return createDispatchSource();
    }

    private static jakarta.xml.bind.JAXBContext createJAXBContext() {
        try {
            return jakarta.xml.bind.JAXBContext.newInstance(client.dispatch.wsdl_hello_lit_asynchandler.client.ObjectFactory.class);
        } catch (jakarta.xml.bind.JAXBException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    public void testHelloAsyncHandlerREXJAXB() {

        JAXBContext jc = null;
        Hello_Type hello = null;
        HelloOutput helloResult = null;
        try {

            jc = createJAXBContext();
            hello = new Hello_Type();
            helloResult = new HelloOutput();
        } catch (Exception jbe) {
            jbe.printStackTrace();
        }

        try {
            hello.setExtra("Test ");
            hello.setArgument("Dispatch ");

            helloResult.setExtra("Test ");
            helloResult.setArgument("Dispatch ");

            Dispatch dispatch = getDispatchJAXB();

            JAXBAsyncHandlerRTEX handler = new JAXBAsyncHandlerRTEX(hello);

            Future result = dispatch.invokeAsync(helloResult, handler);

        } catch (Exception e) {
            assertTrue(e instanceof WebServiceException);
            //fail("Test testHelloAsyncHandlerREXJAXB trew rt exeptionb at client invoke");
            //e.printStackTrace();
        }
    }

    //null arg - WebServiceException
    public void testHelloAsyncHandlerRTEXJAXBBeforeInvoke() {

        JAXBContext jc = null;
        Hello_Type hello = null;
        HelloOutput helloResult = null;
        try {
            jc = createJAXBContext();
            hello = new Hello_Type();
            helloResult = new HelloOutput();
        } catch (Exception jbe) {
            jbe.printStackTrace();
        }

        try {
            hello.setExtra("Test ");
            hello.setArgument("Dispatch ");

            Dispatch dispatch = getDispatchJAXB();

            JAXBAsyncHandlerRTEX handler = new JAXBAsyncHandlerRTEX(hello);
            Future result = dispatch.invokeAsync(null, handler);

        } catch (Exception e) {
            assertTrue(e instanceof WebServiceException);
            System.out.println("test testHelloAsyncHandlerRTEXJAXBBeforeInvoke Passed");
            System.out.println("");
        }
    }

    //todo:investigatejaxbcontext with bad xmlSrc - should fail before send  --but does not
    public void testHelloAsyncHandlerBadHelloMsgJAXB() {


        JAXBContext jc = null;
        Hello_Type hello = null;
        HelloOutput helloResult = null;
        try {
            jc = createJAXBContext();

            hello = new Hello_Type();
            helloResult = new HelloOutput();

        } catch (Exception jbe) {
            jbe.printStackTrace();
        }

        try {
            hello.setExtra("Test ");
            hello.setArgument("Dispatch ");

            Dispatch dispatch = getDispatchJAXB();

            JAXBAsyncHandlerRTEX handler = new JAXBAsyncHandlerRTEX(hello);
            Future result = dispatch.invokeAsync(badhelloMsg, handler);

        } catch (Exception e) {
            System.out.println("");

            assertTrue(e instanceof WebServiceException);
            System.out.println("test testHelloAsyncHandlerRTEXJAXBBeforeInvoke Passed");

        }
    }

 /*todo:should fail before invoke */
   public void testHelloAsyncHandlerNullHandlerJAXB() {

        JAXBContext jc = null;
        Hello_Type hello = null;
        HelloOutput helloResult = null;
        try {
            jc = createJAXBContext();

            hello = new Hello_Type();
            helloResult = new HelloOutput();
        } catch (Exception jbe) {
            jbe.printStackTrace();
        }

        try {
            hello.setExtra("Test ");
            hello.setArgument("Dispatch ");

            Dispatch dispatch = getDispatchJAXB();

            JAXBAsyncHandler handler = new JAXBAsyncHandler(hello);
            Future result = dispatch.invokeAsync(hello, null);

        } catch (Exception e) {
            System.out.println("");
            //System.out.println(" Exception is " + e.getClass().getName());
            Throwable cause = e.getCause();
            assertTrue(e instanceof WebServiceException);
            if (cause != null)
                System.out.println("Cause is " + e.getMessage());
            System.out.println(" testHelloAsyncHandlerNullHandlerJAXB passed");
            System.out.println("");
        }
    }


  public void testHelloAsyncHandlerBadHelloMsgXML() {

        Dispatch dispatch = getDispatchSource();
        Source src = makeStreamSource(badhelloMsg);

        try {

            XMLAsyncHandler handler =
                new XMLAsyncHandler(src);
            Future result = dispatch.invokeAsync(src, handler);

        } catch (Exception e) {

            assertTrue(e instanceof WebServiceException);
            System.out.println("");
            System.out.println(" testHelloAsyncHandlerBadHelloMsgXML passed");
            System.out.println("");

        }
    }

  /*  public void testHelloAsyncHandlerHelloResponseAsRequestMsgXML() {

      Dispatch dispatch = getDispatchSource();
      Source src = makeStreamSource(helloResponse);

      try {

          JAXBAsyncHandlerRTEX handler = new JAXBAsyncHandlerRTEX((Hello_Type) null);
          Future result = dispatch.invokeAsync(src, handler);

      } catch (Exception e) {
          System.out.println("testHelloAsyncHandlerHelloResponseAsRequestMsgXML failed");

      }
  }
  */
}
