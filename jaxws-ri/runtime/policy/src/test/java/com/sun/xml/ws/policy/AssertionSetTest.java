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

import com.sun.xml.ws.policy.testutils.PolicyResourceLoader;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


public class AssertionSetTest extends AbstractPolicyApiClassTestBase {
    
    private static final String[] SINGLE_ALTERNATIVE_POLICY = new String[] {
        "single_alternative_policy/policy1.xml",
        "single_alternative_policy/policy2.xml",
        "single_alternative_policy/policy3.xml",
        "single_alternative_policy/policy4.xml",
        "single_alternative_policy/policy5.xml"
    };

    public AssertionSetTest(String testName) throws PolicyException {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
    }

    protected AssertionSet[][] getEqualInstanceRows() throws Exception {
        Collection<AssertionSet[]> rows = new LinkedList<AssertionSet[]>();
        
        for (String name : SINGLE_ALTERNATIVE_POLICY) {
            Iterator<AssertionSet> iteratorA = PolicyResourceLoader.loadPolicy(name).iterator();
            Iterator<AssertionSet> iteratorB = PolicyResourceLoader.loadPolicy(name).iterator();
            
            if (iteratorA.hasNext()) {
                rows.add(new AssertionSet[] {iteratorA.next(), iteratorB.next()});
            }            
        }
        
        return rows.toArray(new AssertionSet[rows.size()][]);
    }    
}
