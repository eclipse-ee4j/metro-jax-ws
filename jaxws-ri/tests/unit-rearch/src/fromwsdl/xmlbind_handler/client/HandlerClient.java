/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.xmlbind_handler.client;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.Service;

import fromwsdl.xmlbind_handler.common.TestHandler;
import fromwsdl.xmlbind_handler.common.TestSOAPHandler;

import junit.framework.*;
import testutil.ClientServerTestUtil;

public class HandlerClient extends TestCase {

    /*
     * main() method used during debugging
     */
    public static void main(String [] args) {
        try {
            System.setProperty("uselocal", "true");
            System.setProperty("log", "true");
            HandlerClient test = new HandlerClient("HandlerClient");
            test.testHTTPException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HandlerClient(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(fromwsdl.xmlbind_handler.client.HandlerClient.class);
        return suite;
    }

    private Hello_Service createService() throws Exception {
        Hello_Service service = new Hello_Service();
        return service;
    }

    // util method when the service isn't needed
    private Hello createStub() throws Exception {
        return createStub(createService());
    }

    private Hello createStub(Hello_Service service) throws Exception {
        Hello stub = (Hello) service.getPort(new QName("urn:test", "HelloPort"),
            fromwsdl.xmlbind_handler.client.Hello.class);
        ClientServerTestUtil.setTransport(stub, null);
        return stub;
    }

    /* tests below here */

    /*
     * Each handler adds one to the int during request and response,
     * so we should receive the original number plus 2 if the
     * server handlers are working.
     */
    public void test1() throws Exception {
        Hello stub = createStub();

        int x = 1;
        int diff = 2;
        
        int y = stub.hello(x);
        assertEquals(x+diff, y);
    }

    /*
     * This test causes the server handler to throw an http
     * exception.
     */
    public void testHTTPException() throws Exception {
        Hello stub = createStub();
        int x = TestHandler.THROW_HTTP_EXCEPTION;

        TestSOAPHandler handler = new TestSOAPHandler();
        handler.setExpectEmptyResponse(true);
        ClientServerTestUtil.addHandlerToBinding(handler,
            (javax.xml.ws.BindingProvider) stub);
        
        try {
            stub.hello(x);
            fail("did not receive an exception");
        } catch (Exception e) {
            // todo (bobby) -- check exception
            System.out.println("received " + e);
        }
        
        // check handler to make sure response was empty
//        if (handler.getException() != null) {
//            fail(handler.getException().getMessage());
//        }
    }
    
    /*
     * This test causes the server handler to throw
     * a runtime exception.
     */
    public void testRuntimeException() throws Exception {
        Hello stub = createStub();
        int x = TestHandler.THROW_RUNTIME_EXCEPTION;
        
        try {
            stub.hello(x);
            fail("did not receive an exception");
        } catch (Exception e) {
            // todo (bobby) -- check exception
            System.out.println("received " + e);
        }
    }
    
    /*
     * This test causes the server handler to throw a
     * protocol exception.
     */
    public void testProtocolException() throws Exception {
        Hello stub = createStub();
        int x = TestHandler.THROW_HTTP_EXCEPTION;
        
        try {
            stub.hello(x);
            fail("did not receive an exception");
        } catch (Exception e) {
            // todo (bobby) -- check exception
            System.out.println("received " + e);
        }
        
        // check handler to make sure response was empty
//        if (handler.getException() != null) {
//            fail(handler.getException().getMessage());
//        }
    }
    
}
