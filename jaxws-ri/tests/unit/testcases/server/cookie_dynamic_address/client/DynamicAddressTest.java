/*
 * Copyright (c) 2011, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.cookie_dynamic_address.client;

import junit.framework.TestCase;

import jakarta.xml.ws.BindingProvider;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Map;

/**
 * HTTP HA test when address is set dynamically
 * Issue: GLASSFISH_15938
 *
 * @author Jitendra Kotamraju
 */
public class DynamicAddressTest extends TestCase {


    public DynamicAddressTest(String name) {
        super(name);
    }

    /*
    * With maintain property set to true, session
    * should be maintained.
    */
    public void test3() throws Exception {
        Hello proxy = new HelloService().getHelloPort();
        // So that master tube is not used for invocation
        clearTubePool(proxy);


        Map<String, Object> requestContext =
            ((BindingProvider) proxy).getRequestContext();
        requestContext.put(
            BindingProvider.SESSION_MAINTAIN_PROPERTY, Boolean.TRUE);

        // Set the same address with upper case dynamically
        String address = (String)requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        URL url = new URL(address);
        String host = url.getHost();
        address = address.replace(host, host.toUpperCase());
        requestContext.put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);

        proxy.introduce();

        // So that introduce() tubeline is not used for new invocation
        clearTubePool(proxy);
        assertTrue("client session should be maintained", proxy.rememberMe());
    }

    public void test4() throws Exception {
        final Hello proxy = new HelloService().getHelloPort();

        // Set the same address
        Map<String, Object> requestContext =
            ((BindingProvider) proxy).getRequestContext();
        requestContext.put(
            BindingProvider.SESSION_MAINTAIN_PROPERTY, Boolean.TRUE);
        String address = (String)requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        requestContext.put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);

        // So that master tube is not used for invocation
        clearTubePool(proxy);
        proxy.introduce();

        int NO_THREADS = 4;
        Thread[] threads = new Thread[NO_THREADS];
        MyRunnable[] runs = new MyRunnable[NO_THREADS];
        for(int i=0; i < NO_THREADS; i++) {
            runs[i] = new MyRunnable(proxy);
            threads[i] = new Thread(runs[i]);
        }
        for(int i=0; i < NO_THREADS; i++) {
            threads[i].start();
        }
        for(int i=0; i < NO_THREADS; i++) {
            threads[i].join();
        }
        for(int i=0; i < NO_THREADS; i++) {
            if (runs[i].e != null) {
                throw runs[i].e;
            }
        }
    }

    static class MyRunnable implements Runnable {
        final Hello proxy;
        volatile Exception e;

        MyRunnable(Hello proxy) {
            this.proxy = proxy;
        }

        public void run() {
            try {
                assertTrue("client session should be maintained", proxy.rememberMe());
            } catch(Exception e) {
                this.e = e;
            }
        }
    }

    // Reflection code to set
    // ((com.sun.xml.ws.client.Stub)proxy).tubes.queue = null;
    private void clearTubePool(Object proxy) throws Exception {
        InvocationHandler ih = Proxy.getInvocationHandler(proxy);
        Field tubesField = com.sun.xml.ws.client.Stub.class.getDeclaredField("tubes");
        tubesField.setAccessible(true);
        Object tubes = tubesField.get(ih);
        Field queueField = com.sun.xml.ws.util.Pool.class.getDeclaredField("queue");
        queueField.setAccessible(true);
        queueField.set(tubes, null);
    }
    
}
