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
 * PolicyReferenceData Tester.
 *
 * @author Fabian Ritzmann
 */
public class PolicyReferenceDataTest extends TestCase {

    public PolicyReferenceDataTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testToStringNull() {
        final PolicyReferenceData instance = new PolicyReferenceData(null);
        assertNotNull(instance.toString());
    }
}
