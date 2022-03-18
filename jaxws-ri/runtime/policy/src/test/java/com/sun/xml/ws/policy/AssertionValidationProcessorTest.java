/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy;

import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class AssertionValidationProcessorTest extends TestCase {
    
    public AssertionValidationProcessorTest(String testName) {
        super(testName);
    }

    /**
     * Test of getInstance method, of class AssertionValidationProcessor.
     * @throws PolicyException If instantiation failed.
     */
    public void testGetInstance() throws PolicyException {
        AssertionValidationProcessor result = AssertionValidationProcessor.getInstance();
        assertNotNull(result);
    }

}
