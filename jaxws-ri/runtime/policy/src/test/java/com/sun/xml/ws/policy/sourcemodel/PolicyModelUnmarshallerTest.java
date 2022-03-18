/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.sourcemodel;

import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class PolicyModelUnmarshallerTest extends TestCase {
    
    public PolicyModelUnmarshallerTest(String testName) {
        super(testName);
    }            

    /**
     * Test of getXmlUnmarshaller method, of class PolicyModelUnmarshaller.
     */
    public void testGetXmlUnmarshaller() {
        PolicyModelUnmarshaller result = PolicyModelUnmarshaller.getXmlUnmarshaller();
        assertNotNull(result);
    }

}
