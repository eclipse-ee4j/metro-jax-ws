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
import java.util.LinkedList;

public class PolicyScopeTest extends TestCase {
    
    public PolicyScopeTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
    }

    public void testConstructor() {
        LinkedList<PolicySubject> subjects = null;

        PolicyScope result = new PolicyScope(subjects);
        assertNotNull(result);
        
        subjects = new LinkedList<PolicySubject>();
        subjects.add(new PolicySubject(new Object(), Policy.createEmptyPolicy()));
        
        result = new PolicyScope(subjects);
        assertNotNull(result);
    }
    
}
