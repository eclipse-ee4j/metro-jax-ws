/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.rest.client;

import testutil.ClientServerTestUtil;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import junit.framework.TestCase;
import org.w3c.dom.Node;
import java.net.URL;
import java.net.HttpURLConnection;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.io.OutputStream;
import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeBodyPart;
import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeMultipart;
import com.sun.xml.messaging.saaj.packaging.mime.internet.InternetHeaders;
import javax.activation.DataSource;
import javax.xml.ws.WebServiceException;

import com.sun.xml.ws.transport.Headers;

public class URLConnectionTest extends TestCase {

    private String endpointAddress =
        "http://localhost:8080/jaxrpc-provider_tests_rest/hello/restds";
    
    // HTTP GET to get image/jpeg
    public void testGetImageFromDS() throws Exception {
        if (ClientServerTestUtil.useLocal()) {
            return;
        }
        URL url = new URL(endpointAddress+"/java.jpg");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        
        // processing response headers
System.out.println("HEaders="+con.getHeaderFields());
        Map<String, List<String>> hdrs =  getCaseInsensitiveHeaders(
            con.getHeaderFields());
        List<String> hdrValues = hdrs.get("content-type");
        assertTrue(hdrValues != null);
        assertEquals("image/jpeg", hdrValues.get(0));

		// TODO validate content
    }

    private Map<String, List<String>> getCaseInsensitiveHeaders(
        Map<String, List<String>> in) {

        Headers out = new Headers();
        // Doesn't work
        //headers.putAll(in);
        if (in != null) {
            for(Map.Entry<String, List<String>> e : in.entrySet()) {
                out.put(e.getKey(), e.getValue());
            }
        }
        return out;
    }


}
