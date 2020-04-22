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

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class PolicyIntersectorTest extends TestCase {
    
    private static final PolicyIntersector strictIntersector = PolicyIntersector.createStrictPolicyIntersector();
    private static final PolicyIntersector laxIntersector = PolicyIntersector.createLaxPolicyIntersector();

    public PolicyIntersectorTest(String testName) {
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
     * Test of createStrictPolicyIntersector method, of class PolicyIntersector.
     */
    public void testCreateStrictPolicyIntersector() {
        PolicyIntersector result = PolicyIntersector.createStrictPolicyIntersector();
        assertNotNull(result);
    }

    /**
     * Test of createLaxPolicyIntersector method, of class PolicyIntersector.
     */
    public void testCreateLaxPolicyIntersector() {
        PolicyIntersector result = PolicyIntersector.createLaxPolicyIntersector();
        assertNotNull(result);
    }

    /**
     * Test of intersect method, of class PolicyIntersector.
     */
    public void testIntersectLax() {
        AssertionData data1 = AssertionData.createAssertionData(new QName("testns", "name1"));
        PolicyAssertion assertion1 = new MockAssertion(data1);
        AssertionData data2 = AssertionData.createAssertionData(new QName("testns", "name2"));
        PolicyAssertion assertion2 = new MockAssertion(data2);
        AssertionData data3 = AssertionData.createAssertionData(new QName("testns", "name3"));
        PolicyAssertion assertion3 = new MockAssertion(data3);
        LinkedList<PolicyAssertion> assertions1 = new LinkedList<PolicyAssertion>();
        assertions1.add(assertion1);
        assertions1.add(assertion2);
        assertions1.add(assertion3);
        AssertionSet set1 = AssertionSet.createAssertionSet(assertions1);
        LinkedList<AssertionSet> sets1 = new LinkedList<AssertionSet>();
        sets1.add(set1);
        Policy policy1 = Policy.createPolicy(null, "policy1", sets1);
        LinkedList<PolicyAssertion> assertions2 = new LinkedList<PolicyAssertion>();
        assertions2.add(assertion1);
        assertions2.add(assertion2);
        assertions2.add(assertion3);
        AssertionSet set2 = AssertionSet.createAssertionSet(assertions2);
        LinkedList<AssertionSet> sets2 = new LinkedList<AssertionSet>();
        sets2.add(set2);
        Policy policy2 = Policy.createPolicy(null, "policy2", sets2);
        Policy[] policies = new Policy[] {policy1, policy2};
        LinkedList<PolicyAssertion> assertions3 = new LinkedList<PolicyAssertion>();
        assertions3.add(assertion1);
        assertions3.add(assertion2);
        assertions3.add(assertion3);
        AssertionSet set3 = AssertionSet.createAssertionSet(assertions3);
        LinkedList<AssertionSet> sets3 = new LinkedList<AssertionSet>();
        sets3.add(set3);
        Policy expResult = Policy.createPolicy(null, "policy3", sets3);
        PolicyIntersector instance = PolicyIntersector.createLaxPolicyIntersector();
        Policy result = instance.intersect(policies);
        assertEquals(expResult, result);
    }

    public void testIntersectStrict() {
        AssertionData data1 = AssertionData.createAssertionData(new QName("testns", "name1"));
        PolicyAssertion assertion1 = new MockAssertion(data1);
        AssertionData data2 = AssertionData.createAssertionData(new QName("testns", "name2"));
        PolicyAssertion assertion2 = new MockAssertion(data2);
        AssertionData data3 = AssertionData.createAssertionData(new QName("testns", "name3"));
        PolicyAssertion assertion3 = new MockAssertion(data3);
        LinkedList<PolicyAssertion> assertions1 = new LinkedList<PolicyAssertion>();
        assertions1.add(assertion1);
        assertions1.add(assertion2);
        assertions1.add(assertion3);
        AssertionSet set1 = AssertionSet.createAssertionSet(assertions1);
        LinkedList<AssertionSet> sets1 = new LinkedList<AssertionSet>();
        sets1.add(set1);
        Policy policy1 = Policy.createPolicy(null, "policy1", sets1);
        LinkedList<PolicyAssertion> assertions2 = new LinkedList<PolicyAssertion>();
        assertions2.add(assertion1);
        assertions2.add(assertion2);
        assertions2.add(assertion3);
        AssertionSet set2 = AssertionSet.createAssertionSet(assertions2);
        LinkedList<AssertionSet> sets2 = new LinkedList<AssertionSet>();
        sets2.add(set2);
        Policy policy2 = Policy.createPolicy(null, "policy2", sets2);
        Policy[] policies = new Policy[] {policy1, policy2};
        LinkedList<PolicyAssertion> assertions3 = new LinkedList<PolicyAssertion>();
        assertions3.add(assertion1);
        assertions3.add(assertion2);
        assertions3.add(assertion3);
        AssertionSet set3 = AssertionSet.createAssertionSet(assertions3);
        LinkedList<AssertionSet> sets3 = new LinkedList<AssertionSet>();
        sets3.add(set3);
        Policy expResult = Policy.createPolicy(null, "policy3", sets3);
        PolicyIntersector instance = PolicyIntersector.createStrictPolicyIntersector();
        Policy result = instance.intersect(policies);
        assertEquals(expResult, result);
    }

    public void testIntersectNull() {
        try {
            PolicyIntersector instance = PolicyIntersector.createStrictPolicyIntersector();
            Policy result = instance.intersect((Policy[])null);
            fail("Expected IllegalArgumentException, instead got policy result = " + result);
        } catch (IllegalArgumentException e) {
            // expected exception
        }
    }

    public void testIntersectOne() {
        AssertionData data1 = AssertionData.createAssertionData(new QName("testns", "name1"));
        PolicyAssertion assertion1 = new MockAssertion(data1);
        AssertionData data2 = AssertionData.createAssertionData(new QName("testns", "name2"));
        PolicyAssertion assertion2 = new MockAssertion(data2);
        AssertionData data3 = AssertionData.createAssertionData(new QName("testns", "name3"));
        PolicyAssertion assertion3 = new MockAssertion(data3);
        LinkedList<PolicyAssertion> assertions1 = new LinkedList<PolicyAssertion>();
        assertions1.add(assertion1);
        assertions1.add(assertion2);
        assertions1.add(assertion3);
        AssertionSet set1 = AssertionSet.createAssertionSet(assertions1);
        LinkedList<AssertionSet> sets1 = new LinkedList<AssertionSet>();
        sets1.add(set1);
        Policy policy = Policy.createPolicy(null, "policy1", sets1);
        PolicyIntersector instance = PolicyIntersector.createStrictPolicyIntersector();
        Policy expResult = Policy.createPolicy(null, "expectedpolicy", sets1);
        Policy result = instance.intersect(policy);
        assertEquals(expResult, result);
    }

    public void testPolicyIntersectorFactoryMethodsDoNotReturnNull() throws Exception {
        assertNotNull(PolicyIntersector.createStrictPolicyIntersector());
        assertNotNull(PolicyIntersector.createLaxPolicyIntersector());
    }

    public void testStrictIntersectEmptyPolicyCollectionThrowsIAE() throws Exception {
        try {
            Policy result = strictIntersector.intersect();
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
        // ok
        }
    }

    public void testStrictIntersectSinglePolicyInCollectionReturnsTheSameSinglePolicyInstance() throws Exception {
        Policy expected = Policy.createEmptyPolicy("fake", null);
        Policy result = strictIntersector.intersect(expected);

        assertTrue("Intersecting collection with single policy instance should return the very same policy instance", expected == result);
    }

    public void testStrictIntersectNullPolicyInCollectionReturnsNullPolicy() throws Exception {
        Collection<AssertionSet> alternatives = Arrays.asList(
                new AssertionSet[]{
                AssertionSet.createAssertionSet(
                Arrays.asList(new PolicyAssertion[]{
                new SimpleAssertion(AssertionData.createAssertionData(new QName("A")), null) {
                }}))});
        Collection<Policy> policies = new LinkedList<Policy>();
        for (int i = 0; i < 10; i++) {
            policies.add(Policy.createPolicy("fake", null, alternatives));
        }
        policies.add(Policy.createNullPolicy());

        Policy result = strictIntersector.intersect(policies.toArray(new Policy[policies.size()]));

        assertTrue("Intersecting collection with null policy instance should return null policy as a result", result.isNull());
    }

    public void testStrictIntersectEmptyPolicyInCollectionReturnsNullPolicy() throws Exception {
        Collection<AssertionSet> alternatives = Arrays.asList(
                new AssertionSet[]{
                AssertionSet.createAssertionSet(
                Arrays.asList(new PolicyAssertion[]{
                new SimpleAssertion(AssertionData.createAssertionData(new QName("A")), null) {
                }}))});
        Collection<Policy> policies = new LinkedList<Policy>();
        for (int i = 0; i < 10; i++) {
            policies.add(Policy.createPolicy("fake", null, alternatives));
        }
        policies.add(Policy.createEmptyPolicy());

        Policy result = strictIntersector.intersect(policies.toArray(new Policy[policies.size()]));

        assertTrue("Intersecting collection with empty policy instance should return null policy as a result", result.isNull());
    }

    public void testStrictIntersectAllPolicyInCollectionEmptyReturnsEmptyPolicy() throws Exception {
        Collection<Policy> policies = new LinkedList<Policy>();
        for (int i = 0; i < 10; i++) {
            policies.add(Policy.createEmptyPolicy("fake" + i, null));
        }

        Policy result = strictIntersector.intersect(policies.toArray(new Policy[policies.size()]));

        assertTrue("Intersecting collection with all policy instances empty should return empty policy as a result", result.isEmpty());
    }

    public void testStrictIntersectSimplePolicies() throws Exception {
        Policy p1 = PolicyResourceLoader.loadPolicy("intersection/policy1.xml");
        Policy p2 = PolicyResourceLoader.loadPolicy("intersection/policy2.xml");
        Policy p3 = PolicyResourceLoader.loadPolicy("intersection/policy3.xml");

        Policy expectedResult = PolicyResourceLoader.loadPolicy("intersection/policy1-2-3.xml");
        Policy result = strictIntersector.intersect(p1, p2, p3);
        assertEquals("Intersection did not provide expected result.", expectedResult, result);
    }

    public void testStrictInteroperablePolicies() throws Exception {
        performInteroperabilityTests("strict", strictIntersector);
    }

    public void testLaxInteroperablePolicies() throws Exception {
        performInteroperabilityTests("lax", laxIntersector);
    }

    public void performInteroperabilityTests(String mode, PolicyIntersector intersector) throws PolicyException, IOException {
        Policy p4 = PolicyResourceLoader.loadPolicy("intersection/policy4.xml");
        Policy p5 = PolicyResourceLoader.loadPolicy("intersection/policy5.xml");
        Policy p6 = PolicyResourceLoader.loadPolicy("intersection/policy6.xml");
        Policy p7 = PolicyResourceLoader.loadPolicy("intersection/policy7.xml");

        String testId;

        testId = "4-7-" + mode;
        performSingleIntersectionTest(
                intersector,
                testId,
                PolicyResourceLoader.loadPolicy("intersection/policy" + testId + ".xml"),
                p4, p7);
        testId = "7-4-" + mode;
        performSingleIntersectionTest(
                intersector,
                testId,
                PolicyResourceLoader.loadPolicy("intersection/policy" + testId + ".xml"),
                p7, p4);

        testId = "5-7-" + mode;
        performSingleIntersectionTest(
                intersector,
                testId,
                PolicyResourceLoader.loadPolicy("intersection/policy" + testId + ".xml"),
                p5, p7);
        testId = "7-5-" + mode;
        performSingleIntersectionTest(
                intersector,
                testId,
                PolicyResourceLoader.loadPolicy("intersection/policy" + testId + ".xml"),
                p7, p5);

        testId = "6-7-" + mode;
        performSingleIntersectionTest(
                intersector,
                testId,
                PolicyResourceLoader.loadPolicy("intersection/policy" + testId + ".xml"),
                p6, p7);
        testId = "7-6-" + mode;
        performSingleIntersectionTest(
                intersector,
                testId,
                PolicyResourceLoader.loadPolicy("intersection/policy" + testId + ".xml"),
                p7, p6);

        testId = "7-7-" + mode;
        performSingleIntersectionTest(
                intersector,
                testId,
                PolicyResourceLoader.loadPolicy("intersection/policy7-7.xml"),
                p7, p7);
    }

    public void testIntersectNamespaces() throws Exception {
        performSingleIntersectNamespacesTest(strictIntersector);
        performSingleIntersectNamespacesTest(laxIntersector);
    }

    private void performSingleIntersectionTest(PolicyIntersector intersector, String testId, Policy expectedResult, Policy... inputPolicies) {
        Policy result = intersector.intersect(inputPolicies);
        assertEquals("Intersection for test [" + testId + "] did not provide expected result.", expectedResult, result);
    }

    private void performSingleIntersectNamespacesTest(PolicyIntersector intersector) throws Exception {
        Collection<Policy> policies = new LinkedList<Policy>();
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.2.xml"));
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.2.xml"));
        Policy result = intersector.intersect(policies.toArray(new Policy[] {}));
        assertEquals(
                "When intersecting policies with same original namespace, the namespace should be preserved during intersection operation",
                NamespaceVersion.v1_2,
                result.getNamespaceVersion()
                );

        policies.clear();
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.5.xml"));
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.5.xml"));
        result = intersector.intersect(policies.toArray(new Policy[] {}));
        assertEquals(
                "When intersecting policies with same original namespace, the namespace should be preserved during intersection operation",
                NamespaceVersion.v1_5,
                result.getNamespaceVersion()
                );

        policies.clear();
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.2.xml"));
        policies.add(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.5.xml"));
        result = intersector.intersect(policies.toArray(new Policy[] {}));
        assertEquals(
                "When intersecting policies with different original namespace, the latest namespace should be preserved during intersection operation",
                NamespaceVersion.v1_5,
                result.getNamespaceVersion()
                );
    }


    private static class MockAssertion extends PolicyAssertion {

        MockAssertion(AssertionData data) {
            super(data, null);
        }

    }

}
