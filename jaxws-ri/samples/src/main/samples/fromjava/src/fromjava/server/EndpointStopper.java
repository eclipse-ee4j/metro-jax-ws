/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EndpointStopper {

    EndpointStopper(final int port, final Endpoint endpoint) throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(port), 5);
        final ExecutorService threads  = Executors.newFixedThreadPool(2);
        server.setExecutor(threads);
        server.start();

        HttpContext context = server.createContext("/stop");
        context.setHandler(new HttpHandler() {
    		public void handle(HttpExchange msg) throws IOException {
				System.out.println("Shutting down the Endpoint");
				endpoint.stop();
				System.out.println("Endpoint is down");
                msg.sendResponseHeaders(200, 0);
                msg.close();
        		server.stop(1);
        		threads.shutdown();
			}
		});
    }
}
