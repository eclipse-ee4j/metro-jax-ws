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

import javax.xml.namespace.QName;

import testutil.ClientServerTestUtil;

/**
 * @author JAX-RPC RI Development Team
 */
public class TestApp extends TestCase {

    private static Hello stub;

    public TestApp(String name) throws Exception {
        super(name);
        Hello_Service service = new Hello_Service();
        stub = (Hello) service.getPort(new QName("urn:test", "HelloPort"),Hello.class );
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

    //TODO: commenting out till we know how to support unbound parts at runtime
//   public void testEchoArray() throws Exception{
//          try{
//              String[] in = {"JAXRPC 1.0", "JAXRPC 1.1", "JAXRPC 1.1.2", "JAXRPC 2.0"};
//              NameType nt = new NameType();
//              nt.getName().add(in[0]);
//              nt.getName().add(in[1]);
//              nt.getName().add(in[2]);
//              nt.getName().add(in[3]);
//              javax.xml.ws.Holder<NameType> req = new javax.xml.ws.Holder<NameType>(nt);
//              stub.echoArray(req);
//              System.out.println("Here we are");
//              assertTrue(req.value == null);
//          }catch(Exception e){
//              e.printStackTrace();
//              assertTrue(false);
//          }
//   }
//



    //TODO: commentout out. INOUT rule need to change for a header and body
    //bound part in BARE mode.
//    public void testEchoArray1() throws Exception{
//        try{
//            String[] in = {"JAXRPC 1.0", "JAXRPC 1.1", "JAXRPC 1.1.2", "JAXRPC 2.0", "EA"};
//            NameType nt = new NameType();
//            nt.getName().add(in[0]);
//            nt.getName().add(in[1]);
//            nt.getName().add(in[2]);
//            nt.getName().add(in[3]);
//            javax.xml.ws.Holder<NameType> req = new javax.xml.ws.Holder<NameType>(nt);
//            stub.echoArray1(req);
//            assertTrue(java.util.Arrays.equals(in, req.value.getName().toArray()));
//        }catch(Exception e){
//            e.printStackTrace();
//            assertTrue(false);
//        }
//    }
//
//


    /*   public void testEchoArray2c() throws Exception {
           try {
               javax.xml.ws.Holder<NameType> req = new javax.xml.ws.Holder<NameType>();
               stub.echoArray2(req);
               assertTrue(req.value != null);
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
               javax.xml.ws.Holder<java.util.List<String>> req = new javax.xml.ws.Holder<java.util.List<String>>(in);
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
       */
}
