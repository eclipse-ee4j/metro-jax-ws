/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.spi;

import com.sun.xml.ws.policy.sourcemodel.AssertionData;
import javax.xml.namespace.QName;
import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class AssertionCreationExceptionTest extends TestCase {
    
    public AssertionCreationExceptionTest(String testName) {
        super(testName);
    }            

    /**
     * Test of getAssertionData method, of class AssertionCreationException.
     */
    public void testGetAssertionData() {
        AssertionData expResult = AssertionData.createAssertionData(new QName("test1", "test2"));
        AssertionCreationException instance = new AssertionCreationException(expResult, "testing an exception");
        AssertionData result = instance.getAssertionData();
        assertSame(expResult, result);
    }

}
