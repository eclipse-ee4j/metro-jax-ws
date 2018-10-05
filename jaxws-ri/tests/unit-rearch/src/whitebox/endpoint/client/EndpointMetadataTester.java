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

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.net.URL;
import java.io.*;


/**
 * @author Jitendra Kotamraju
 */
public class EndpointMetadataTester extends TestCase {

    public void testMetadata() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "WEB-INF/wsdl/RpcLitEndpoint.wsdl",
                "WEB-INF/wsdl/RpcLitAbstract.wsdl",
                "WEB-INF/wsdl/RpcLitEndpoint.xsd"
        };
        for (String doc : docs) {
            URL url = cl.getResource(doc);
            metadata.add(new StreamSource(url.openStream(), url.toExternalForm()));
        }
        endpoint.setMetadata(metadata);
        endpoint.publish(address);
        URL pubUrl = new URL(address + "?wsdl");
        boolean gen = isGenerated(pubUrl.openStream());
        assertFalse(gen);
        URL absUrl = new URL(address + "?wsdl=1");
        gen = isGenerated(absUrl.openStream());
        assertFalse(gen);
        URL xsdUrl = new URL(address + "?xsd=1");
        gen = isGenerated(xsdUrl.openStream());
        assertFalse(gen);
        endpoint.stop();
    }

    public void testJCKMetadata() throws IOException {
        int port = Util.getFreePort();
        String address = "http://localhost:" + port + "/jck";
        Endpoint endpoint = Endpoint.create(new JCKEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "WEB-INF/wsdl/jck.wsdl",
        };
        for (String doc : docs) {
            URL url = cl.getResource(doc);
            metadata.add(new StreamSource(url.openStream(), url.toExternalForm()));
        }

        endpoint.setMetadata(metadata);
        endpoint.publish(address);
        URL pubUrl = new URL(address + "?wsdl");
        boolean gen = isGenerated(pubUrl.openStream());
        assertFalse(gen);
        endpoint.stop();

    }

    public void testBadMetadata() throws IOException {
        int port = Util.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "WEB-INF/wsdl/RpcLitBadEndpoint.wsdl",
                "WEB-INF/wsdl/RpcLitAbstract.wsdl",
                "WEB-INF/wsdl/RpcLitEndpoint.xsd"
        };
        for (String doc : docs) {
            URL url = cl.getResource(doc);
            metadata.add(new StreamSource(url.openStream(), url.toExternalForm()));
        }
        endpoint.setMetadata(metadata);

        try {
            endpoint.publish(address);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
            assertTrue(true);
            return;
        }
    }


    public void testAbstractWsdl() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "WEB-INF/wsdl/RpcLitAbstract.wsdl",
                "WEB-INF/wsdl/RpcLitEndpoint.xsd"
        };
        for (String doc : docs) {
            URL url = cl.getResource(doc);
            metadata.add(new StreamSource(url.openStream(), url.toExternalForm()));
        }
        endpoint.setMetadata(metadata);
        endpoint.publish(address);
        URL pubUrl = new URL(address + "?wsdl");
        boolean gen = isGenerated(pubUrl.openStream());
        assertTrue(gen);
        URL absUrl = new URL(address + "?wsdl=1");
        gen = isGenerated(absUrl.openStream());
        assertFalse(gen);
        URL xsdUrl = new URL(address + "?xsd=1");
        gen = isGenerated(xsdUrl.openStream());
        assertFalse(gen);
        endpoint.stop();
    }

    public void testXsd() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "WEB-INF/wsdl/RpcLitEndpoint.xsd"
        };
        for (String doc : docs) {
            URL url = cl.getResource(doc);
            metadata.add(new StreamSource(url.openStream(), url.toExternalForm()));
        }
        endpoint.setMetadata(metadata);
        endpoint.publish(address);
        URL pubUrl = new URL(address + "?wsdl");
        boolean gen = isGenerated(pubUrl.openStream());
        assertTrue(gen);
        URL absUrl = new URL(address + "?wsdl=1");
        gen = isGenerated(absUrl.openStream());
        assertTrue(gen);
//        URL xsdUrl = new URL(address + "?xsd=1");
//        gen = isGenerated(xsdUrl.openStream());
//        assertFalse(gen);
        endpoint.stop();
    }

    public void testDuplicateConcreteWsdl() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "WEB-INF/wsdl/RpcLitEndpoint.wsdl",
                "WEB-INF/wsdl/RpcLitAbstract.wsdl",
                "WEB-INF/wsdl/RpcLitEndpoint.xsd"
        };
        for (String doc : docs) {
            URL url = cl.getResource(doc);
            metadata.add(new StreamSource(url.openStream(), url.toExternalForm()));
        }
        URL url = cl.getResource(docs[0]);
        metadata.add(new StreamSource(url.openStream(), url.toExternalForm() + "1"));
        endpoint.setMetadata(metadata);
        try {
            endpoint.publish(address);
            assertFalse(true);
        } catch (Exception e) {
            // Duplicate concrete WSDL generates exception
            // Intentionally leaving empty
        }
    }

    public void testDuplicateAbstractWsdl() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "WEB-INF/wsdl/RpcLitEndpoint.wsdl",
                "WEB-INF/wsdl/RpcLitAbstract.wsdl",
                "WEB-INF/wsdl/RpcLitEndpoint.xsd"
        };
        for (String doc : docs) {
            URL url = cl.getResource(doc);
            metadata.add(new StreamSource(url.openStream(), url.toExternalForm()));
        }
        URL url = cl.getResource(docs[1]);
        metadata.add(new StreamSource(url.openStream(), url.toExternalForm() + "1"));
        endpoint.setMetadata(metadata);
        try {
            endpoint.publish(address);
            assertFalse(true);
        } catch (Exception e) {
            // Duplicate abstract WSDL generates exception
            // Intentionally leaving empty
        }
    }

    public boolean isGenerated(InputStream in) throws IOException {
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        String str;
        while ((str = rdr.readLine()) != null) {
            if (str.indexOf("NOT_GENERATED") != -1) {
                return false;
            }

        }
        return true;
    }
}

