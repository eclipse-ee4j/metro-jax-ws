/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint.client;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.net.URL;
import java.io.*;

import testutil.PortAllocator;

/**
 * @author Jitendra Kotamraju
 */
public class EndpointMetadataTest extends TestCase {

    public void testMetadata() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "RpcLitEndpoint.wsdl",
                "RpcLitAbstract.wsdl",
                "RpcLitEndpoint.xsd"
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
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:" + port + "/jck";
        Endpoint endpoint = Endpoint.create(new JCKEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "jck.wsdl",
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
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "RpcLitBadEndpoint.wsdl",
                "RpcLitAbstract.wsdl",
                "RpcLitEndpoint.xsd"
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
        }
    }


    public void testAbstractWsdl() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "RpcLitAbstract.wsdl",
                "RpcLitEndpoint.xsd"
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
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "RpcLitEndpoint.xsd"
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
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "RpcLitEndpoint.wsdl",
                "RpcLitAbstract.wsdl",
                "RpcLitEndpoint.xsd"
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
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "RpcLitEndpoint.wsdl",
                "RpcLitAbstract.wsdl",
                "RpcLitEndpoint.xsd"
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

    // read the whole document. Otherwise, endpoint.stop() would be
    // done by this test and the server runtime throws an exception
    // when it tries to send the rest of the doc
    public boolean isGenerated(InputStream in) throws IOException {
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        boolean generated = true;
        try {
            String str;
            while ((str = rdr.readLine()) != null) {
                if (str.indexOf("NOT_GENERATED") != -1) {
                    generated = false;
                }
            }
        } finally {
            rdr.close();
        }
        return generated;
    }
}

