/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package customization.jaxb_custom.doclit.client;

import junit.framework.TestCase;
import junit.framework.Assert;
import testutil.ClientServerTestUtil;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Holder;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    private static HelloWorld stub;

    public HelloLiteralTest(String name) throws Exception{
        super(name);
        CustomService service = new CustomService();
        stub = service.getCustomizedPort();
        ClientServerTestUtil.setTransport(stub);
//            stub = (HelloWorld) ClientServerTestUtil.getPort(CustomService.class, HelloWorld.class, new QName("urn:test", "HelloPort"));
       
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

    public void testAbstract() throws Exception{
        try{
            MyAbstractType req = new MyAbstractType();
            req.setAbstract("Hello Abstract");
            req.setStatic("Hello Static");
            Holder<String> abstractReq = new Holder<String>("abstract");
            Holder<String> staticReq = new Holder<String>("static");

            stub.helloAbstract(abstractReq, staticReq);
            assertTrue(abstractReq.value.equals("hello abstract!") && staticReq.value.equals("hello static!"));   
        }catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }


    public void testFoo() throws Exception {
        try{
            RenamedFoo req = new RenamedFoo();
            req.setFooChild1("Hello");
            req.setFooChild2("World!");
            FooResponse resp = stub.echoFoo(req);
            assertEquals(resp.getFooResponse1(), "Hello World!");
        }catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

}
