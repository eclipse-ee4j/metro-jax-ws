/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy;

import java.util.Arrays;
import junit.framework.TestCase;

/**
 *
 * @author Marek Potociar
 */
public class ComplexAssertionTest extends TestCase {
    private AssertionSet emptyNestedAlternative;
    private AssertionSet notEmptyNestedAlternative;
    
    private ComplexAssertion assertionCreatedWithEmptyConstructor;
    private ComplexAssertion assertionCreatedWithNullNestedAlternative;
    private ComplexAssertion assertionCreatedWithEmptyNestedAlternative;
    private ComplexAssertion assertionCreatedWithNotEmptyNestedAlternative;

    public ComplexAssertionTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        emptyNestedAlternative = AssertionSet.createAssertionSet(null);
        notEmptyNestedAlternative = AssertionSet.createAssertionSet(Arrays.asList(new PolicyAssertion(){}));
        
        assertionCreatedWithEmptyConstructor = new ComplexAssertion() {};
        assertionCreatedWithNullNestedAlternative = new ComplexAssertion(null, null, null) {};
        assertionCreatedWithEmptyNestedAlternative = new ComplexAssertion(null, null, emptyNestedAlternative) {};
        assertionCreatedWithNotEmptyNestedAlternative = new ComplexAssertion(null, null, notEmptyNestedAlternative) {};
    }

    @Override
    protected void tearDown() throws Exception {
    }

    public void testHasNestedPolicy() {
        assertTrue(assertionCreatedWithEmptyConstructor.hasNestedPolicy());
        assertTrue(assertionCreatedWithNullNestedAlternative.hasNestedPolicy());
        assertTrue(assertionCreatedWithEmptyNestedAlternative.hasNestedPolicy());
        assertTrue(assertionCreatedWithNotEmptyNestedAlternative.hasNestedPolicy());
    }

    public void testGetNestedPolicy() {
        NestedPolicy np = assertionCreatedWithEmptyConstructor.getNestedPolicy();
        assertNotNull(np);
        assertFalse(np.isNull());
        assertTrue(np.isEmpty());
        
        np = assertionCreatedWithNullNestedAlternative.getNestedPolicy();
        assertNotNull(np);
        assertFalse(np.isNull());
        assertTrue(np.isEmpty());
        
        np = assertionCreatedWithEmptyNestedAlternative.getNestedPolicy();
        assertNotNull(np);
        assertFalse(np.isNull());
        assertTrue(np.isEmpty());
        
        np = assertionCreatedWithNotEmptyNestedAlternative.getNestedPolicy();
        assertNotNull(np);
        assertFalse(np.isNull());
        assertFalse(np.isEmpty());
    }
}
