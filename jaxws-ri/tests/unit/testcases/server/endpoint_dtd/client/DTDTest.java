/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint_dtd.client;

import java.io.StringReader;
import java.net.URL;
 
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.Oneway;
import javax.xml.namespace.QName;
import javax.xml.ws.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import javax.xml.stream.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import testutil.ClientServerTestUtil;
import testutil.HTTPResponseInfo;
import testutil.PortAllocator;

import junit.framework.TestCase;

/**
 * Tests for DTD in SOAP requests
 *
 * @author Jitendra Kotamraju
 */
public class DTDTest extends TestCase
{
    private static final String NS = "http://echo.org/";

/*
    public void testXXE() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/xxe";
        Endpoint endpoint = Endpoint.create(new MyEndpoint());
        endpoint.publish(address);
        try {
            HTTPResponseInfo rInfo = sendXXE(address);
            String resp = rInfo.getResponseBody();
            if (resp.contains("root")) {
                fail("XXE attack with external entity is working");
            }
            int code = rInfo.getResponseCode();
            assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, code);
        } finally {
            endpoint.stop();
        }
    }

    private HTTPResponseInfo sendXXE(String address) throws Exception {

        String message = "<?xml version='1.0' ?><!DOCTYPE S:Envelope[<!ENTITY passwd SYSTEM '/etc/passwd'>]><S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'><S:Body><ns0:echoString xmlns:ns0='http://echo.org/'><arg0>&passwd;</arg0></ns0:echoString></S:Body></S:Envelope>";

        return ClientServerTestUtil.sendPOSTRequest(address, message, "text/xml");
    }
*/

    public void testEntity() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:"+port+"/entity";
        Endpoint endpoint = Endpoint.create(new MyEndpoint());
        endpoint.publish(address);
        try {
            HTTPResponseInfo rInfo = sendEntity(address);
            String resp = rInfo.getResponseBody();
            if (resp.contains("x1y1")) {
                fail("Entity is getting resolved");
            }
            int code = rInfo.getResponseCode();
            assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, code);
        } finally {
            endpoint.stop();
        }
    }

    private HTTPResponseInfo sendEntity(String address) throws Exception {
        String message = "<?xml version='1.0' ?><!DOCTYPE S:Envelope[<!ENTITY xy  'x1y1'><!ENTITY xy2 '&xy; &xy;'>]><S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'><S:Body><ns0:echoString xmlns:ns0='http://echo.org/'><arg0>&xy2;</arg0></ns0:echoString></S:Body></S:Envelope>";

        return ClientServerTestUtil.sendPOSTRequest(address, message, "text/xml");
    }

    @WebService(targetNamespace="http://echo.org/")
    @SOAPBinding(style=Style.RPC)
    public static class MyEndpoint {
        public String echoString(String arg) {
            return arg;
        }
    }
}
