/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.xmlhttp.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.http.HTTPBinding;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


/**
 * @author JAX-RPC RI Development Team
 */
public class XMLHttpClient extends TestCase {

    // see google-custom-search.txt in project root ...
    private static final String KEY = "AIzaSyAuu1mWRLODp-bQPod76AXUf-ih6gZsrRQ";
    private static final String CX = "007386487642334705425:rffb5lj8r98";

    private static final QName GOOGLE_PORT_NAME = new QName("google_port", "http://google.com/");
    private static final QName GOOGLE_SERVICE_NAME = new QName("google", "http://google.com/");

    @Override
    protected void setUp() throws Exception {
//        System.setProperty("proxyHost", "www-proxy.us.oracle.com");
//        System.setProperty("proxyPort", "80");
    }

    /**
     * Currently these test have no assertions to test
     * failures. todo: need way to test pass/fail
     * Currently this needs to be done vissually by viewing output
     */
    public XMLHttpClient(String name) {
        super(name);
    }

    private String sourceToXMLString(Source result) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        OutputStream out = new ByteArrayOutputStream();
        StreamResult streamResult = new StreamResult();
        streamResult.setOutputStream(out);
        transformer.transform(result, streamResult);
        return streamResult.getOutputStream().toString();
    }


    // yahoo service down (was stopped)
    public void testHttpGetMESSAGE() throws TransformerException, URISyntaxException {

        if (ClientServerTestUtil.useLocal()) {
            return;
        }

        String address = getGoogleURL("JAX-WS");
        System.out.println("address = " + address);

        Service s = Service.create(GOOGLE_SERVICE_NAME);
        s.addPort(GOOGLE_PORT_NAME, HTTPBinding.HTTP_BINDING, address);
        Dispatch<Source> d = s.createDispatch(GOOGLE_PORT_NAME, Source.class, Service.Mode.MESSAGE);

        Map<String, Object> requestContext = d.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "GET");
        Source result = d.invoke(null);
        System.out.println(sourceToXMLString(result));
    }

    public static String getGoogleURL(String query) throws URISyntaxException {
        return "https://www.googleapis.com/customsearch/v1?key=" + KEY + "&cx=" + CX + "&q=" + query + "&alt=atom";
    }

    public void testHttpGetPAYLOAD() throws URISyntaxException, TransformerException {

        if (ClientServerTestUtil.useLocal()) {
            return;
        }

        String address = getGoogleURL("JAX-WS");
        System.out.println("address = " + address);

        Service s = Service.create(GOOGLE_SERVICE_NAME);
        s.addPort(GOOGLE_PORT_NAME, HTTPBinding.HTTP_BINDING, address);
        Dispatch<Source> d = s.createDispatch(GOOGLE_PORT_NAME, Source.class, Service.Mode.PAYLOAD);
        d.getRequestContext().put(MessageContext.HTTP_REQUEST_METHOD, "GET");
        Source result = d.invoke(null);
        System.out.println(sourceToXMLString(result));
    }


    public void testXMLEndpointAddress() throws URISyntaxException, TransformerException {

        if (ClientServerTestUtil.useLocal())
            return;

        String address = getGoogleURL("JAX-WS");
        System.out.println("address = " + address);

        XMLHttpClient client = new XMLHttpClient("XMLHttpClient");
        Service s = Service.create(GOOGLE_SERVICE_NAME);
        s.addPort(GOOGLE_PORT_NAME, HTTPBinding.HTTP_BINDING, address);
        Dispatch<Source> d = s.createDispatch(GOOGLE_PORT_NAME, Source.class, Service.Mode.PAYLOAD);
        d.getRequestContext().put(MessageContext.HTTP_REQUEST_METHOD, "GET");

        // Execute the first search
        Source result = d.invoke(null);
        System.out.println("First search result: " + client.sourceToXMLString(result));

        // Execute the second search
        d.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getGoogleURL("JAXB"));
        Source result2 = d.invoke(null);
        System.out.println("Second search result: " + client.sourceToXMLString(result2));
    }

}
