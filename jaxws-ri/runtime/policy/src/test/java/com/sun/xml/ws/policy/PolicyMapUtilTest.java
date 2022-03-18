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

import com.sun.xml.ws.policy.sourcemodel.AssertionData;
import com.sun.xml.ws.policy.sourcemodel.ModelNode;
import com.sun.xml.ws.policy.sourcemodel.PolicyModelTranslator;
import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;

import java.util.Arrays;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class PolicyMapUtilTest extends TestCase {
    
    public PolicyMapUtilTest(String testName) {
        super(testName);
    }            

    /**
     * Test of rejectAlternatives method, of class PolicyMapUtil.
     */
    public void testRejectAlternativesSimple() throws PolicyException {
        PolicyMapExtender extender = PolicyMapExtender.createPolicyMapExtender();
        PolicyMap map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));        

        PolicySubject subject = new PolicySubject("dummy", Policy.createEmptyPolicy());
        
        PolicyMapKey key = PolicyMap.createWsdlServiceScopeKey(new QName("1"));
        extender.putServiceSubject(key, subject);
        key = PolicyMap.createWsdlServiceScopeKey(new QName("2"));
        extender.putServiceSubject(key, subject);
        
        PolicyMapUtil.rejectAlternatives(map);
    }

    public void testRejectAlternativesComplex() throws PolicyException {
        PolicySourceModel model = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_5, "id", null);
        ModelNode root = model.getRootNode();
        ModelNode alternatives = root.createChildExactlyOneNode();
        ModelNode alternative1 = alternatives.createChildAllNode();
        ModelNode alternative2 = alternatives.createChildAllNode();
        AssertionData assertion1 = AssertionData.createAssertionData(new QName("test1", "test1"));
        alternative1.createChildAssertionNode(assertion1);
        AssertionData assertion2 = AssertionData.createAssertionData(new QName("test2", "test2"));
        alternative2.createChildAssertionNode(assertion2);
        PolicyModelTranslator translator = PolicyModelTranslator.getTranslator();
        Policy policy = translator.translate(model);
        
        PolicyMapExtender extender = PolicyMapExtender.createPolicyMapExtender();
        PolicyMap map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));        

        PolicySubject subject = new PolicySubject("dummy", policy);
        
        PolicyMapKey key = PolicyMap.createWsdlServiceScopeKey(new QName("1"));
        extender.putServiceSubject(key, subject);
        key = PolicyMap.createWsdlServiceScopeKey(new QName("2"));
        extender.putServiceSubject(key, subject);

        try {
            PolicyMapUtil.rejectAlternatives(map);
            fail("Expected a PolicyException");
        } catch (PolicyException e) {
        }
    }

}
