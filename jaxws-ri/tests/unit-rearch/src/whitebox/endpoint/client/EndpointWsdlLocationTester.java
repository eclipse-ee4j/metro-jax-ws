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
import javax.xml.transform.stream.StreamSource;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.soap.SOAPBinding;


/**
 * @author Jitendra Kotamraju
 */
public class EndpointWsdlLocationTester extends TestCase {

    // endpoint has wsdlLocation="...". It publishes the same wsdl, metadata
    // docs
    public void testWsdlLocation() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpointWsdlLocation());
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
            "WEB-INF/wsdl/RpcLitAbstract.wsdl",
            "WEB-INF/wsdl/RpcLitEndpoint.xsd"
        };
        List<Source> metadata = new ArrayList<Source>();
        for(String doc : docs) {
            URL url = cl.getResource(doc);
            metadata.add(new StreamSource(url.openStream(), url.toExternalForm()));
        }
        endpoint.setMetadata(metadata);
        endpoint.publish(address);
        URL pubUrl = new URL(address+"?wsdl");
        boolean gen = isGenerated(pubUrl.openStream());
        assertFalse(gen);
        pubUrl = new URL(address+"?wsdl=1");
        gen = isGenerated(pubUrl.openStream());
        assertFalse(gen);
        pubUrl = new URL(address+"?xsd=1");
        gen = isGenerated(pubUrl.openStream());
        assertFalse(gen);
        invoke(address);
        endpoint.stop();
    }

    // The published WSDL is wrong as we are not setting metadata
    public void testWsdlLocation1() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpointWsdlLocation());
        endpoint.publish(address);
        URL pubUrl = new URL(address+"?wsdl");
        boolean gen = isGenerated(pubUrl.openStream());
        assertFalse(gen);
        invoke(address);
        endpoint.stop();
    }

    public void testHtmlPage() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpointWsdlLocation());
        endpoint.publish(address);
        URL pubUrl = new URL(address);
        URLConnection con = pubUrl.openConnection();
        InputStream is = con.getInputStream();
        int ch;
        while((ch=is.read()) != -1);
        assertTrue(con.getContentType().contains("text/html"));
        endpoint.stop();
    }

    public boolean isGenerated(InputStream in) throws IOException {
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        String str;
        while ((str=rdr.readLine()) != null) {
            if (str.indexOf("NOT_GENERATED") != -1) {
                return false;
            }

        }
        return true;
    }

    // ns0=http://echo.org is according to RpcLitEndpoint.wsdl binding
    private void invoke(String address) { 
        // access service
        QName portName = new QName("http://echo.org/", "RpcLitPort");
        QName serviceName = new QName("http://echo.org/", "RpcLitEndpoint");
        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, address);
        Dispatch<Source> d = service.createDispatch(portName, Source.class, Service.Mode.PAYLOAD);
        String body  = "<ns0:echoInteger xmlns:ns0=\"http://echo.input.binding.org/\"><arg0>2</arg0></ns0:echoInteger>";
        Source request = new StreamSource(new ByteArrayInputStream(body.getBytes()));
        Source response = d.invoke(request);
        request = new StreamSource(new ByteArrayInputStream(body.getBytes()));
        response = d.invoke(request);
    }


    @jakarta.jws.WebService(name="RpcLit", serviceName="RpcLitEndpoint",
        portName="RpcLitPort", targetNamespace="http://echo.org/",
        endpointInterface="whitebox.endpoint.client.RpcLitEndpointIF", 
        wsdlLocation="WEB-INF/wsdl/RpcLitEndpoint.wsdl")
    @jakarta.jws.soap.SOAPBinding(style=jakarta.jws.soap.SOAPBinding.Style.RPC)
    public static class RpcLitEndpointWsdlLocation {

        public int echoInteger(int arg0) {
            return arg0;
        }
    }

}

