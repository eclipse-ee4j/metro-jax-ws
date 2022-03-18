/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.query_string.client;

import junit.framework.TestCase;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import jakarta.xml.ws.*;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.http.HTTPBinding;

import testutil.ClientServerTestUtil;

/**
 * RESTful client for Google Base web service.
 *
 * @author
 */
public class QueryStringClient extends TestCase {

    public static void main(String[] args) throws Exception {
        QueryStringClient client = new QueryStringClient("QueryStringClient");

        URI address = new URI("http://localhost:9090/hello");
       //client.testCreateDispatch(address);
        client.testCreateDispatchCase2(address);
    }

    public QueryStringClient(String name){
         super(name);
    }

    public void testQueryStringClient() throws Exception {

        // Create resource representation
        URI address = new URI("http://localhost:8080/hello");
        System.out.println("Getting URL = '" + address + "' ...");
       // testCreateDispatch(address);
        testCreateDispatchCase2(address);
    }

   public void xxtestCreateDispatch(URI uri) {
        // Create service and port to obtain Dispatch instance
        Service s = jakarta.xml.ws.Service.create(
                new QName("http://hello.org", "hello"));
        QName portName = new QName("http://hello.org", "helloport");
        s.addPort(portName, HTTPBinding.HTTP_BINDING,
            uri.toString() + "?%E5%B2%A1%E5%B4%8E");

        // Create Dispatch instance and setup HTTP headers
        Dispatch<Source> d = s.createDispatch(portName,
                Source.class, Service.Mode.PAYLOAD);

         Map<String, Object> requestContext = d.getRequestContext();

        // Set HTTP operation to GET
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, new String("GET"));

        //System.out.println("EndpointAddress " + requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
       try {
       Source result = d.invoke(null);
       } catch (Exception e){
           //ok
       }
        //System.out.println("EndpointAddress " + requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
       assertEquals("http://localhost:8080/hello?%E5%B2%A1%E5%B4%8E", requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));

    }


    public void testCreateDispatchCase2(URI uri) {
           // Create service and port to obtain Dispatch instance
           Service s = jakarta.xml.ws.Service.create(
                   new QName("http://hello.org", "hello"));
           QName portName = new QName("http://hello.org", "helloport");
           s.addPort(portName, HTTPBinding.HTTP_BINDING,
               uri.toString());

           // Create Dispatch instance and setup HTTP headers
           Dispatch<Source> d = s.createDispatch(portName,
                   Source.class, Service.Mode.PAYLOAD);

           Map<String, Object> requestContext = d.getRequestContext();

        // Set HTTP operation to GET
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, new String("GET"));
        requestContext.put(MessageContext.QUERY_STRING,new String("%E5%B2%A1%E5%B4%8E"));
        try {
         Source result = d.invoke(null);
        } catch (Exception ex) {
            //ok
        }
        //System.out.println("EndpointAddress " + requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
        //assertEquals("http://localhost:9090/hello?%E5%B2%A1%E5%B4%8E", requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
       }


    private void setupHTTPHeaders(Dispatch<Source> d) {
        Map<String, Object> requestContext = d.getRequestContext();

        // Set HTTP operation to GET
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, new String("GET"));

        // Setup HTTP headers as required by service
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("X-Google-Key",
            Arrays.asList("key=ABQIAAAA7VerLsOcLuBYXR7vZI2NjhTRERdeAiwZ9EeJWta3L_JZVS0bOBRIFbhTrQjhHE52fqjZvfabYYyn6A"));
        headers.put("Accept", Arrays.asList("application/atom+xml"));
        headers.put("Content-Type", Arrays.asList("application/atom+xml"));
        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    }

    private void outputSource(Source s) throws Exception {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty("indent", "yes");
        File output = new File("google.xml");
        t.transform(s, new StreamResult(output));
        System.out.println("Output written to '" + output.toURL() + "'");
        System.out.println("Done.");
    }
}
