/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.wsdl_hello_lit.client;

import jakarta.xml.ws.soap.SOAPFaultException;
import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.ProtocolException;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.soap.SOAPFaultException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fromwsdl.wsdl_hello_lit.client.handlers.MUHelperHandler;

/**
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    // main method added for debugging
    public static void main(String [] args) {
        try {
            System.setProperty("uselocal", "true");
            HelloLiteralTest test = new HelloLiteralTest("HelloLiteralTest");
            test.testHello();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static Hello stub;

    public HelloLiteralTest(String name) throws Exception {
        super(name);
        Hello_Service service = new Hello_Service();

        stub = service.getHelloPort();
        ClientServerTestUtil.setTransport(stub);
    }


    public void testHello() throws Exception {
        try {
            String arg = "foo";
            String extra = "bar";
            Hello_Type req = new Hello_Type();
            req.setArgument(arg);
            req.setExtra(extra);
            HelloResponse response = stub.hello(req);
            assertEquals(arg, response.getArgument());
            assertEquals(extra, response.getExtra());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testKeyword(){
        String req = "Hello";
        List<Handler> handlerchain =  new ArrayList<Handler>();
        handlerchain.add(new MyHandler());
        ((BindingProvider) stub).getBinding().setHandlerChain(handlerchain);
        ((BindingProvider) stub).getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);        
        ((BindingProvider) stub).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY,"urn:test:hello_mod");
        String resp = stub.testKeyword(req);
        assertTrue(resp.equals("Hello World!"));
    }

    class MyHandler implements SOAPHandler<SOAPMessageContext> {

        public boolean handleMessage(SOAPMessageContext context) {
            if (context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY).equals(
                    Boolean.TRUE)) {
                if (!(context.get(BindingProvider.SOAPACTION_URI_PROPERTY).equals("urn:test:hello_mod"))) {
                    throw new RuntimeException("SOAPAction not set as expected");
                }
            }
            return true;
        }
        public boolean handleFault(SOAPMessageContext context) {
            return true;
        }
        public void close(MessageContext context) {}
        public Set<QName> getHeaders() {
            return null;
        }
    }

    public void testVoid() throws Exception {
        try {
            VoidType req = new VoidType();
            VoidType response = stub.voidTest(req);
            assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

   public void testEchoArray() throws Exception{
          try{
              String[] in = {"JAXRPC 1.0", "JAXRPC 1.1", "JAXRPC 1.1.2", "JAXRPC 2.0"};
              NameType nt = new NameType();
              nt.getName().add(in[0]);
              nt.getName().add(in[1]);
              nt.getName().add(in[2]);
              nt.getName().add(in[3]);
              jakarta.xml.ws.Holder<NameType> req = new jakarta.xml.ws.Holder<NameType>(nt);
              stub.echoArray(req);
              System.out.println("Here we are");
              assertTrue(req.value == null);
          }catch(Exception e){
              e.printStackTrace();
              assertTrue(false);
          }
   }




    public void testEchoArray1() throws Exception{
        try{
            String[] in = {"JAXRPC 1.0", "JAXRPC 1.1", "JAXRPC 1.1.2", "JAXRPC 2.0", "EA"};
            NameType nt = new NameType();
            nt.getName().add(in[0]);
            nt.getName().add(in[1]);
            nt.getName().add(in[2]);
            nt.getName().add(in[3]);
            jakarta.xml.ws.Holder<NameType> req = new jakarta.xml.ws.Holder<NameType>(nt);
            stub.echoArray1(req);
            assertTrue(java.util.Arrays.equals(in, req.value.getName().toArray()));
        }catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }




    public void testEchoArray2c() throws Exception {
        try {
            jakarta.xml.ws.Holder<NameType> req = new jakarta.xml.ws.Holder<NameType>();
            stub.echoArray2(req);
            assertTrue(req.value == null);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testEchoArray3() throws Exception {
        try {
            java.util.List<String> in = new ArrayList<String>();
            in.add("JAXRPC 1.0");
            in.add("JAXRPC 1.1");
            in.add("JAXRPC 1.1.2");
            in.add("JAXRPC 2.0");
            jakarta.xml.ws.Holder<java.util.List<String>> req = new jakarta.xml.ws.Holder<java.util.List<String>>(in);
            stub.echoArray3(req);
            assertTrue(in.equals(req.value));
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testEchoArray4() throws Exception {
        try {
            NameType1 resp = stub.echoArray4(new NameType1());
            assertEquals(resp.getName().get(0).getArgument(), "arg1");
            assertEquals(resp.getName().get(0).getExtra(), "extra1");
            assertEquals(resp.getName().get(1).getArgument(), "arg2");
            assertEquals(resp.getName().get(1).getExtra(), "extra2");


        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
    
    /*
     * MU test here for soap 1.1. Test uses a simple handler
     * on client side to test service with no handlers.
     */
    public void testMustUnderstand1() throws Exception {
        String next_1_1 = "http://schemas.xmlsoap.org/soap/actor/next";
        try {
            // clear handlers (should be none) and add helper handler
            ClientServerTestUtil.clearHandlers((BindingProvider) stub);
            MUHelperHandler handler = new MUHelperHandler();
            ClientServerTestUtil.addHandlerToBinding(handler,
                (BindingProvider) stub);
            
            // have handler set header that is ignored
            handler.setMUHeader(new QName("urn:mutest", "someheader"),
                "notarealactor");

            // make the call
            String arg = "foo";
            Hello_Type req = new Hello_Type();
            req.setArgument(arg);
            HelloResponse response = stub.hello(req);
            assertEquals(arg, response.getArgument());
            
            // add header that should result in soap fault
            handler.setMUHeader(new QName("urn:mutest", "someheader"),
                next_1_1);
            
            // make the call
            try {
                response = stub.hello(req);
                fail("did not receive any exception");
            } catch (ProtocolException e) {
                if (e instanceof SOAPFaultException) {
                    // pass
                } else {
                    fail("did not receive soap fault, received: " +
                        e.toString());
                }
            } catch (Exception e) {
                fail("did not receive protocol exception. received " +
                    e.toString());
            }
        } finally {
            // always clear the handlers
            ClientServerTestUtil.clearHandlers((BindingProvider) stub);
        }
    }

}
