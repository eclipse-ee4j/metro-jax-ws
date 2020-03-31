/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.misc.client;

import java.io.*;
import java.util.concurrent.*;
import java.util.Random;
import junit.framework.*;
import testutil.ClientServerTestUtil;
import testutil.HTTPResponseInfo;
import jakarta.xml.soap.*;
import javax.xml.namespace.QName;


/**
 *
 * @author JAX-RPC RI Development Team
 */
public class LifeCycleTest extends TestCase {
    
    private static final int NO_THREADS = 10;
    private static final int NO_REQS = 300;
    private static int NO_RESPS = 0;
	private HelloPortType stub;

    public LifeCycleTest(String name) throws Exception {
        super(name);
		getStub();
    }

    HelloPortType getStub() throws Exception {
        if (stub == null) {
            HelloService service = new HelloService();
            stub = service.getHelloPort();
            ClientServerTestUtil.setTransport(stub);
        }
        return stub;
    }

    public void testEcho2() throws Exception {
        if (ClientServerTestUtil.useLocal()) {
            System.out.println("skipping http only test");
            return;
        }
        ExecutorService pool = Executors.newFixedThreadPool(NO_THREADS);
        try {
           for (int i=0; i < NO_REQS; i++) {
              pool.execute(new Runnable() {
                  public void run() {
                      try {
                          invoke();
                          Thread.sleep(10);
                      } catch(Exception e) {
                          assertTrue(false);
                      }
                  }
                  public void invoke() throws Exception {
                      int rand = new Random(System.currentTimeMillis()).nextInt(1000);
                      String var1 = "bar"+rand;
                      String response = stub.echo2(var1);
                      assertEquals(var1, response);
                      synchronized(MultiThreadTest.class) {
                          ++NO_RESPS;
                      }
                 }
             });
           }
           // Give sufficient time to gather all responses
           pool.awaitTermination(20000L, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            assertTrue(false);
        } finally {
            pool.shutdown();
        }
        synchronized(MultiThreadTest.class) {
        	assertEquals(NO_REQS, NO_RESPS);
        }
    }
}
