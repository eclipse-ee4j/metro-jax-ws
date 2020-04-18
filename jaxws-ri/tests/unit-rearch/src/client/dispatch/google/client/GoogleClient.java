/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.google.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.http.HTTPBinding;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * RESTful client for Google Base web service.
 *
 * @author Santiago.PericasGeertsen@sun.com
 */
public class GoogleClient extends TestCase {

    // see google-custom-search.txt in project root ...
    private static final String KEY = "AIzaSyAuu1mWRLODp-bQPod76AXUf-ih6gZsrRQ";
    private static final String CX = "007386487642334705425:rffb5lj8r98";

    private static final QName GOOGLE_PORT_NAME = new QName("google_port", "http://google.com/");
    private static final QName GOOGLE_SERVICE_NAME = new QName("google", "http://google.com/");

    @Override
    protected void setUp() throws Exception {
        // Map<String,String> env = System.getenv();
        // System.setProperty("proxyHost", env.get("proxyHost"));
        // System.setProperty("proxyPort", env.get("proxyPort"));
    }

    public void testGoogleClient() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("Remote Google Service only exiting..");
            return;
        }

        // Create resource representation
        URI address = getGoogleURL("SAAJ");
        System.out.println("Getting URL = '" + address + "' ...");

        // Create Dispatch object and invoke WS
        Dispatch<Source> d = createDispatch(address);
        Source result = d.invoke(null);

        // Output result returned from service
        outputSource(result);
    }

    public static URI getGoogleURL(String query) throws URISyntaxException {
        return new URI("https", null, "www.googleapis.com", 443, "/customsearch/v1", "key=" + KEY + "&cx=" + CX + "&q=" + query + "&alt=atom", null);
    }

    private Dispatch<Source> createDispatch(URI uri) {

        // Create service and port to obtain Dispatch instance
        Service s = jakarta.xml.ws.Service.create(GOOGLE_SERVICE_NAME);
        s.addPort(GOOGLE_PORT_NAME, HTTPBinding.HTTP_BINDING, uri.toString());

        Dispatch<Source> d = s.createDispatch(GOOGLE_PORT_NAME, Source.class, Service.Mode.PAYLOAD);
        d.getRequestContext().put(MessageContext.HTTP_REQUEST_METHOD, "GET");

        return d;
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
