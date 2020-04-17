/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.handler_simple.client;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.HandlerResolver;
import jakarta.xml.ws.handler.PortInfo;
import jakarta.xml.ws.Service;

import fromjava.handler_simple.common.TestHandler;

import junit.framework.*;
import testutil.ClientServerTestUtil;

public class HandlerClient extends TestCase {

    /*
     * main() method used during debugging
     */
    public static void main(String [] args) {
        try {
            System.setProperty("uselocal", "true");
            HandlerClient test = new HandlerClient("HandlerClient");
            test.test2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HandlerClient(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(fromjava.handler_simple.client.HandlerClient.class);
        return suite;
    }

    private HelloService createService() throws Exception {
        HelloService service = new HelloService();
        return service;
    }

    // util method when the service isn't needed
    private HelloPortType createStub() throws Exception {
        return createStub(createService());
    }

    private HelloPortType createStub(HelloService service) throws Exception {
        HelloPortType stub = service.getHelloPort();
        ClientServerTestUtil.setTransport(stub);
        return stub;
    }

    /* tests below here */

    /*
     * Each handler adds one to the int during request and response,
     * so we should receive the original number plus 4 if the
     * handlers are working.
     */
    public void test1() throws Exception {
        HelloPortType stub = createStub();

        int x = 1;
        int diff = 4; // 2 per handler invoked

        int y = stub.hello(x);

        System.out.println("sent: " + x + ", expect " + (x+diff) +
            " back. received: " + y);
        assertTrue(y == x+diff); // x+4 with all handlers
    }

//    public void testDispatch1() throws Exception {
//        int x = 1; // what we send
//        int diff = 2; // 4 handler invocations
//
//        JAXBContext jxbContext = JAXBContext.newInstance(ObjectFactory.class);
//        Dispatch dispatch = createDispatchForJAXB(
//            new QName("urn:test", "HelloPort"), jxbContext);
//
//        Hello_Type request = objectFactory.createHello_Type();
//        request.setValue(1);
//
//        // make first call with no client side handlers
//        HelloResponse response = (HelloResponse) dispatch.invoke(request);
//        assertNotNull("response cannot be null", response);
//
//        int y = response.getValue();
//        System.out.println("sent: " + x + ", expect " + (x+diff) +
//            " back. received: " + y);
//        assertTrue(y == x+diff);
//
//        System.out.println("now adding handler to dispatch");
//        Binding binding = dispatch.getBinding();
//        assertNotNull("binding cannot be null", binding);
//        int handlerChainLength = binding.getHandlerChain().size();
//        assertEquals("the handler list is not empty", 0, handlerChainLength);
//
//        // add handler
//        HandlerInfo hInfo = new HandlerInfo(
//            handler_tests.simple_handler_test.client.handlers.ClientHandler.class,
//            null, null);
//        List<HandlerInfo> newHandlers = new ArrayList<HandlerInfo>();
//        newHandlers.add(hInfo);
//        binding.setHandlerChain(newHandlers);
//
//        // now try again
//        diff = 4;
//        response = (HelloResponse) dispatch.invoke(request);
//        assertNotNull("response cannot be null", response);
//
//        y = response.getValue();
//        System.out.println("sent: " + x + ", expect " + (x+diff) +
//            " back. received: " + y);
//        assertTrue(y == x+diff);
//    }

    /*
     * Test tries to add a handler programmatically after clearing
     * handlers out of registry in the service.
     */
    public void test2() throws Exception {
        HelloService service = createService();
        service.setHandlerResolver(new HandlerResolver() {
            public List<Handler> getHandlerChain(PortInfo info) {
                return new ArrayList<Handler>();
            }
        });

        HelloPortType stub = createStub(service);

        int x = 1;
        int diff = 2; // 2 per handler invoked

        int y = stub.hello(x);

        System.out.println("sent: " + x + ", expect " + (x+diff) +
            " back. received: " + y);
        assertTrue(y == x+diff);

        // now add client handler
        service.setHandlerResolver(new HandlerResolver() {
            public List<Handler> getHandlerChain(PortInfo info) {
                List<Handler> handlers = new ArrayList<Handler>();
                handlers.add(new TestHandler());
                return handlers;
            }
        });
        stub = createStub(service);

        // test again
        diff = 4;
        y = stub.hello(x);
        System.out.println("sent: " + x + ", expect " + (x+diff) +
            " back. received: " + y);
        assertTrue(y == x+diff);

    }

}
