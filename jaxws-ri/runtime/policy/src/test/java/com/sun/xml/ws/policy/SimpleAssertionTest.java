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
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public class SimpleAssertionTest extends TestCase {

    public SimpleAssertionTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
    }

    public void testHasNestedPolicy() {
        SimpleAssertion instance = new SimpleAssertion() {
        };
        boolean result = instance.hasNestedPolicy();
        assertFalse("Simple assertion should never claim to have a nested policy", result);
    }

    public void testGetNestedPolicy() {
        SimpleAssertion instance = new SimpleAssertion() {
        };
        NestedPolicy result = instance.getNestedPolicy();
        assertNull("Nested policy of simple assertion should always be 'null'", result);
    }
}
