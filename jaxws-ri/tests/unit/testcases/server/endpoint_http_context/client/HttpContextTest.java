/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint_http_context.client;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import com.sun.xml.ws.transport.http.HttpAdapter;
import junit.framework.TestCase;
import testutil.ClientServerTestUtil;
import testutil.PortAllocator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.soap.SOAPBinding;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jitendra Kotamraju
 */
public class HttpContextTest extends TestCase {

    public void testContext() throws Exception {
        try {
            HttpAdapter.setPublishStatus(true);
            int port = PortAllocator.getFreePort();
            String address = "http://localhost:" + port + "/hello";

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 5);
            ExecutorService threads = Executors.newFixedThreadPool(5);
            server.setExecutor(threads);
            server.start();

            Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
            HttpContext context = server.createContext("/hello");
            endpoint.publish(context);

            // access HTML page and check the wsdl location
            String wsdlAddress = address + "?wsdl";
            String str = getHtmlPage(address);
            assertTrue(str + "doesn't have " + wsdlAddress, str.contains(wsdlAddress));

            // See if WSDL is published at the correct address
            int code = getHttpStatusCode(wsdlAddress);
            assertEquals(HttpURLConnection.HTTP_OK, code);

            // Check abstract wsdl address
            String wsdlImportAddress = getWsdlImportAddress(wsdlAddress);
            assertEquals(address + "?wsdl=1", wsdlImportAddress);

            // See if abstract WSDL is published at the correct address
            code = getHttpStatusCode(wsdlImportAddress);
            assertEquals(HttpURLConnection.HTTP_OK, code);

            // Check published web service soap address
            String pubAddress = getSoapAddress(wsdlAddress);
            assertEquals(address, pubAddress);

            // Invoke service
            invoke(address);

            endpoint.stop();

            server.stop(1);
            threads.shutdown();
        } finally {
            HttpAdapter.setPublishStatus(false);
        }
    }

    private void invoke(String address) {
        // access service
        QName portName = new QName("http://echo.org/", "RpcLitPort");
        QName serviceName = new QName("http://echo.org/", "RpcLitEndpoint");
        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, address);
        Dispatch<Source> d = service.createDispatch(portName, Source.class, Service.Mode.PAYLOAD);
        String body  = "<ns0:echoInteger xmlns:ns0=\"http://echo.abstract.org/\"><arg0>2</arg0></ns0:echoInteger>";
        Source request = new StreamSource(new ByteArrayInputStream(body.getBytes()));
        d.invoke(request);
    }

    private int getHttpStatusCode(String address) throws Exception {
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.connect();
        return con.getResponseCode();
    }

    private String getHtmlPage(String address) throws Exception {
        URL url = new URL(address);
        BufferedReader rdr = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str=rdr.readLine()) != null) {
            sb.append(str);
        }
        rdr.close();
        return sb.toString();
    }


    private String getWsdlImportAddress(String wsdlAddress) throws Exception {
        URL url = new URL(wsdlAddress);
        XMLStreamReader rdr = XMLInputFactory.newInstance().createXMLStreamReader(url.openStream());
        try {
            while(rdr.hasNext()) {
                int event = rdr.next();
                if (event == XMLStreamReader.START_ELEMENT) {
                    if (rdr.getName().equals(new QName("http://schemas.xmlsoap.org/wsdl/", "import"))) {
                        return rdr.getAttributeValue(null, "location");
                    }
                }
            }
        } finally {
            rdr.close();
        }
        return null;
    }

    private String getSoapAddress(String wsdlAddress) throws Exception {
        URL url = new URL(wsdlAddress);
        XMLStreamReader rdr = XMLInputFactory.newInstance().createXMLStreamReader(url.openStream());
        try {
            while(rdr.hasNext()) {
                int event = rdr.next();
                if (event == XMLStreamReader.START_ELEMENT) {
                    if (rdr.getName().equals(new QName("http://schemas.xmlsoap.org/wsdl/soap/", "address"))) {
                        return rdr.getAttributeValue(null, "location");
                    }
                }
            }
        } finally {
            rdr.close();
        }
        return null;
    }

}

