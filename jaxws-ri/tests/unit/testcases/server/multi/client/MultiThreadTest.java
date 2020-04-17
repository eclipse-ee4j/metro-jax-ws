/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.multi.client;

import com.sun.xml.ws.Closeable;
import junit.framework.TestCase;

import jakarta.xml.ws.BindingProvider;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Jitendra Kotamraju
 */
@SuppressWarnings("empty-statement")
public class MultiThreadTest extends TestCase {
    
    private static final int NO_THREADS = 20;
    private static final int NO_REQS = 50000;
    private int noReqs = 0;
    private int noResps = 0;
    private HelloPortType stub;

    public MultiThreadTest(String name) throws Exception {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        HelloService service = new HelloService();
        stub = service.getHelloPort();

        // initialize
        Map<String,Object> ctx = ((BindingProvider) stub).getRequestContext();
        ctx.put("whatever", "whatever");
        ctx.put("whatever2", "whatever2");

        // this turns RequestContext to fallback mode
        ctx.keySet();
    }
    
    @Override
    public void tearDown() throws Exception {
        ((Closeable)stub).close();
        
    }
    
    public void testMultiThread() throws Exception {
        System.out.println("MultiThreadTest: testMultiThread");
        synchronized(this) {
            noReqs = NO_REQS; noResps = 0;
        }
        Thread[] threads = new Thread[NO_THREADS];
        for(int i=0; i < NO_THREADS; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0; i < noReqs/NO_THREADS; i++) {
                        try {
                            invoke();
                        } catch(Exception e) {
                            fail(e.getMessage());
                        }
                    }
                }
            });
        }
        for(int i=0; i < NO_THREADS; i++) {
            threads[i].start();
        }
        for(int i=0; i < NO_THREADS; i++) {
            threads[i].join();
        }
        synchronized(this) {
            assertEquals(noReqs, noResps);
        }
    }


    public void invoke() throws Exception {
	int rand = new Random(System.currentTimeMillis()).nextInt(1000);
	String var1 = "foo"+rand;
	String var2 = "bar"+rand;
	ObjectFactory of = new ObjectFactory();
	EchoType request = of.createEchoType();
	request.setReqInfo(var1);
	Echo2Type header2 = of.createEcho2Type();
	header2.setReqInfo(var2);
	EchoResponseType response = stub.echo(request, request, header2);
	assertEquals(var1, stub.echo2(var1));
	assertEquals(var1+var1+var2, (response.getRespInfo()));
	synchronized(this) {
	    ++noResps;
	}
    }

    public void testThreadPool() throws Exception {
        System.out.println("MultiThreadTest: testThreadPool");
        ExecutorService service = new ThreadPoolExecutor(NO_THREADS/2, NO_THREADS,
            30L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()); 
        synchronized(this) {
            noReqs = NO_REQS; noResps = 0;
        }
        doTestWithThreadPool(service, noReqs);
        service.shutdown();
        while(!service.awaitTermination(7L, TimeUnit.SECONDS));
        synchronized(this) {
            assertEquals(noReqs, noResps);
        }
    }

    public void testFixedThreadPool() throws Exception {
        System.out.println("MultiThreadTest: testFixedThreadPool");
        ExecutorService service = Executors.newFixedThreadPool(NO_THREADS);
        synchronized(this) {
            noReqs = NO_REQS; noResps = 0;
        }
        doTestWithThreadPool(service, noReqs);
        service.shutdown();
        while(!service.awaitTermination(5L, TimeUnit.SECONDS));
        synchronized(this) {
            assertEquals(noReqs, noResps);
        }
    }

    public void testCachedThreadPool() throws Exception {
        System.out.println("MultiThreadTest: testCachedThreadPool");
        ExecutorService service = Executors.newCachedThreadPool();
        synchronized(this) {
            noReqs = 50; noResps = 0;
        }
        doTestWithThreadPool(service, noReqs);
        service.shutdown();
        while(!service.awaitTermination(5L, TimeUnit.SECONDS));
        synchronized(this) {
            assertEquals(noReqs, noResps);
        }
    }

    private void doTestWithThreadPool(ExecutorService service, int noReqs) throws Exception {
        for(int i=0; i < noReqs; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        invoke();
                    } catch(Exception e) {
                        fail(e.getMessage());
                    }
                }
            });
        }
    }

}
