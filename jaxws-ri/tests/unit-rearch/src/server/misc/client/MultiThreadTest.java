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

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jitendra Kotamraju
 */
public class MultiThreadTest extends TestCase {

    private static final Logger LOGGER = Logger.getLogger(MultiThreadTest.class.getName());
    private static final int NO_THREADS = 20;

    private int noReqs = 0;
    private int noResps = 0;

    private final HelloPortType stub;

    public MultiThreadTest(String name) throws Exception {
        super(name);
        HelloService service = new HelloService();
        stub = service.getHelloPort();
        ClientServerTestUtil.setTransport(stub);
    }

    public void testMultiThread() throws Exception {
        synchronized (this) {
            noReqs = 50000;
            noResps = 0;
        }
        Thread[] threads = new Thread[NO_THREADS];
        for (int i = 0; i < NO_THREADS; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < noReqs / NO_THREADS; i++) {
                        try {
                            invoke();
                            synchronized (MultiThreadTest.this) {
                                ++noResps;
                            }
                        } catch (Exception e) {
                            String msg = "server.multi.client.MultiThreadTest.testMultiThread: invokation failed ";
                            LOGGER.log(Level.SEVERE, msg, e);
                            fail(msg + " " + e.getMessage());
                        }
                    }
                }
            });
        }
        for (int i = 0; i < NO_THREADS; i++) {
            threads[i].start();
        }
        for (int i = 0; i < NO_THREADS; i++) {
            threads[i].join();
        }
        synchronized (this) {
            assertEquals(noReqs, noResps);
        }
    }


    public void invoke() throws Exception {
        //Thread.sleep(new Random(System.currentTimeMillis()).nextInt(50));
        int rand = new Random(System.currentTimeMillis()).nextInt(1000);
        String var1 = "foo" + rand;
        String var2 = "bar" + rand;
        ObjectFactory of = new ObjectFactory();
        EchoType request = of.createEchoType();
        request.setReqInfo(var1);
        Echo2Type header2 = of.createEcho2Type();
        header2.setReqInfo(var2);
        EchoResponseType response = stub.echo(request, request, header2);
        assertEquals(var1, stub.echo2(var1));
        assertEquals(var1 + var1 + var2, (response.getRespInfo()));
    }

    public void testThreadPool() throws Exception {
        ExecutorService service = new ThreadPoolExecutor(NO_THREADS / 2, NO_THREADS,
                30L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        synchronized (this) {
            noReqs = 50000;
            noResps = 0;
        }
        doTestWithThreadPool(service, noReqs);
        service.shutdown();
        while (!service.awaitTermination(5L, TimeUnit.SECONDS)) ;
        synchronized (this) {
            assertEquals(noReqs, noResps);
        }
    }

    public void testFixedThreadPool() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(NO_THREADS);
        synchronized (this) {
            noReqs = 50000;
            noResps = 0;
        }
        doTestWithThreadPool(service, noReqs);
        service.shutdown();
        while (!service.awaitTermination(5L, TimeUnit.SECONDS)) ;
        synchronized (this) {
            assertEquals(noReqs, noResps);
        }
    }

    public void testCachedThreadPool() throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        synchronized (this) {
            noReqs = 50;
            noResps = 0;
        }
        doTestWithThreadPool(service, noReqs);
        service.shutdown();
        while (!service.awaitTermination(5L, TimeUnit.SECONDS)) ;
        synchronized (this) {
            assertEquals(noReqs, noResps);
        }
    }

    private void doTestWithThreadPool(ExecutorService service, int noReqs) throws Exception {
        for (int i = 0; i < noReqs; i++) {
            service.execute(new Runnable() {
                public void run() {
                    try {
                        invoke();
                        synchronized (MultiThreadTest.this) {
                            ++noResps;
                        }
                    } catch (Exception e) {
                        String msg = "server.multi.client.MultiThreadTest.testMultiThread: invokation failed ";
                        LOGGER.log(Level.SEVERE, msg, e);
                        fail(msg + " " + e.getMessage());
                    }
                }
            });
        }
    }

}
