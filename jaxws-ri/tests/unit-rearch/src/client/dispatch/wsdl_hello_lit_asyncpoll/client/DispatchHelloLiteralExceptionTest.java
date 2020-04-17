/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.wsdl_hello_lit_asyncpoll.client;

import com.sun.xml.ws.streaming.XMLReaderException;
import junit.framework.Assert;
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
import jakarta.xml.ws.*;
import jakarta.xml.ws.soap.SOAPFaultException;
import jakarta.xml.ws.soap.SOAPBinding;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

/**
 * @author JAX-RPC RI Development Team
 */
public class DispatchHelloLiteralExceptionTest extends TestCase {

    private String helloMsg = "<Hello xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></Hello>";
    private String badhelloMsg = "<Hello xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</Hello>";

    private String helloResponse = "<HelloResponse xmlns=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></HelloResponse>";
    private String voidMsg = "<VoidTest xmlns=\"urn:test:types\"/>";
    private String voidResponse = "<VoidTestResponse xmlns=\"urn:test:types\"/>";

    private QName serviceQName = new QName("urn:test", "Hello");
    private QName portQName = new QName("urn:test", "HelloPort");;
    private String bindingIdString = SOAPBinding.SOAP11HTTP_BINDING;
    private String endpointAddress = "http://localhost:8080/jaxrpc-client_dispatch_wsdl_hello_lit_asyncpoll/hello";
    private static final String ENDPOINT_IMPL = "client.dispatch.wsdl_hello_lit_asyncpoll.server.Hello_PortType_Impl";
    private Service service;
    private Dispatch dispatch;

    public DispatchHelloLiteralExceptionTest(String name) {
        super(name);
    }

    private void createService() throws Exception {

        service = Service.create(serviceQName);
    }

    void setTransport(Dispatch dispatch) throws Exception {
        // create helper class
        ClientServerTestUtil util = new ClientServerTestUtil();
        // set transport
        OutputStream log = null;
        //log = System.out;
        util.setTransport(dispatch, (OutputStream) log);
    }

    private Dispatch createDispatchJAXB() throws Exception {
        JAXBContext context = createJAXBContext();
        service.addPort(portQName, bindingIdString, endpointAddress);
        dispatch = service.createDispatch(portQName, context, Service.Mode.PAYLOAD);
        setTransport(dispatch);
        return dispatch;
    }

    private Dispatch createDispatchSource() throws Exception {
        service.addPort(portQName, bindingIdString, endpointAddress);
        dispatch = service.createDispatch(portQName, Source.class, Service.Mode.PAYLOAD);
        setTransport(dispatch);
        return dispatch;
    }

    private Dispatch getDispatchJAXB() throws Exception {
        createService();
        return createDispatchJAXB();
    }

    private Dispatch getDispatchSource() throws Exception {
        createService();
        return createDispatchSource();
    }

    private Collection<Source> makeMsgSource(String msg) throws Exception {
        Collection<Source> sourceList = new ArrayList();

        byte[] bytes = msg.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ByteArrayInputStream saxinputStream = new ByteArrayInputStream(bytes);
        InputSource inputSource = new InputSource(saxinputStream);

        ByteArrayInputStream sinputStream = new ByteArrayInputStream(bytes);

        DOMSource domSource = new DOMSource(createDOMNode(inputStream));
        sourceList.add(domSource);
        SAXSource saxSource = new SAXSource(inputSource);
        sourceList.add(saxSource);
        StreamSource streamSource = new StreamSource(sinputStream);
        sourceList.add(streamSource);

        return sourceList;
    }

    public Node createDOMNode(InputStream inputStream) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setValidating(false);
        DocumentBuilder builder = dbf.newDocumentBuilder();
        return builder.parse(inputStream);
    }


    private Source makeStreamSource(String msg) {
        byte[] bytes = msg.getBytes();
        ByteArrayInputStream sinputStream = new ByteArrayInputStream(bytes);
        return new StreamSource(sinputStream);
    }


    private static JAXBContext createJAXBContext() throws Exception {
        return JAXBContext.newInstance(client.dispatch.wsdl_hello_lit_asyncpoll.client.ObjectFactory.class);
    }



    public void testHelloAsyncPollExceptionServerJAXB() throws Exception {
        JAXBContext jc = createJAXBContext();
        HelloException hello = new HelloException();
        HelloResponseException helloResult = new HelloResponseException();

        hello.setExtra("Test ");
        hello.setArgument("Dispatch ");

        Dispatch dispatch = getDispatchJAXB();

        Response result = dispatch.invokeAsync(hello);
        Object obj = result.get();
        System.out.println("Object is " + obj.getClass().getName());
        assertTrue(obj instanceof HelloResponseException);

    }
   /*take out for now to investigate
    public void testHelloAsyncPollREXJAXB() {

        JAXBContext jc = null;
        Hello_Type hello = null;
        HelloResponse helloResult = null;
        try {

            jc = createJAXBContext();
            hello = new Hello_Type();
            helloResult = new HelloResponse();
        } catch (Exception jbe) {
            jbe.printStackTrace();
        }

        try {
            hello.setExtra("Test ");
            hello.setArgument("Dispatch ");

            Dispatch dispatch = getDispatchJAXB();

            Response result = dispatch.invokeAsync(helloResult);

            while (!result.isDone()) {
            }
            result.get();
        } catch (Exception ex) {
            //todo
            System.out.println("");

            Assert.assertTrue(ex instanceof ExecutionException);
            //Assert.assertTrue(ex.getCause() instanceof SOAPFaultException);
            //Assert.assertTrue(ex.getCause().getCause() instanceof SOAPFaultException);
            String orgCause = ex.getCause().getMessage();

            if (orgCause != null)
                System.out.println("Cause = " + orgCause);
            System.out.println("");
            System.out.println("Test testHelloAsyncPollREXJAXB passed");

        }
    }
     */
    //null arg - WebServiceException
    public void testHelloAsyncPollRTEXJAXBBeforeInvoke() throws Exception {
        JAXBContext jc = createJAXBContext();
        Hello_Type hello = new Hello_Type();
        HelloResponse helloResult = new HelloResponse();

        try {
            Dispatch dispatch = getDispatchJAXB();
            Response result = dispatch.invokeAsync(null);
            result.get();
            //todo: put server side bug in - need to look at result of source
            //fail("No exception thrown");
        } catch (WebServiceException e) {
            assertTrue(e instanceof WebServiceException);
            if (e.getCause() != null){
                System.out.println("Cause for RTEX before poll is " + e.getCause());
            } else {
                //todo : set cause as IllegalArgumentException
                //System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            fail("Wrong exception thrown " + e.getMessage());
        }
    }
    //??
    //jaxbcontext with bad xmlSrc - should fail before send
    //look at this test
    //todo:need to make sure getCause is returned
    public void testHelloAsyncPollBadHelloMsgJAXB() throws Exception {
        JAXBContext jc = createJAXBContext();
        Hello_Type hello = new Hello_Type();
        HelloResponse helloResult = new HelloResponse();

        try {
            Dispatch dispatch = getDispatchJAXB();
            Response result = dispatch.invokeAsync(badhelloMsg);
            result.get();
            fail("Test did not throw WebServiceException as Expected");
        } catch (Exception e) {

            assertTrue(e instanceof WebServiceException);
            if (e.getCause() != null) {
               //System.out.println("Bad hello jaxb Cause is " + e.getCause());
              
            }else {
                //todo fix bug: Need to set webServiceException cause a jaxbException
                //System.out.println("BadHello message jaxb is " + e.getMessage());
               //fail("e.getCause of WebServiceException is not set- must be set");

            }
        }
    }

    //jaxbcontext with bad xmlSrc - should fail before send
    public void testHelloAsyncPollBadHelloMsgSrcJAXB() throws Exception {
        Source src = makeStreamSource(badhelloMsg);
        JAXBContext jc = createJAXBContext();
        Hello_Type hello = new Hello_Type();
        HelloResponse helloResult = new HelloResponse();

        try {
            Dispatch dispatch = getDispatchSource();
            Response result = dispatch.invokeAsync(src);
            result.get();
             fail("Test did not throw WebServiceException as Expected");
        } catch (WebServiceException e) {
            Throwable cause = e.getCause();
            assertNotNull("cause is null", cause);
            assertTrue("cause is incorrect. should be XMLStreamException, " +
                "instead is " + cause.getClass().toString(),
                cause.getClass().isAssignableFrom(XMLStreamException.class));
        }
    }

 /*   public void testHelloResponseAsRequestAsyncpollJAXB() {

        JAXBContext jc = null;
        Hello_Type hello = null;
        HelloResponse helloResult = null;
        try {
            jc = createJAXBContext();

            hello = new Hello_Type();
            helloResult = new HelloResponse();
        } catch (Exception jbe) {
            jbe.printStackTrace();
        }

        try {
            Dispatch dispatch = getDispatchJAXB();
            Response result = dispatch.invokeAsync(helloResult);
            result.get();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof ExecutionException);
            Assert.assertTrue(ex.getCause() instanceof SOAPFaultException);
            //Assert.assertTrue(ex.getCause().getCause() instanceof SOAPFaultException);
            String orgCause = ex.getCause().getMessage();
            if (orgCause != null)
                System.out.println("Cause = " + orgCause);
            System.out.println("SOAPFaultExceptionTestPassed ");
        }
    }
   */

    public void testHelloAsyncPollBadHelloMsgXML() throws Exception {

        Dispatch dispatch = getDispatchSource();
        Source src = makeStreamSource(badhelloMsg);

        try {
            Response result = dispatch.invokeAsync(src);
            fail("Test did not throw WebServiceException as Expected");
        } catch (WebServiceException e) {
            Throwable cause = e.getCause();
            assertNotNull("cause is null", cause);
            assertTrue("cause is incorrect. should be XMLStreamException, " +
                "instead is " + cause.getClass().toString(),
                cause.getClass().isAssignableFrom(XMLStreamException.class));
        }
    }

    public void testHelloAsyncPollHelloResponseAsRequestMsgXML() {

        Dispatch dispatch = null;
        try {
            dispatch = getDispatchSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Source src = makeStreamSource(helloResponse);

        try {
            Response result = dispatch.invokeAsync(src);
            Object res = result.get();
            //todo put in server side bug
            //fail("No exception thrown as expected got " + res.toString());
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof ExecutionException);
            Assert.assertTrue(ex.getCause() instanceof SOAPFaultException);
            String orgCause = ex.getCause().getMessage();

            System.out.println("SOAPFaultExceptionTestPassed ");
            System.out.println("testHelloAsyncPollHelloResponseAsRequestMsgXML passed");
        }
    }


}
