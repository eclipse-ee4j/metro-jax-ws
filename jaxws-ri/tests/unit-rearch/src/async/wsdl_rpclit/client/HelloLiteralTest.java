/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.wsdl_rpclit.client;

import junit.framework.Assert;
import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import jakarta.xml.ws.AsyncHandler;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.Response;
import javax.xml.namespace.QName;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    private static Hello stub;

    public HelloLiteralTest(String name) throws Exception{
        super(name);
        Hello_Service service = new Hello_Service();
        stub = service.getHelloPort();
        ClientServerTestUtil.setTransport(stub);
    }

    public void testHello() throws Exception {
        try{
            String arg = "foo";
            String extra = "bar";
            HelloType req = new HelloType();
            req.setArgument(arg);req.setExtra(extra);
            Holder<HelloType> inout = new Holder<HelloType>(req);
            stub.hello(inout);
            HelloType response = inout.value;
            assertEquals(arg, response.getArgument());
            assertEquals(extra, response.getExtra());
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testHelloAsyncPoll() throws Exception {
        try{
            String arg = "foo";
            String extra = "bar";
            HelloType req = new HelloType();
            req.setArgument(arg);req.setExtra(extra);
            Response<HelloType> response = stub.helloAsync(req);
            System.out.print("\npolling for response ...");
            while (!response.isDone()) {
                //System.out.print(".");
            }
            HelloType output = response.get();
            assertEquals(arg, output.getArgument());
            assertEquals(extra, output.getExtra());
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testHelloAsyncCallback() throws Exception {
        try{
            String arg = "foo";
            String extra = "bar";
            HelloType req = new HelloType();
            req.setArgument(arg);req.setExtra(extra);
            Future<?> response = stub.helloAsync(req, new HelloCallbackHandler());
            System.out.print("\nWaiting for CallbackHandler to complete...");
//            while(!response.isDone())
                //System.out.print(".");
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testHello2() throws Exception {
        try{
            String arg = "foo";
            String extra = "bar";
            HelloType req = new HelloType();
            req.setArgument(arg);req.setExtra(extra);
            Holder<HelloType> inout = new Holder<HelloType>(req);
            int age = stub.hello2(inout, "foo");
            HelloType response = inout.value;
            assertEquals(arg, response.getArgument());
            assertEquals(extra, response.getExtra());
            assertEquals(age, 1234);
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testHello2AsyncPoll() throws Exception {
        try{
            String arg = "foo";
            String extra = "bar";
            HelloType req = new HelloType();
            req.setArgument(arg);req.setExtra(extra);
            Response<Hello2Response> response = stub.hello2Async(req, "foo");
            System.out.print("\npolling for response ...");
            while (!response.isDone()) {
                //System.out.print(".");
            }
            Hello2Response resp = response.get();
            HelloType output = resp.getParam();
            int age = resp.getAge();
            assertEquals(arg, output.getArgument());
            assertEquals(extra, output.getExtra());
            assertEquals(age, 1234);
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testHello2AsyncCallback() throws Exception {
        try{
            String arg = "foo";
            String extra = "bar";
            HelloType req = new HelloType();
            req.setArgument(arg);req.setExtra(extra);
            Future<?> response = stub.hello2Async(req, "foo", new Hello2CallbackHandler());
            System.out.print("\nWaiting for CallbackHandler to complete...");
//            while(!response.isDone())
                //System.out.print(".");
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testHello1() throws Exception {
        try{
            String arg = "foo";
            String extra = "bar";
            HelloType req = new HelloType();
            req.setArgument(arg);req.setExtra(extra);
            HelloType reqH = new HelloType();
            reqH.setArgument("header arg");
            reqH.setExtra("header extra");
            Holder<HelloType> resp = new Holder<HelloType>();
            Holder<HelloType> respH = new Holder<HelloType>();
            Holder<String> hdr = new Holder<String>("Hello");
            stub.hello1(req, reqH, resp, respH);
            assertEquals(arg, resp.value.getArgument());
            assertEquals(extra, resp.value.getExtra());
            assertEquals(reqH.getArgument(), respH.value.getArgument());
            assertEquals(reqH.getExtra(), respH.value.getExtra());
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testHello1AsyncPoll() throws Exception {
        try{
            String arg = "foo";
            String extra = "bar";
            HelloType req = new HelloType();
            req.setArgument(arg);req.setExtra(extra);

            HelloType reqH = new HelloType();
            reqH.setArgument("header arg");
            reqH.setExtra("header extra");


            Response<Hello1Response> response = stub.hello1Async(req, reqH);
            System.out.print("\npolling for response ...");
            while (!response.isDone()) {
                //System.out.print(".");
            }
            Hello1Response resp = response.get();
            HelloType out = resp.getRes();
            HelloType outH = resp.getHeader();
            assertEquals(arg, out.getArgument());
            assertEquals(extra, out.getExtra());
            assertEquals(reqH.getArgument(), outH.getArgument());
            assertEquals(reqH.getExtra(), outH.getExtra());
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testHello1AsyncCallback() throws Exception {
        try{
            String arg = "foo";
            String extra = "bar";
            HelloType req = new HelloType();
            req.setArgument(arg);req.setExtra(extra);

            HelloType reqH = new HelloType();
            reqH.setArgument("header arg");
            reqH.setExtra("header extra");

            Future<?> response = stub.hello1Async(req, reqH,
                new Hello1CallbackHandler());
            System.out.print("\nWaiting for CallbackHandler to complete...");
//            while(!response.isDone())
//                System.out.print(".");
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

        //testHello0
    public void testHello0() throws Exception {
        try{
            int response = stub.hello0(54321);
            assertEquals(response, 54321);
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testHello0AsyncPoll() throws Exception {
        try{
            System.out.println("testHello0AsyncPoll");
            System.out.println("==================");

            Response<Integer> response = stub.hello0Async(54321);
            System.out.print("\npolling for response ...");
            while (!response.isDone()) {
                //System.out.print(".");
            }
            Integer output = response.get();
            assertEquals(output.intValue(), 54321);
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testHelloAsyncCallback0() throws Exception {
        try{
            Future<?> response = stub.hello0Async(54321, new Hello0CallbackHandler());
            System.out.print("\nWaiting for CallbackHandler to complete...");
//            while(!response.isDone())
                //System.out.print(".");
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }
    
    public void testHello4AsyncPoll() throws Exception {
        try{
            String arg = "foo";
            String extra = "bar";
            HelloType req = new HelloType();
            req.setArgument(arg);req.setExtra(extra);

            HelloType reqH = new HelloType();
            reqH.setArgument("header arg");
            reqH.setExtra("header extra");


            Response<Hello4Response> response = stub.hello4Async(req, reqH);
            System.out.print("\npolling for response ...");
            while (!response.isDone()) {
                //System.out.print(".");
            }
            Hello4Response resp = response.get();
            HelloType out = resp.getRes();
            HelloType outH = resp.getHeader();
            assertEquals(arg, out.getArgument());
            assertEquals(extra, out.getExtra());
            assertEquals(reqH.getArgument(), outH.getArgument());
            assertEquals(reqH.getExtra(), outH.getExtra());
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }



}

class HelloCallbackHandler extends TestCase implements AsyncHandler<HelloType> {

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.rpc.AsyncHandler#handleResponse(javax.xml.rpc.Response)
     */
    public void handleResponse(Response<HelloType> response) {
        try {            
            HelloType output = response.get();
            assertEquals("foo", output.getArgument());
            assertEquals("bar", output.getExtra());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }                    
}

class Hello0CallbackHandler extends TestCase implements AsyncHandler<Integer> {

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.rpc.AsyncHandler#handleResponse(javax.xml.rpc.Response)
     */
    public void handleResponse(Response<Integer> response) {
        try {
            Integer output = response.get();
            assertEquals(output.intValue(), 54321);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Hello1CallbackHandler extends TestCase implements AsyncHandler<Hello1Response> {

    /*
    * (non-Javadoc)
    * 
    * @see javax.xml.rpc.AsyncHandler#handleResponse(javax.xml.rpc.Response)
    */
    public void handleResponse(Response<Hello1Response> response) {
        try {    
            Hello1Response resp = response.get();
            HelloType out = resp.getRes();
            HelloType outH = resp.getHeader();            
            assertEquals("foo", out.getArgument());
            assertEquals("bar", out.getExtra());
            assertEquals("header arg", outH.getArgument());
            assertEquals("header extra", outH.getExtra());
//            assertEquals("Hello World!", resp.getExtraHeader());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
    
class Hello2CallbackHandler extends TestCase implements AsyncHandler<Hello2Response> {
    /*
    * (non-Javadoc)
    * 
    * @see javax.xml.rpc.AsyncHandler#handleResponse(javax.xml.rpc.Response)
    */
    public void handleResponse(Response<Hello2Response> response) {
        try {    
            Hello2Response resp = response.get();
            HelloType out = resp.getParam();
            int age = resp.getAge();
            assertEquals("foo", out.getArgument());
            assertEquals("bar", out.getExtra());
            assertEquals(age, 1234);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
    
