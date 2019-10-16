/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package externalmetadata.fromwsdl.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceFeature;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.oracle.webservices.api.databinding.ExternalMetadataFeature;
import com.oracle.webservices.api.databinding.ExternalMetadataFeature.Builder;

/**
 * simple J2SE tester class for this sample
 */
public class J2SETester {

    public static void main (String[] args) throws Exception {

        File file = new File("etc/external-metadata.xml");
        WebServiceFeature feature = ExternalMetadataFeature.builder().addFiles(file).build();
        Endpoint endpoint = Endpoint.create(new BlackboxServiceImpl(), feature);
        endpoint.publish("http://localhost:8080/jaxws-external-metadata-fromwsdl/WS");

        // Stops the endpoint if it receives request http://localhost:9090/stop
        new EndpointStopper(9090, endpoint);
    }

    static class EndpointStopper {

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

}
