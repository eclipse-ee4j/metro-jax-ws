/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.endpoint.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Holder;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.ws.Endpoint;
import javax.xml.transform.Source;
import jakarta.xml.ws.Dispatch;
import javax.xml.transform.stream.StreamSource;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.*;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.BindingProvider;


/**
 * @author Jitendra Kotamraju
 */
public class HttpContextTester extends TestCase {

    public void testContext() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/hello";

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 5);
        ExecutorService threads  = Executors.newFixedThreadPool(5);
        server.setExecutor(threads);
        server.start();

        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        HttpContext context = server.createContext("/hello");
        endpoint.publish(context);
        // Gets WSDL from the published endpoint
        int code = getHttpStatusCode(address);
        assertEquals(HttpURLConnection.HTTP_OK, code);
        endpoint.stop();

        server.stop(1);
        threads.shutdown();
    }

    public void testAuthentication() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/hello";

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 5);
        ExecutorService threads  = Executors.newFixedThreadPool(5);
        server.setExecutor(threads);
        server.start();

        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        HttpContext context = server.createContext("/hello");
        final String realm = "localhost.realm.com";
        context.setAuthenticator(new BasicAuthenticator(realm) {
            public boolean checkCredentials (String username, String password) {
                System.out.println("Authenticator is called");
                if (username.equals("auth-user") && password.equals("auth-pass")) {
                    return true;
                }
                return false;
            }
        });
        endpoint.publish(context);

/*

        Works but the next request hangs
 
        // Gets WSDL from the published endpoint
        int code = getHttpStatusCode(address);
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, code);
 */
        
        // access service
        QName portName = new QName("http://echo.org/", "RpcLitPort");
        QName serviceName = new QName("http://echo.org/", "RpcLitEndpoint");
        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, address);
        Dispatch<Source> d = service.createDispatch(portName, Source.class, Service.Mode.PAYLOAD);
        d.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "auth-user");
        d.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "auth-pass");
        String body  = "<ns0:echoInteger xmlns:ns0=\"http://echo.abstract.org/\"><arg0>2</arg0></ns0:echoInteger>";
        Source request = new StreamSource(new ByteArrayInputStream(body.getBytes()));
        Source response = d.invoke(request);
        request = new StreamSource(new ByteArrayInputStream(body.getBytes()));
        response = d.invoke(request);

        endpoint.stop();
        server.stop(1);
        threads.shutdown();
    }

    private int getHttpStatusCode(String address) throws Exception {
        URL url = new URL(address+"?wsdl");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.connect();
        return con.getResponseCode();
    }

}

