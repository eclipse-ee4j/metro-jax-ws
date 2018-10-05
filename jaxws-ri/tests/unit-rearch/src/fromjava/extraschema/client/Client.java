/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.extraschema.client;

import junit.framework.TestCase;

import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.util.StringTokenizer;

import com.sun.xml.ws.util.ASCIIUtility;
import com.sun.xml.ws.util.DOMUtil;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Client extends TestCase {
    public Client(String name) {
        super(name);
    }

    public void testNoExtraSchema() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("TestEndpointService.wsdl");
        assertTrue(is != null);

        Node node = DOMUtil.createDOMNode(is);
        Element elm = DOMUtil.getFirstElementChild(node);
        NodeList nl = elm.getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema", "import");
        if(nl.getLength() > 0)
            System.out.println("Expected none but "+ nl.getLength()+" xs:import found!");
        assertTrue(nl.getLength() == 0);
    }
}
