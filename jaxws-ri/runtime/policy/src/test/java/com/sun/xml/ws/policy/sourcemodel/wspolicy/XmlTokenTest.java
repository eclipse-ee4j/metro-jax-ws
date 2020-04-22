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

import junit.framework.TestCase;

/**
 *
 * @author fr159072
 */
public class XmlTokenTest extends TestCase {
    
    public XmlTokenTest(String testName) {
        super(testName);
    }            

    /**
     * Test of resolveToken method, of class XmlToken.
     */
    public void testResolveTokenPolicy() {
        String name = "Policy";
        XmlToken expResult = XmlToken.Policy;
        XmlToken result = XmlToken.resolveToken(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of resolveToken method, of class XmlToken.
     */
    public void testResolveTokenUnknown() {
        String name = "test";
        XmlToken expResult = XmlToken.UNKNOWN;
        XmlToken result = XmlToken.resolveToken(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of isElement method, of class XmlToken.
     */
    public void testIsElementAll() {
        XmlToken instance = XmlToken.All;
        boolean expResult = true;
        boolean result = instance.isElement();
        assertEquals(expResult, result);
    }

    /**
     * Test of isElement method, of class XmlToken.
     */
    public void testIsElementDigest() {
        XmlToken instance = XmlToken.Digest;
        boolean expResult = false;
        boolean result = instance.isElement();
        assertEquals(expResult, result);
    }

}
