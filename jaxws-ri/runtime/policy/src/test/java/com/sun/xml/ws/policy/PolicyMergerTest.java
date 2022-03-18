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
import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
import com.sun.xml.ws.policy.testutils.PolicyResourceLoader;

import java.util.Collection;
import java.util.LinkedList;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class PolicyMergerTest extends TestCase {
    
    private static final PolicyMerger merger = PolicyMerger.getMerger();

    public PolicyMergerTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getMerger method, of class PolicyMerger.
     */
    public void testGetMerger() {
        final PolicyMerger result = PolicyMerger.getMerger();
        assertNotNull(result);
    }

    /**
     * Test of merge method, of class PolicyMerger.
     */
    public void testMergeNull() {
        final Collection<Policy> policies = null;
        final PolicyMerger instance = PolicyMerger.getMerger();
        final Policy result = instance.merge(policies);
        assertNull(result);
    }

    /**
     * Test of merge method, of class PolicyMerger.
     */
    public void testMergeNone() {
        final Collection<Policy> policies = new LinkedList<Policy>();
        final PolicyMerger instance = PolicyMerger.getMerger();
        final Policy result = instance.merge(policies);
        assertNull(result);
    }

    /**
     * Test of merge method, of class PolicyMerger.
     */
    public void testMergeEmpty() {
        final Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(Policy.createEmptyPolicy());
        final PolicyMerger instance = PolicyMerger.getMerger();
        final Policy result = instance.merge(policies);
        assertTrue(result.isEmpty());
    }

    /**
     * Test of merge method, of class PolicyMerger.
     */
    public void testFourEmpty() {
        final Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(Policy.createEmptyPolicy());
        policies.add(Policy.createEmptyPolicy());
        policies.add(Policy.createEmptyPolicy());
        policies.add(Policy.createEmptyPolicy());
        final PolicyMerger instance = PolicyMerger.getMerger();
        final Policy result = instance.merge(policies);
        assertTrue(result.isEmpty());
    }

    /**
     * Test of merge method, of class PolicyMerger.
     */
    public void testMergeEmptyAndNonEmpty() {
        final Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(Policy.createEmptyPolicy());
        AssertionData assertionData = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion1"));
        PolicyAssertion assertion = new PolicyAssertion(assertionData, null) { };
        Collection<PolicyAssertion> assertions = new LinkedList<PolicyAssertion>();
        assertions.add(assertion);
        AssertionSet assertionSet = AssertionSet.createAssertionSet(assertions);
        Collection<AssertionSet> assertionSets = new LinkedList<AssertionSet>();
        assertionSets.add(assertionSet);
        Policy expResult = Policy.createPolicy(assertionSets);
        policies.add(expResult);
        final PolicyMerger instance = PolicyMerger.getMerger();
        final Policy result = instance.merge(policies);
        assertEquals(expResult, result);
    }

    /**
     * Test of merge method, of class PolicyMerger.
     */
    public void testMergeTwo() {
        AssertionData assertionData1 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion1"));
        PolicyAssertion assertion1 = new PolicyAssertion(assertionData1, null) { };
        Collection<PolicyAssertion> assertions1 = new LinkedList<PolicyAssertion>();
        assertions1.add(assertion1);
        AssertionSet assertionSet1 = AssertionSet.createAssertionSet(assertions1);
        Collection<AssertionSet> assertionSets1 = new LinkedList<AssertionSet>();
        assertionSets1.add(assertionSet1);
        Policy policy1 = Policy.createPolicy(assertionSets1);

        AssertionData assertionData2 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion2"));
        PolicyAssertion assertion2 = new PolicyAssertion(assertionData2, null) { };
        Collection<PolicyAssertion> assertions2 = new LinkedList<PolicyAssertion>();
        assertions2.add(assertion2);
        AssertionSet assertionSet2 = AssertionSet.createAssertionSet(assertions2);
        Collection<AssertionSet> assertionSets2 = new LinkedList<AssertionSet>();
        assertionSets2.add(assertionSet2);
        Policy policy2 = Policy.createPolicy(assertionSets2);

        final Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(policy1);
        policies.add(policy2);

        Collection<PolicyAssertion> assertionsExp = new LinkedList<PolicyAssertion>();
        assertionsExp.add(assertion1);
        assertionsExp.add(assertion2);
        AssertionSet assertionSetExp = AssertionSet.createAssertionSet(assertionsExp);
        Collection<AssertionSet> assertionSetsExp = new LinkedList<AssertionSet>();
        assertionSetsExp.add(assertionSetExp);
        Policy expResult = Policy.createPolicy(assertionSetsExp);

        final PolicyMerger instance = PolicyMerger.getMerger();
        final Policy result = instance.merge(policies);
        assertEquals(expResult, result);
    }

    /**
     * Test of merge method, of class PolicyMerger.
     */
    public void testMergeTwo2() {
        AssertionData assertionData1 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion1"));
        AssertionData assertionData2 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion2"));
        AssertionData assertionData3 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion3"));
        AssertionData assertionData4 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion4"));
        PolicyAssertion assertion1 = new PolicyAssertion(assertionData1, null) { };
        PolicyAssertion assertion2 = new PolicyAssertion(assertionData2, null) { };
        PolicyAssertion assertion3 = new PolicyAssertion(assertionData3, null) { };
        PolicyAssertion assertion4 = new PolicyAssertion(assertionData4, null) { };
        Collection<PolicyAssertion> assertions1 = new LinkedList<PolicyAssertion>();
        assertions1.add(assertion1);
        assertions1.add(assertion2);
        assertions1.add(assertion3);
        assertions1.add(assertion4);
        AssertionSet assertionSet1 = AssertionSet.createAssertionSet(assertions1);
        Collection<AssertionSet> assertionSets1 = new LinkedList<AssertionSet>();
        assertionSets1.add(assertionSet1);
        Policy policy1 = Policy.createPolicy(assertionSets1);

        AssertionData assertionData23 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion3"));
        AssertionData assertionData24 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion4"));
        AssertionData assertionData25 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion5"));
        AssertionData assertionData26 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion6"));
        PolicyAssertion assertion23 = new PolicyAssertion(assertionData23, null) { };
        PolicyAssertion assertion24 = new PolicyAssertion(assertionData24, null) { };
        PolicyAssertion assertion25 = new PolicyAssertion(assertionData25, null) { };
        PolicyAssertion assertion26 = new PolicyAssertion(assertionData26, null) { };
        Collection<PolicyAssertion> assertions2 = new LinkedList<PolicyAssertion>();
        assertions2.add(assertion23);
        assertions2.add(assertion24);
        assertions2.add(assertion25);
        assertions2.add(assertion26);
        AssertionSet assertionSet2 = AssertionSet.createAssertionSet(assertions2);
        Collection<AssertionSet> assertionSets2 = new LinkedList<AssertionSet>();
        assertionSets2.add(assertionSet2);
        Policy policy2 = Policy.createPolicy(assertionSets2);

        final Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(policy1);
        policies.add(policy2);

        Collection<PolicyAssertion> assertionsExp = new LinkedList<PolicyAssertion>();
        assertionsExp.add(assertion1);
        assertionsExp.add(assertion2);
        assertionsExp.add(assertion3);
        assertionsExp.add(assertion4);
        assertionsExp.add(assertion25);
        assertionsExp.add(assertion26);
        AssertionSet assertionSetExp = AssertionSet.createAssertionSet(assertionsExp);
        Collection<AssertionSet> assertionSetsExp = new LinkedList<AssertionSet>();
        assertionSetsExp.add(assertionSetExp);
        Policy expResult = Policy.createPolicy(assertionSetsExp);

        final PolicyMerger instance = PolicyMerger.getMerger();
        final Policy result = instance.merge(policies);
        assertEquals(expResult, result);
    }

    /**
     * Test of merge method, of class PolicyMerger.
     */
    public void testMergeId() {
        AssertionData assertionData1 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion1"));
        PolicyAssertion assertion1 = new PolicyAssertion(assertionData1, null) { };
        Collection<PolicyAssertion> assertions1 = new LinkedList<PolicyAssertion>();
        assertions1.add(assertion1);
        AssertionSet assertionSet1 = AssertionSet.createAssertionSet(assertions1);
        Collection<AssertionSet> assertionSets1 = new LinkedList<AssertionSet>();
        assertionSets1.add(assertionSet1);
        Policy policy1 = Policy.createPolicy(null, "id1", assertionSets1);

        AssertionData assertionData2 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion2"));
        PolicyAssertion assertion2 = new PolicyAssertion(assertionData2, null) { };
        Collection<PolicyAssertion> assertions2 = new LinkedList<PolicyAssertion>();
        assertions2.add(assertion2);
        AssertionSet assertionSet2 = AssertionSet.createAssertionSet(assertions2);
        Collection<AssertionSet> assertionSets2 = new LinkedList<AssertionSet>();
        assertionSets2.add(assertionSet2);
        Policy policy2 = Policy.createPolicy(null, "id2", assertionSets2);

        final Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(policy1);
        policies.add(policy2);

        final String expResult1 = "id1-id2";
        final String expResult2 = "id2-id1";
        final PolicyMerger instance = PolicyMerger.getMerger();
        final Policy mergedPolicy = instance.merge(policies);
        final String result = mergedPolicy.getId();
        if (!expResult1.equals(result)) {
            assertEquals(expResult2, result);
        }
    }

    /**
     * Test of merge method, of class PolicyMerger.
     */
    public void testMergePolicyNamespace() {
        AssertionData assertionData1 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion1"));
        PolicyAssertion assertion1 = new PolicyAssertion(assertionData1, null) { };
        Collection<PolicyAssertion> assertions1 = new LinkedList<PolicyAssertion>();
        assertions1.add(assertion1);
        AssertionSet assertionSet1 = AssertionSet.createAssertionSet(assertions1);
        Collection<AssertionSet> assertionSets1 = new LinkedList<AssertionSet>();
        assertionSets1.add(assertionSet1);
        Policy policy1 = Policy.createPolicy(NamespaceVersion.v1_2, null, null, assertionSets1);

        AssertionData assertionData2 = AssertionData.createAssertionData(new QName("http://example.test/", "Assertion2"));
        PolicyAssertion assertion2 = new PolicyAssertion(assertionData2, null) { };
        Collection<PolicyAssertion> assertions2 = new LinkedList<PolicyAssertion>();
        assertions2.add(assertion2);
        AssertionSet assertionSet2 = AssertionSet.createAssertionSet(assertions2);
        Collection<AssertionSet> assertionSets2 = new LinkedList<AssertionSet>();
        assertionSets2.add(assertionSet2);
        Policy policy2 = Policy.createPolicy(NamespaceVersion.v1_5, null, null, assertionSets2);

        final Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(policy1);
        policies.add(policy2);

        Collection<PolicyAssertion> assertionsExp = new LinkedList<PolicyAssertion>();
        assertionsExp.add(assertion1);
        assertionsExp.add(assertion2);
        AssertionSet assertionSetExp = AssertionSet.createAssertionSet(assertionsExp);
        Collection<AssertionSet> assertionSetsExp = new LinkedList<AssertionSet>();
        assertionSetsExp.add(assertionSetExp);
        Policy expResult = Policy.createPolicy(assertionSetsExp);

        final PolicyMerger instance = PolicyMerger.getMerger();
        final Policy result = instance.merge(policies);
        assertEquals(expResult, result);
    }

    /**
     * Test of merge method, of class PolicyMerger.
     */
    public void testMergeNoAlternatives() {
        final Collection<Policy> policies = new LinkedList<Policy>();
        final Policy policy1 = Policy.createNullPolicy();
        final Policy policy2 = Policy.createNullPolicy();
        policies.add(policy1);
        policies.add(policy2);

        Policy expResult = Policy.createNullPolicy();
        final PolicyMerger instance = PolicyMerger.getMerger();
        final Policy result = instance.merge(policies);
        assertEquals(expResult, result);
    }

    /**
     * Test of merge method, of class PolicyMerger.
     */
    public void testMergeNoAlternativesId() {
        final Collection<Policy> policies = new LinkedList<Policy>();
        final Policy policy1 = Policy.createNullPolicy(null, "id1");
        final Policy policy2 = Policy.createNullPolicy(null, "id2");
        policies.add(policy1);
        policies.add(policy2);

        Policy expResult = Policy.createNullPolicy();
        final PolicyMerger instance = PolicyMerger.getMerger();
        final Policy result = instance.merge(policies);

        assertEquals(expResult, result);
        final String expResult1 = "id1-id2";
        final String expResult2 = "id2-id1";
        final String id = result.getId();
        if (!expResult1.equals(id)) {
            assertEquals(expResult2, id);
        }
    }

    public void testMergeTwoPolicies() throws Exception {
        Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(PolicyResourceLoader.loadPolicy("merge/policy1.xml"));
        policies.add(PolicyResourceLoader.loadPolicy("merge/policy2.xml"));
        policies.add(PolicyResourceLoader.loadPolicy("merge/policy3.xml"));

        Policy result = merger.merge(policies);
        Policy expected = PolicyResourceLoader.loadPolicy("merge/merge_1-2-3.xml");

        assertEquals(expected, result);
    }

    public void testMergeEmtpyNonEmptyPolicies() throws Exception {
        Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(PolicyResourceLoader.loadPolicy("merge/policy1.xml"));
        policies.add(PolicyResourceLoader.loadPolicy("merge/policy-empty-alt.xml"));

        Policy result = merger.merge(policies);
        Policy expected = PolicyResourceLoader.loadPolicy("merge/policy1.xml");

        assertEquals(expected, result);
    }

    public void testMergeNoAltPolicies() throws Exception {
        Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(PolicyResourceLoader.loadPolicy("merge/policy1.xml"));
        policies.add(PolicyResourceLoader.loadPolicy("merge/policy-no-alt.xml"));

        Policy result = merger.merge(policies);
        Policy expected = PolicyResourceLoader.loadPolicy("merge/policy-no-alt.xml");

        assertEquals(expected, result);
    }

    public void testMergeNamespaces() throws Exception {
        Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.2.xml"));
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.2.xml"));
        Policy result = merger.merge(policies);
        assertEquals(
                "When merging policies with same original namespace, the namespace should be preserved during merge operation",
                NamespaceVersion.v1_2,
                result.getNamespaceVersion()
                );

        policies.clear();
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.5.xml"));
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.5.xml"));
        result = merger.merge(policies);
        assertEquals(
                "When merging policies with same original namespace, the namespace should be preserved during merge operation",
                NamespaceVersion.v1_5,
                result.getNamespaceVersion()
                );

        policies.clear();
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.2.xml"));
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.5.xml"));
        result = merger.merge(policies);
        assertEquals(
                "When merging policies with different original namespace, the latest namespace should be preserved during merge operation",
                NamespaceVersion.v1_5,
                result.getNamespaceVersion()
                );
    }

}
