/*
 * Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.processor.modeler.annotation;

import com.sun.tools.ws.wscompile.WsgenTool;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Assert;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class NillableArrayTest extends TestCase {
    public void testNillableArray() {
        File destDir;
        List<String> options;

        destDir = new File(System.getProperty("java.io.tmpdir"), NillableArrayTest.class.getSimpleName());
        destDir.mkdirs();
        options = new ArrayList<>();
        options.add("-d");
        options.add(destDir.getAbsolutePath());
        options.add("-cp");
        options.add(System.getProperty("java.class.path") + File.pathSeparator + System.getProperty("jdk.module.path"));
        options.add("com.sun.tools.ws.test.processor.modeler.annotation.NillableTestWs");
        options.add("-wsdl");

        Properties props = System.getProperties();
        props.setProperty("com.sun.xml.ws.jaxb.allowNonNillableArray","true");

        WsgenTool wsgen = new WsgenTool(System.out);

        try {
            wsgen.run(options.toArray(new String[options.size()]));
            File file = new File(destDir, "NillableTestWsService_schema1.xsd");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            NodeList complexTypesNodes = doc.getElementsByTagName("xs:complexType");
            for( int i = 0; i < complexTypesNodes.getLength(); i++) {

                //the nillable value is not set so nillable attribute in the schema for that array element will be true
                if((complexTypesNodes.item(i).getAttributes().getNamedItem("name").getNodeValue()).equals("setEntitlements")) {
                    Assert.assertEquals("true", complexTypesNodes.item(i).getChildNodes().item(1).getChildNodes().item(1).getAttributes().getNamedItem("nillable").getNodeValue());
                }

               //the nillable value is set to false so nillable attribute in the schema for that element will be null
                if((complexTypesNodes.item(i).getAttributes().getNamedItem("name").getNodeValue()).equals("getEntitlementsResponse")) {
                    Assert.assertNull(complexTypesNodes.item(i).getChildNodes().item(1).getChildNodes().item(1).getAttributes().getNamedItem("nillable"));
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        } finally{
            System.clearProperty("com.sun.xml.ws.jaxb.allowNonNillableArray");
        }
    }
}
