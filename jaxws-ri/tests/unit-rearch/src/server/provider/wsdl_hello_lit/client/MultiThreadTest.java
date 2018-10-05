/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.wsdl_hello_lit.client;

import junit.framework.*;
import testutil.ClientServerTestUtil;
import javax.xml.ws.Service;
import javax.xml.namespace.QName;
import java.io.PrintStream;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.Random;

/*
 * @author Jitendra Kotamraju
 */
public class MultiThreadTest extends TestCase {

    private static final int NO_THREADS = 10;
    private static final int REQS_PER_THREAD = 5000;
    private static int NO_RESPS = 0;

    public void testAsyncMultiThread() throws Exception {
        Hello_Service service = new Hello_Service();
        Hello stub = service.getHelloPort();
        Hello asyncStub = service.getHelloAsyncPort();
        Thread[] threads = new Thread[NO_THREADS];
        for(int i=0; i < NO_THREADS; i++) {
            threads[i] = new Thread(new MultiRunnable(i%2==0?stub:asyncStub));
        }
        for(int i=0; i < NO_THREADS; i++) {
            threads[i].start();
        }
        for(int i=0; i < NO_THREADS; i++) {
            threads[i].join();
        }
        synchronized(HelloLiteralTest.class) {
            assertEquals(NO_THREADS*REQS_PER_THREAD, NO_RESPS);
        }
    }

    private static class MultiRunnable implements Runnable {
        Hello stub;

        MultiRunnable(Hello stub) {
            this.stub = stub;
        }

        public void run() {
            for(int i=0; i < REQS_PER_THREAD; i++) {
            	int rand = new Random(System.currentTimeMillis()).nextInt(1000);
                Hello_Type req = new Hello_Type();
		String arg = "arg"+rand;
		String extra = "extra"+rand;
                req.setArgument(arg);req.setExtra(extra);
                HelloResponse response = stub.hello(req, req);
                assertEquals(arg, response.getArgument());
                assertEquals(extra, response.getExtra());
                synchronized(HelloLiteralTest.class) {
                    ++NO_RESPS;
                }
                rand = rand/20;
                try { Thread.sleep(rand); } catch(InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

}
