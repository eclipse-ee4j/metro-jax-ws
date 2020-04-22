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
import com.sun.xml.ws.policy.testutils.PolicyResourceLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.xml.namespace.QName;

/**
 *
 * @author Marek Potociar (marek.potociar@sun.com)
 */
public class PolicyAssertionTest extends AbstractPolicyApiClassTestBase {

    private static final String[] SINGLE_ALTERNATIVE_POLICY = new String[] {
        "single_alternative_policy/policy1.xml",
        "single_alternative_policy/policy2.xml",
        "single_alternative_policy/policy3.xml",
        "single_alternative_policy/policy4.xml",
        "single_alternative_policy/policy5.xml"
    };

    private static final QName attribName = new QName("http://foo.com", "attribute");
    private static final String attribValue = "avalue";
    private static final QName assertionName = new QName("http://foo.com", "assertion");
    private static final AssertionData data = AssertionData.createAssertionData(assertionName);
    private Map<QName, String> attributes;
    private PolicyAssertion a1;
    private PolicyAssertion a2;
    private PolicyAssertion a3;
    private PolicyAssertion a4;
    private PolicyAssertion a5;
    private PolicyAssertion assertionWithAttributes;

    public PolicyAssertionTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        a1 = new PolicyAssertion() {
        };
        a2 = new PolicyAssertion(null, null) {
        };
        a3 = new PolicyAssertion(data, null) {
        };
        a4 = new PolicyAssertion(data, new ArrayList<PolicyAssertion>(0)) {
        };
        a5 = new PolicyAssertion(data, Arrays.asList(new PolicyAssertion() {
        })) {
        };
        attributes = new HashMap<QName, String>();
        attributes.put(attribName, attribValue);
        assertionWithAttributes = new PolicyAssertion(AssertionData.createAssertionData(assertionName, "test", attributes, false, false), null) {
        };
    }

    @Override
    protected void tearDown() throws Exception {
    }

    protected PolicyAssertion[][] getEqualInstanceRows() throws Exception {
        Collection<PolicyAssertion[]> rows = new LinkedList<PolicyAssertion[]>();

        for (String name : SINGLE_ALTERNATIVE_POLICY) {
            Iterator<AssertionSet> setsA = PolicyResourceLoader.loadPolicy(name).iterator();
            Iterator<AssertionSet> setsB = PolicyResourceLoader.loadPolicy(name).iterator();

            if (setsA.hasNext()) {
                AssertionSet setA = setsA.next();
                AssertionSet setB = setsB.next();

                Iterator<PolicyAssertion> assertionsA = setA.iterator();
                Iterator<PolicyAssertion> assertionsB = setB.iterator();

                while (assertionsA.hasNext()) {
                    rows.add(new PolicyAssertion[]{assertionsA.next(), assertionsB.next()});
                }
            }
        }

        return rows.toArray(new PolicyAssertion[rows.size()][]);
    }

    public void testGetAttributesValueReturnsProperValue() throws Exception {
        QName headerParameterName = new QName("http://schemas.xmlsoap.org/ws/2005/07/securitypolicy", "Header");
        QName nameAttributeName = new QName("Name");
        QName namespaceAttributeName = new QName("Namespace");

        Policy policy = PolicyResourceLoader.loadPolicy("bug_reproduction/securityPolicy1.xml");
        AssertionSet alternative = policy.iterator().next();
        PolicyAssertion signedParts = alternative.get(new QName("http://schemas.xmlsoap.org/ws/2005/07/securitypolicy", "SignedParts")).iterator().next();
        Iterator<PolicyAssertion> iterator = signedParts.getParametersIterator();
        while (iterator.hasNext()) {
            PolicyAssertion assertion = iterator.next();
            if (assertion.getName().equals(headerParameterName)) {
                // System.out.println(assertion.toString());
                String nameValue = assertion.getAttributeValue(nameAttributeName);
                String namespaceValue = assertion.getAttributeValue(namespaceAttributeName);
                // System.out.println();
                // System.out.println("Name value: '" + nameValue + "'");
                // System.out.println("Namespace value: '" + namespaceValue + "'");
                // System.out.println("==========================================");
                assertNotNull("'Name' attribute of 'Header' parameter is expected to be not null.", nameValue);
                assertNotNull("'Namespace' attribute of 'Header' parameter is expected to be not null.", namespaceValue);
            }
        }
    }

    public void testGetName() {
        assertNull(a1.getName());
        assertNull(a2.getName());
        assertEquals(assertionName, a3.getName());
        assertEquals("Name equality check", assertionName, a4.getName());
        assertEquals("Name equality check", assertionName, a5.getName());
    }

    public void testHasParameters() {
        assertFalse(a1.hasParameters());
        assertFalse(a2.hasParameters());
        assertFalse(a3.hasParameters());
        assertFalse("Empty parameter list should result in no parameters", a4.hasParameters());
        assertTrue("Non-empty parameter list should result in existing parameters", a5.hasParameters());
    }

    public void testGetValue() {
        assertNull(a1.getValue());
        assertNull(a2.getValue());
        assertNull(a3.getValue());
        assertNull(a4.getValue());
        assertNull(a5.getValue());

        String expValue = "test";
        PolicyAssertion assertionWithValue = new PolicyAssertion(AssertionData.createAssertionData(assertionName, expValue, null, false, false), null) {
        };
        assertEquals(assertionWithValue.getValue(), expValue);
    }

    public void testGetAttributeSet() {
        assertTrue(a1.getAttributesSet().isEmpty());
        assertTrue(a2.getAttributesSet().isEmpty());
        assertTrue(a3.getAttributesSet().isEmpty());
        assertTrue(a4.getAttributesSet().isEmpty());
        assertTrue(a5.getAttributesSet().isEmpty());

        assertFalse(assertionWithAttributes.getAttributesSet().isEmpty());
    }

    public void testGetAttributes() {
        assertTrue(a1.getAttributes().isEmpty());
        assertTrue(a2.getAttributes().isEmpty());
        assertTrue(a3.getAttributes().isEmpty());
        assertTrue(a4.getAttributes().isEmpty());
        assertTrue(a5.getAttributes().isEmpty());

        assertFalse(assertionWithAttributes.getAttributes().isEmpty());
    }

    public void testGetAttributeValue() {
        assertNull(a1.getAttributeValue(attribName));
        assertNull(a2.getAttributeValue(attribName));
        assertNull(a3.getAttributeValue(attribName));
        assertNull(a4.getAttributeValue(attribName));
        assertNull(a5.getAttributeValue(attribName));

        assertEquals(attribValue, assertionWithAttributes.getAttributeValue(attribName));
    }

    public void testIsOptional() {
        assertFalse(a1.isOptional());
        assertFalse(a2.isOptional());
        assertFalse(a3.isOptional());
        assertFalse(a4.isOptional());
        assertFalse(a5.isOptional());

        AssertionData assertionData = AssertionData.createAssertionData(assertionName, "test", attributes, true, false);
        PolicyAssertion assertion = new PolicyAssertion(assertionData, null) {
        };
        assertTrue(assertion.isOptional());

        assertionData = AssertionData.createAssertionData(assertionName, "test", attributes, false, false);
        assertionData.setOptionalAttribute(true);
        assertion = new PolicyAssertion(assertionData, null) {
        };
        assertTrue(assertion.isOptional());
    }

    public void testIsIgnorable() {
        assertFalse(a1.isIgnorable());
        assertFalse(a2.isIgnorable());
        assertFalse(a3.isIgnorable());
        assertFalse(a4.isIgnorable());
        assertFalse(a5.isIgnorable());

        AssertionData assertionData = AssertionData.createAssertionData(assertionName, "test", attributes, false, true);
        PolicyAssertion assertion = new PolicyAssertion(assertionData, null) {
        };
        assertTrue(assertion.isIgnorable());

        assertionData = AssertionData.createAssertionData(assertionName, "test", attributes, false, false);
        assertionData.setIgnorableAttribute(true);
        assertion = new PolicyAssertion(assertionData, null) {
        };
        assertTrue(assertion.isIgnorable());
    }

    public void testIsPrivate() {
        assertFalse(a1.isPrivate());
        assertFalse(a2.isPrivate());
        assertFalse(a3.isPrivate());
        assertFalse(a4.isPrivate());
        assertFalse(a5.isPrivate());

        attributes.put(PolicyConstants.VISIBILITY_ATTRIBUTE, PolicyConstants.VISIBILITY_VALUE_PRIVATE);
        PolicyAssertion assertion = new PolicyAssertion(AssertionData.createAssertionData(assertionName, "test", attributes, false, false), null) {
        };
        assertTrue(assertion.isPrivate());
    }

}
