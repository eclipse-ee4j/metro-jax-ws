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
 * @author Marek Potociar (marek.potociar@sun.com)
 */
public class PolicyTest extends TestCase {
    public PolicyTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
    }
    
    @Override
    protected void tearDown() throws Exception {
    }
    
    public void testEmptyPolicyReturnsTrueOnIsEmptyAndFalseOnIsNull() {
        Policy tested = Policy.createEmptyPolicy();
        assertTrue("Empty policy must return 'true' on isEmpty() call", tested.isEmpty());
        assertFalse("Empty policy must return 'false' on isNull() call", tested.isNull());
        
    }
    
    public void testNullPolicyReturnsFalseOnIsEmptyAndTrueOnIsNull() {
        Policy tested = Policy.createNullPolicy();
        assertFalse("Null policy must return 'false' on isEmpty() call", tested.isEmpty());
        assertTrue("Null policy must return 'true' on isNull() call", tested.isNull());
    }

    public void testNullPolicyFactoryMethodReturnsConstantObjectOnNullArguments () {
        Policy tested = Policy.createNullPolicy(null, null);
        Policy expected = Policy.createNullPolicy();
        
        assertTrue("The createNullPolicy(String, String) factory method should return the same instance as createNullPolicy()", tested == expected);
    }    

    public void testEmptyPolicyFactoryMethodReturnsConstantObjectOnNullArguments () {
        Policy tested = Policy.createEmptyPolicy(null, null);
        Policy expected = Policy.createEmptyPolicy();
        
        assertTrue("The createEmptyPolicy(String, String) factory method should return the same instance as createEmptyPolicy()", tested == expected);
    }    

    public void testNullPolicyFactoryMethodReturnsProperObjectOnNonNullArguments () {
        Policy tested = Policy.createNullPolicy("aaa", "bbb");
        
        assertEquals("The name is not initialized as expected", tested.getName(), "aaa");
        assertEquals("The ID is not initialized as expected", tested.getId(), "bbb");
    }    

    public void testEmptyPolicyFactoryMethodReturnsProperObjectOnNonNullArguments () {
        Policy tested = Policy.createEmptyPolicy("aaa", "bbb");
        
        assertEquals("The name is not initialized as expected", tested.getName(), "aaa");
        assertEquals("The ID is not initialized as expected", tested.getId(), "bbb");
    }       
}
