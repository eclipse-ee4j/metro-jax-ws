/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.sourcemodel;

import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
import com.sun.xml.ws.policy.testutils.PolicyResourceLoader;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class PolicySourceModelTest extends TestCase {
    
    public PolicySourceModelTest(String testName) {
        super(testName);
    }

    /**
     * Test of createPolicySourceModel method, of class PolicySourceModel.
     */
    public void testCreatePolicySourceModel_NamespaceVersion12() {
        NamespaceVersion nsVersion = NamespaceVersion.v1_2;
        NamespaceVersion expResult = NamespaceVersion.v1_2;
        PolicySourceModel result = PolicySourceModel.createPolicySourceModel(nsVersion);
        assertEquals(expResult, result.getNamespaceVersion());
    }

    /**
     * Test of createPolicySourceModel method, of class PolicySourceModel.
     */
    public void testCreatePolicySourceModel_NamespaceVersion15() {
        NamespaceVersion nsVersion = NamespaceVersion.v1_5;
        NamespaceVersion expResult = NamespaceVersion.v1_5;
        PolicySourceModel result = PolicySourceModel.createPolicySourceModel(nsVersion);
        assertEquals(expResult, result.getNamespaceVersion());
    }

    /**
     * Test of getNamespaceToPrefixMapping method, of class PolicySourceModel.
     */
    public void testGetNamespaceToPrefixMapping() throws PolicyException {
        PolicySourceModel instance = PolicySourceModel.createPolicySourceModel(NamespaceVersion.getLatestVersion(), "policy-id", null);
        ModelNode root = instance.getRootNode();
        QName name = new QName("http://java.sun.com/xml/ns/wsit/policy", "local");
        AssertionData assertion = AssertionData.createAssertionData(name);
        ModelNode childNode = root.createChildAssertionNode(assertion);
        AssertionData assertionChild = AssertionData.createAssertionData(name);
        childNode.createChildAssertionParameterNode(assertionChild);
        Map<String, String> result = instance.getNamespaceToPrefixMapping();
        assertEquals("wsp", result.get("http://www.w3.org/ns/ws-policy"));
        assertEquals("wsu", result.get("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"));
        assertEquals("sunwsp", result.get("http://java.sun.com/xml/ns/wsit/policy"));
    }

    public void testEqualsDifferentClass() {
        PolicySourceModel instance = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_2);
        assertFalse(instance.equals(new Object()));
    }

    public void testEqualsSameObject() {
        PolicySourceModel instance = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_2);
        assertTrue(instance.equals(instance));
    }

    public void testEqualsDifferentObject() {
        PolicySourceModel instance = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_2);
        ModelNode root = instance.getRootNode();
        QName name = new QName("http://java.sun.com/xml/ns/wsit/policy", "local");
        AssertionData assertion = AssertionData.createAssertionData(name);
        root.createChildAssertionNode(assertion);

        PolicySourceModel instance2 = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_2);
        ModelNode root2 = instance2.getRootNode();
        QName name2 = new QName("http://java.sun.com/xml/ns/wsit/policy", "local2");
        AssertionData assertion2 = AssertionData.createAssertionData(name2);
        root2.createChildAssertionNode(assertion2);

        assertFalse(instance.equals(instance2));
    }

    public void testToString() {
        PolicySourceModel instance = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_2);
        assertNotNull(instance.toString());
    }

    public void testContainsPolicyReferences() {
        PolicySourceModel instance = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_2);
        assertFalse(instance.containsPolicyReferences());
    }

    public void testExpand() throws URISyntaxException, PolicyException {
        PolicySourceModel referringModel = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_5);
        ModelNode root = referringModel.getRootNode();
        URI policyReference = new URI("#local");
        PolicyReferenceData referenceData = new PolicyReferenceData(policyReference);
        ModelNode referenceNode = root.createChildPolicyReferenceNode(referenceData);

        PolicySourceModel referredModel = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_2, "local", null);
        ModelNode root2 = referredModel.getRootNode();
        QName name = new QName("http://java.sun.com/xml/ns/wsit/policy", "local");
        AssertionData assertion = AssertionData.createAssertionData(name);
        root2.createChildAssertionNode(assertion);

        PolicySourceModelContext context = PolicySourceModelContext.createContext();
        context.addModel(policyReference, referredModel);

        assertNull(referenceNode.getReferencedModel());
        referringModel.expand(context);
        assertNotNull(referenceNode.getReferencedModel());
    }

    public void testCloneModel() throws Exception {
        PolicySourceModel model = PolicyResourceLoader.unmarshallModel("complex_policy/nested_assertions_with_alternatives.xml");
        PolicySourceModel clone = model.clone();

        model.toString();
        clone.toString();
        assertEquals(model, clone);
    }

}
