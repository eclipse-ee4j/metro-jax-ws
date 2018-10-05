/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policytest;

import com.sun.xml.ws.api.policy.AlternativeSelector;
import com.sun.xml.ws.api.policy.ModelTranslator;
import com.sun.xml.ws.policy.EffectivePolicyModifier;
import com.sun.xml.ws.policy.Policy;
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.PolicyMapExtender;
import com.sun.xml.ws.policy.PolicyMapKey;
import com.sun.xml.ws.policy.PolicyMapMutator;
import com.sun.xml.ws.policy.PolicySubject;
import com.sun.xml.ws.policy.sourcemodel.AssertionData;
import com.sun.xml.ws.policy.sourcemodel.ModelNode;
import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;

import java.util.Arrays;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

/**
 * This class is in the com.sun.xml.ws.policy package instead of
 * com.sun.xml.ws.api.policy so that it can access some package private policy
 * methods.
 *
 * @author Fabian Ritzmann
 */
public class AlternativeSelectorTest extends TestCase {

    private final QName assertion1Name = new QName("test1", "test1");
    private final QName assertion2Name = new QName("test2", "test2");

    private Policy multipleAlternativesPolicy;


    public AlternativeSelectorTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws PolicyException {
        final PolicySourceModel model = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_5, "id", null);
        final ModelNode root = model.getRootNode();
        final ModelNode alternatives = root.createChildExactlyOneNode();
        final ModelNode alternative1 = alternatives.createChildAllNode();
        final ModelNode alternative2 = alternatives.createChildAllNode();
        final AssertionData assertion1 = AssertionData.createAssertionData(assertion1Name);
        alternative1.createChildAssertionNode(assertion1);
        final AssertionData assertion2 = AssertionData.createAssertionData(assertion2Name);
        alternative2.createChildAssertionNode(assertion2);
        final ModelTranslator translator = ModelTranslator.getTranslator();
        this.multipleAlternativesPolicy = translator.translate(model);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of doSelection method, of class AlternativeSelector.
     * @throws PolicyException
     */
    public void testDoSelectionNull() throws PolicyException {
        final EffectivePolicyModifier modifier = null;
        try {
            AlternativeSelector.doSelection(modifier);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * Test of doSelection method, of class AlternativeSelector.
     * @throws PolicyException
     */
    public void testDoSelectionUnconnected() throws PolicyException {
        final EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
        try {
            AlternativeSelector.doSelection(modifier);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * Test of doSelection method, of class AlternativeSelector.
     * @throws PolicyException
     */
    public void testDoSelectionEmpty() throws PolicyException {
        final EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
        final PolicyMap map = PolicyMap.createPolicyMap(null);
        modifier.connect(map);
        AlternativeSelector.doSelection(modifier);
        assertTrue(map.isEmpty());
    }

    /**
     * Test of doSelection method, of class AlternativeSelector.
     * @throws PolicyException
     */
    public void testDoSelectionAlternativesService() throws PolicyException {

        final PolicyMapExtender extender = PolicyMapExtender.createPolicyMapExtender();
        final PolicyMap map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));

        final PolicySubject subject = new PolicySubject("dummy", this.multipleAlternativesPolicy);

        final PolicyMapKey key = PolicyMap.createWsdlServiceScopeKey(new QName("service"));
        extender.putServiceSubject(key, subject);

        final EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
        modifier.connect(map);
        AlternativeSelector.doSelection(modifier);

        final Policy result = map.getServiceEffectivePolicy(key);
        if (result.contains(this.assertion1Name)) {
            assertFalse(result.contains(this.assertion2Name));
        }
        else if (result.contains(this.assertion2Name)) {
            assertFalse(result.contains(this.assertion1Name));
        }
        else {
            fail("Expected exactly one assertion in the resulting policy.");
        }
    }

    /**
     * Test of doSelection method, of class AlternativeSelector.
     * @throws PolicyException
     */
    public void testDoSelectionAlternativesEndpoint() throws PolicyException {

        final PolicyMapExtender extender = PolicyMapExtender.createPolicyMapExtender();
        final PolicyMap map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));

        final PolicySubject subject = new PolicySubject("dummy", this.multipleAlternativesPolicy);

        final PolicyMapKey key = PolicyMap.createWsdlEndpointScopeKey(new QName("service"), new QName("port"));
        extender.putEndpointSubject(key, subject);

        final EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
        modifier.connect(map);
        AlternativeSelector.doSelection(modifier);

        final Policy result = map.getEndpointEffectivePolicy(key);
        if (result.contains(this.assertion1Name)) {
            assertFalse(result.contains(this.assertion2Name));
        }
        else if (result.contains(this.assertion2Name)) {
            assertFalse(result.contains(this.assertion1Name));
        }
        else {
            fail("Expected exactly one assertion in the resulting policy.");
        }
    }

    /**
     * Test of doSelection method, of class AlternativeSelector.
     * @throws PolicyException
     */
    public void testDoSelectionAlternativesOperation() throws PolicyException {

        final PolicyMapExtender extender = PolicyMapExtender.createPolicyMapExtender();
        final PolicyMap map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));

        final PolicySubject subject = new PolicySubject("dummy", this.multipleAlternativesPolicy);

        final PolicyMapKey key = PolicyMap.createWsdlOperationScopeKey(
                new QName("service"), new QName("port"), new QName("operation"));
        extender.putOperationSubject(key, subject);

        final EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
        modifier.connect(map);
        AlternativeSelector.doSelection(modifier);

        final Policy result = map.getOperationEffectivePolicy(key);
        if (result.contains(this.assertion1Name)) {
            assertFalse(result.contains(this.assertion2Name));
        }
        else if (result.contains(this.assertion2Name)) {
            assertFalse(result.contains(this.assertion1Name));
        }
        else {
            fail("Expected exactly one assertion in the resulting policy.");
        }
    }

    /**
     * Test of doSelection method, of class AlternativeSelector.
     * @throws PolicyException
     */
    public void testDoSelectionAlternativesInput() throws PolicyException {

        final PolicyMapExtender extender = PolicyMapExtender.createPolicyMapExtender();
        final PolicyMap map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));

        final PolicySubject subject = new PolicySubject("dummy", this.multipleAlternativesPolicy);

        final PolicyMapKey key = PolicyMap.createWsdlMessageScopeKey(
                new QName("service"), new QName("port"), new QName("operation"));
        extender.putInputMessageSubject(key, subject);

        final EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
        modifier.connect(map);
        AlternativeSelector.doSelection(modifier);

        final Policy result = map.getInputMessageEffectivePolicy(key);
        if (result.contains(this.assertion1Name)) {
            assertFalse(result.contains(this.assertion2Name));
        }
        else if (result.contains(this.assertion2Name)) {
            assertFalse(result.contains(this.assertion1Name));
        }
        else {
            fail("Expected exactly one assertion in the resulting policy.");
        }
    }

    public void testDoSelectionAlternativesOutput() throws PolicyException {

        final PolicyMapExtender extender = PolicyMapExtender.createPolicyMapExtender();
        final PolicyMap map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));

        final PolicySubject subject = new PolicySubject("dummy", this.multipleAlternativesPolicy);

        final PolicyMapKey key = PolicyMap.createWsdlMessageScopeKey(
                new QName("service"), new QName("port"), new QName("operation"));
        extender.putOutputMessageSubject(key, subject);

        final EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
        modifier.connect(map);
        AlternativeSelector.doSelection(modifier);

        final Policy result = map.getOutputMessageEffectivePolicy(key);
        if (result.contains(this.assertion1Name)) {
            assertFalse(result.contains(this.assertion2Name));
        }
        else if (result.contains(this.assertion2Name)) {
            assertFalse(result.contains(this.assertion1Name));
        }
        else {
            fail("Expected exactly one assertion in the resulting policy.");
        }
    }

    public void testDoSelectionAlternativesFault() throws PolicyException {

        final PolicyMapExtender extender = PolicyMapExtender.createPolicyMapExtender();
        final PolicyMap map = PolicyMap.createPolicyMap(Arrays.asList(new PolicyMapMutator[] {extender}));

        final PolicySubject subject = new PolicySubject("dummy", this.multipleAlternativesPolicy);

        final PolicyMapKey key = PolicyMap.createWsdlFaultMessageScopeKey(
                new QName("service"), new QName("port"), new QName("operation"), new QName("fault"));
        extender.putFaultMessageSubject(key, subject);

        final EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
        modifier.connect(map);
        AlternativeSelector.doSelection(modifier);

        final Policy result = map.getFaultMessageEffectivePolicy(key);
        if (result.contains(this.assertion1Name)) {
            assertFalse(result.contains(this.assertion2Name));
        }
        else if (result.contains(this.assertion2Name)) {
            assertFalse(result.contains(this.assertion1Name));
        }
        else {
            fail("Expected exactly one assertion in the resulting policy.");
        }
    }

}
