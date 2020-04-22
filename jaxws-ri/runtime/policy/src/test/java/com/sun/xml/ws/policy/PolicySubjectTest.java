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

import java.util.ArrayList;
import java.util.Collection;
import junit.framework.TestCase;

/**
 *
 * @author Marek Potociar (marek.potociar@sun.com)
 */
public class PolicySubjectTest extends TestCase {
    private Object subject = new Object();
    private PolicyMerger merger = PolicyMerger.getMerger();
    
    public PolicySubjectTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
    }
    
    @Override
    protected void tearDown() throws Exception {
    }           
    
    public void testCreatePolicySubjectWithNullSubjectMustThrowIAE() throws Exception {
        try {
            new PolicySubject(null, Policy.createNullPolicy());
            fail ("PolicySubject creation must throw IllegalArgumentException on 'null' subject");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }
    
    public void testCreatePolicySubjectWithNullPolicyMustThrowIAE() throws Exception {
        try {
            Policy p = null;
            new PolicySubject(subject, p);
            fail ("PolicySubject creation must throw IllegalArgumentException on 'null' policy");
        } catch (IllegalArgumentException e) {
            // ok.
        }        
    }

    public void testCreatePolicySubjectWithNullPolicyCollcetionMustThrowIAE() throws Exception {
        try {
            Collection<Policy> c = null;
            new PolicySubject(subject, c);
            fail ("PolicySubject creation must throw IllegalArgumentException on 'null' policy collection");
        } catch (IllegalArgumentException e) {
            // ok.
        }        
    }
    
    public void testCreatePolicySubjectWithEmptyPolicyCollectionMustThrowIAE() throws Exception {
        try {
            Collection<Policy> c = new ArrayList<Policy>();
            new PolicySubject(subject, c);
            fail ("PolicySubject creation must throw IllegalArgumentException on empty policy collection");
        } catch (IllegalArgumentException e) {
            // ok.
        }        
    }
    
    public void testGetSubjectReturnsCorrectSubject() throws Exception {
        PolicySubject ps = new PolicySubject(subject, Policy.createNullPolicy());
        assertEquals("Subject used in constructor must equal to subject returned from getter.", subject, ps.getSubject());
    }
    
    public void testGetSubjectReturnsCorrectReference() throws Exception {
        StringBuffer subject = new StringBuffer('a');
        PolicySubject ps = new PolicySubject(subject, Policy.createNullPolicy());
        subject.append('b');
        assertEquals("Subject used in constructor must equal to subject returned from getter.", subject, ps.getSubject());
    }
    
    public void testAttachingNullPolicyThrowsIAE() throws Exception {
        PolicySubject ps = new PolicySubject(subject, Policy.createNullPolicy());        
        try {
        ps.attach(null);
        fail("Attaching a 'null' policy to the policyt subject must throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }
    
    public void testAttachingNoPolicyToAnyPolicyResultsInNoEffectivePolicy() throws Exception {
        PolicySubject ps = new PolicySubject(subject, Policy.createEmptyPolicy());        
        Policy noPolicy = Policy.createNullPolicy();
        ps.attach(noPolicy);
        
        assertEquals(noPolicy, ps.getEffectivePolicy(merger));
    }
    
}
