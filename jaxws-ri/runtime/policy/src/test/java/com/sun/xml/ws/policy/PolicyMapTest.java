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
import java.util.LinkedList;
import javax.xml.namespace.QName;
import junit.framework.TestCase;

/**
 *
 * @author Marek Potociar
 */
public class PolicyMapTest extends TestCase {
    
    public PolicyMapTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
    }
    
    public void testCreatePolicyMapWithNullMutatorCollection() throws Exception {
        assertNotNull("Policy map instance should not be null", PolicyMap.createPolicyMap(null));
    }
    
    public void testCreatePolicyMapWithEmptyMutatorCollection() throws Exception {
        assertNotNull("Policy map instance should not be null", PolicyMap.createPolicyMap(new LinkedList<PolicyMapMutator>()));        
    }

    public void testCreatePolicyMapWithNonemptyMutatorCollection() throws Exception {
        assertNotNull("Policy map instance should not be null", PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {PolicyMapExtender.createPolicyMapExtender()})));                
    }
    
    public void testPolicyMapIterator() throws Exception {
        PolicyMapExtender extender = PolicyMapExtender.createPolicyMapExtender();
        PolicyMap map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));        

        PolicySubject subject = new PolicySubject("dummy", Policy.createEmptyPolicy());
        
        PolicyMapKey key = PolicyMap.createWsdlServiceScopeKey(new QName("1"));
        extender.putServiceSubject(key, subject);
        key = PolicyMap.createWsdlServiceScopeKey(new QName("2"));
        extender.putServiceSubject(key, subject);
        
        key = PolicyMap.createWsdlEndpointScopeKey(new QName("3"), new QName("port"));
        extender.putEndpointSubject(key, subject);
        key = PolicyMap.createWsdlEndpointScopeKey(new QName("4"), new QName("port"));
        extender.putEndpointSubject(key, subject);
        key = PolicyMap.createWsdlEndpointScopeKey(new QName("5"), new QName("port"));
        extender.putEndpointSubject(key, subject);

        key = PolicyMap.createWsdlMessageScopeKey(new QName("6"), new QName("port"), new QName("operation"));
        extender.putInputMessageSubject(key, subject);

        key = PolicyMap.createWsdlMessageScopeKey(new QName("7"), new QName("port"), new QName("operation"));
        extender.putOutputMessageSubject(key, subject);
        
        key = PolicyMap.createWsdlMessageScopeKey(new QName("8"), new QName("port"), new QName("operation"));
        extender.putFaultMessageSubject(key, subject);
        
        int counter = 0;
        for (Policy policy : map) {
            counter++;
        }
        
        assertEquals("Did not iterate over expected number of policies.", 8, counter);        
    }
    
    public void testIsEmpty() {
        PolicyMap map = PolicyMap.createPolicyMap(null);
        assertTrue(map.isEmpty());
        
        map = PolicyMap.createPolicyMap(new LinkedList<PolicyMapMutator>());
        assertTrue(map.isEmpty());
        
        map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {PolicyMapExtender.createPolicyMapExtender()}));
        assertTrue(map.isEmpty());
        
        PolicyMapExtender extender = PolicyMapExtender.createPolicyMapExtender();
        PolicyMapMutator[] mutators = new PolicyMapMutator[] {extender};
        PolicyMapKey key = PolicyMap.createWsdlServiceScopeKey(new QName("service"));
        map = PolicyMap.createPolicyMap(Arrays.asList(mutators));
        extender.putServiceSubject(key, null);
        assertFalse(map.isEmpty());

        mutators[0].disconnect();
        key = PolicyMap.createWsdlEndpointScopeKey(new QName("service"), new QName("port"));
        map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));
        extender.putEndpointSubject(key, null);
        assertFalse(map.isEmpty());

        mutators[0].disconnect();
        key = PolicyMap.createWsdlOperationScopeKey(new QName("service"), new QName("port"), new QName("operation"));
        map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));
        extender.putOperationSubject(key, null);
        assertFalse(map.isEmpty());

        mutators[0].disconnect();
        key = PolicyMap.createWsdlMessageScopeKey(new QName("service"), new QName("port"), new QName("operation"));
        map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));
        extender.putInputMessageSubject(key, null);
        assertFalse(map.isEmpty());

        mutators[0].disconnect();
        key = PolicyMap.createWsdlMessageScopeKey(new QName("service"), new QName("port"), new QName("operation"));
        map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));
        extender.putOutputMessageSubject(key, null);
        assertFalse(map.isEmpty());

        mutators[0].disconnect();
        key = PolicyMap.createWsdlMessageScopeKey(new QName("service"), new QName("port"), new QName("operation"));
        map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));
        extender.putFaultMessageSubject(key, null);
        assertFalse(map.isEmpty());
    }
    
}
