/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.sourcemodel.wspolicy;

import javax.xml.namespace.QName;
import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class NamespaceVersionTest extends TestCase {
    
    public NamespaceVersionTest(String testName) {
        super(testName);
    }            

    /**
     * Test of resolveVersion method, of class NamespaceVersion.
     */
    public void testResolveVersion_String() {
        String uri = "http://schemas.xmlsoap.org/ws/2004/09/policy";
        NamespaceVersion expResult = NamespaceVersion.v1_2;
        NamespaceVersion result = NamespaceVersion.resolveVersion(uri);
        assertEquals(expResult, result);
    }
    
    public void testResolveVersion_StringNull() {
        String uri = "http://metro.test/";
        NamespaceVersion result = NamespaceVersion.resolveVersion(uri);
        assertNull(result);
    }

    /**
     * Test of resolveVersion method, of class NamespaceVersion.
     */
    public void testResolveVersion_QName() {
        QName name = new QName("http://www.w3.org/ns/ws-policy", "Policy");
        NamespaceVersion expResult = NamespaceVersion.v1_5;
        NamespaceVersion result = NamespaceVersion.resolveVersion(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getLatestVersion method, of class NamespaceVersion.
     */
    public void testGetLatestVersion() {
        NamespaceVersion expResult = NamespaceVersion.v1_5;
        NamespaceVersion result = NamespaceVersion.getLatestVersion();
        assertEquals(expResult, result);
    }

    /**
     * Test of resolveAsToken method, of class NamespaceVersion.
     */
    public void testResolveAsToken() {
        QName name = new QName("http://www.w3.org/ns/ws-policy", "Policy");
        XmlToken expResult = XmlToken.Policy;
        XmlToken result = NamespaceVersion.resolveAsToken(name);
        assertEquals(expResult, result);
    }
    
    public void testResolveAsTokenWrongNamespace() {
        QName name = new QName("http://metro.test/", "Policy");
        XmlToken expResult = XmlToken.UNKNOWN;
        XmlToken result = NamespaceVersion.resolveAsToken(name);
        assertEquals(expResult, result);
    }

    public void testResolveAsTokenWrongLocalName() {
        QName name = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "test");
        XmlToken expResult = XmlToken.UNKNOWN;
        XmlToken result = NamespaceVersion.resolveAsToken(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDefaultNamespacePrefix method, of class NamespaceVersion.
     */
    public void testGetDefaultNamespacePrefix() {
        NamespaceVersion instance = NamespaceVersion.v1_2;
        String expResult = "wsp1_2";
        String result = instance.getDefaultNamespacePrefix();
        assertEquals(expResult, result);
    }

    /**
     * Test of asQName method, of class NamespaceVersion.
     */
    public void testAsQName() {
        XmlToken token = XmlToken.ExactlyOne;
        NamespaceVersion instance = NamespaceVersion.v1_5;
        QName expResult = new QName("http://www.w3.org/ns/ws-policy", "ExactlyOne");
        QName result = instance.asQName(token);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class NamespaceVersion.
     */
    public void testToString() {
        NamespaceVersion instance = NamespaceVersion.v1_2;
        String result = instance.toString();
        assertNotNull(result);
    }

}
