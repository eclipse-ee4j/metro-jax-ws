/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint.client;

import junit.framework.TestCase;

import javax.xml.ws.Endpoint;
import javax.xml.ws.http.HTTPBinding;
import java.util.concurrent.Executor;
import java.net.HttpURLConnection;
import java.net.URL;

import testutil.PortAllocator;

/**
 * @author Jitendra Kotamraju
 */
public class EndpointExecutorTest extends TestCase {

    public void testExecutor() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:"+port+"/exe";
        Endpoint e = Endpoint.create(HTTPBinding.HTTP_BINDING, new MyProvider());
        MyExecutor executor = new MyExecutor();
        e.setExecutor(executor);
        e.publish(address);
        // Make GET request for WSDL. But no WSDL is published for this endpoint
        assertEquals(
            getHttpStatusCode(address), HttpURLConnection.HTTP_NOT_FOUND);
        // Check whether MyExecutor is invoked or not
        assertTrue(executor.isExecuted());
        e.stop();
    }

    private static class MyExecutor implements Executor {
        boolean executed;
        public boolean isExecuted() {
            return executed;
        }

        public void execute(Runnable command) {
            executed = true;
            command.run();
        }
    }

    private int getHttpStatusCode(String address) throws Exception {
        URL url = new URL(address+"?wsdl");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.connect();
        return con.getResponseCode();
    }

}

