/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import testutil.ClientServerTestUtil;
import testutil.HTTPResponseInfo;

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

import testutil.PortAllocator;

/**
 * @author Jitendra Kotamraju
 */
public class HttpStatusCodes extends TestCase {

    public void testUnsupportedMediaType() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:"+port+"/hello";

        Endpoint endpoint = Endpoint.publish(address, new RpcLitEndpoint());

        // Send a request with "a/b" as Content-Type
        String message = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'><soapenv:Body/></soapenv:Envelope>";
        HTTPResponseInfo rInfo =
            ClientServerTestUtil.sendPOSTRequest(address, message, "a/b" );
        int code = rInfo.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_UNSUPPORTED_TYPE, code);
        endpoint.stop();
    }

    public void testUnsupportedMediaType1() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:"+port+"/hello";

        Endpoint endpoint = Endpoint.publish(address, new RpcLitEndpoint());

        // Send a request with "a/b" as Content-Type
        String message = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'><soapenv:Body/></soapenv:Envelope>";
        HTTPResponseInfo rInfo =
            ClientServerTestUtil.sendPOSTRequest(address, message, null);
        int code = rInfo.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_UNSUPPORTED_TYPE, code);
        endpoint.stop();
    }

}
