/*
 * Copyright (c) 2020, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.writer;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.Endpoint;

import junit.framework.TestCase;

import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.transport.http.server.EndpointImpl;
import org.junit.Assert;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class PublishWSDLTest extends TestCase {

    public void testPublishWSDL() {
        PublishWSDLTestWs service = new PublishWSDLTestWs();
        String address = "http://localhost:9999/hello";
        Endpoint endpoint = new EndpointImpl(BindingID.parse(service.getClass()), service);
        endpoint.publish(address);
        Properties props = System.getProperties();
        props.setProperty("com.sun.xml.ws.wsdl.externalSchemaLocationURL","true");
        try {
            URL wsdlUrl = new URL(address+"?wsdl");
            InputStream in = wsdlUrl.openStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
            NodeList importNodes = doc.getElementsByTagName("xsd:import");
            Assert.assertEquals("PublishWSDLTestWsService_schema1.xsd", importNodes.item(0).getAttributes().getNamedItem("schemaLocation").getNodeValue());
            endpoint.stop();
        } catch(Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        } finally{
            System.clearProperty("com.sun.xml.ws.wsdl.externalSchemaLocationURL");
        }
    }
} 
