/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package customization.embedded.doclit.client;

import junit.framework.TestCase;
import junit.framework.Assert;
import testutil.ClientServerTestUtil;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    private static HelloWorld stub;

    public HelloLiteralTest(String name) throws Exception{
        super(name);
//        stub = (HelloWorld) ClientServerTestUtil.getPort(CustomService.class, HelloWorld.class, new QName("urn:test", "HelloPort"));
        CustomService service = new CustomService();
        stub = service.getCustomizedPort();
        ClientServerTestUtil.setTransport(stub);
    }



    public void testHello() throws Exception {
        try{
            String arg = "Hello";
            String extra = "Works";
            
            Holder<String> argHolder = new Holder<String>();
            argHolder.value = arg;
            
            Holder<String> extHolder = new Holder<String>();
            extHolder.value = extra;
                        
            stub.helloWorld(argHolder, extHolder);
            assertEquals(argHolder.value, arg + " World!");
            assertEquals(extHolder.value, extra + " Fine!");
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }
    
//    public void testHellowithHeaderFault() throws Exception {
//        try{
//            String arg = "Hello";
//            String extra = "Works";
//
//            Holder<String> argHolder = new Holder<String>();
//            argHolder.value = arg;
//            
//            Holder<String> extHolder = new Holder<String>();
//            extHolder.value = extra;
//
//            stub.helloWorld(argHolder, extHolder, "please throw exception!");
//            assertTrue(false);
//        } catch(InfoException ie){
//            if(ie.getFaultInfo().equals("InfoException on request"))
//                assertTrue(true);
//            else
//                assertTrue(false);
//        }catch(Exception e){
//            e.printStackTrace();
//            assertTrue(false);
//        }
//    }


    public void testFoo() throws Exception {
        try{
            Foo req = new Foo();
            req.setFooChild1("Hello");
            req.setFooChild2("World!");
            FooResponse resp = stub.echoFoo(req);
            assertEquals(resp.getFooResponse1(), "Hello World!");
        }catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }
    
    public void testVoid() throws Exception {
        try{
            stub.voidTest();
        }catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }
    

}
