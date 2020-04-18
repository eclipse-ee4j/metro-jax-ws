/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.wsdl_hello_lit_fault.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import jakarta.xml.ws.AsyncHandler;
import jakarta.xml.ws.Response;
import jakarta.xml.ws.soap.SOAPFaultException;
import javax.xml.namespace.QName;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.rmi.RemoteException;


/**
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    private static Hello stub;

    public HelloLiteralTest(String name) throws Exception{
        super(name);
//        stub = (Hello)ClientServerTestUtil.getPort(Hello_Service.class, Hello.class, new QName("urn:test", "HelloPort"));
        Hello_Service service = new Hello_Service();
        stub = service.getHelloPort();
        ClientServerTestUtil.setTransport(stub);
    }

    public void testHello() throws Exception {
          try{
              String arg = "foo";
              String extra = "bar";
              Hello_Type req = new Hello_Type();
              req.setArgument(arg);req.setExtra(extra);
              HelloOutput response = stub.hello(req);
          } catch(Exception e){
              System.out.println("e is " + e.getClass().getName());
              assertTrue(e instanceof HelloFault);
          }
      }

     public void testHelloAsyncPoll() throws Exception {
          try{
              System.out.println("testHelloAsyncPoll");
              System.out.println("==================");
              String arg = "foo";
              String extra = "bar";
              Hello_Type req = new Hello_Type();
              req.setArgument(arg);req.setExtra(extra);
              Response<HelloOutput> response = stub.helloAsync(req);
              System.out.print("\npolling for response ...");
              while (!response.isDone()) {
                  //System.out.print(".");
              }
              HelloOutput output = response.get();
              assertTrue(output == null);
          } catch(Exception e){
//              e.printStackTrace();
               assertTrue(e instanceof ExecutionException);
              assertTrue(e.getCause() instanceof HelloFault);
          }
      }

    public void testHelloAsyncCallback() throws Exception {
        try {
            String arg = "foo";
            String extra = "bar";
            Hello_Type req = new Hello_Type();
            req.setArgument(arg);
            req.setExtra(extra);
            Future<?> response = stub.helloAsync(req, new HelloCallbackHandler());
            System.out.print("\nWaiting for CallbackHandler to complete...");
            while (!response.isDone()) {
            }
            //System.out.print(".");
        } catch (Exception e) {
            //e.printStackTrace();
            assertFalse(true);
        }
    }


class HelloCallbackHandler extends TestCase implements AsyncHandler<HelloOutput> {

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.rpc.AsyncHandler#handleResponse(javax.xml.rpc.Response)
     */
    public void handleResponse(Response<HelloOutput> response) {
        System.out.println("In asyncHandler");
        try {
            HelloOutput output = response.get();
            //assertEquals("foo", output.getArgument());
            //assertEquals("bar", output.getExtra());
        } catch (ExecutionException e) {
            System.out.println("ExecutionException thrown");
            assertTrue(e.getCause() instanceof HelloFault);
            assertTrue(true);
            //e.printStackTrace();
        } catch (InterruptedException e) {
            assertTrue(false);
            // e.printStackTrace();
        } catch (Exception ex) {
            System.out.println("e is " + ex.getClass().getName());
            ex.printStackTrace();
        }
    }
}
}
