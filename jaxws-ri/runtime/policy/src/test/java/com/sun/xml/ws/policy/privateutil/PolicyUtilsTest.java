/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.privateutil;

import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.spi.PolicyAssertionCreator;

import java.io.Closeable;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import javax.xml.stream.XMLStreamReader;

import junit.framework.TestCase;

/**
 *
 * @author Marek Potociar
 */
public class PolicyUtilsTest extends TestCase {

    public PolicyUtilsTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
    }

    /**
     * Test of getStackMethodName method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.Commons.
     */
    public void testCommonsGetStackMethodName() {
        int index;
        String expResult, result;

        index = 0;
        expResult = "getStackMethodName";
        result = PolicyUtils.Commons.getStackMethodName(index);
        assertEquals(expResult, result);

        index++;
        expResult = "testCommonsGetStackMethodName";
        result = PolicyUtils.Commons.getStackMethodName(index);
        assertEquals(expResult, result);
    }

    public void testGetCallerMethodName() {
        class TestCall {
            public void testCall() {
                String expResult, result;

                expResult = "testGetCallerMethodName";
                result = PolicyUtils.Commons.getCallerMethodName();
                assertEquals(expResult, result);
            }
        }

        TestCall tc = new TestCall();
        tc.testCall();
    }

    /**
     * Test of closeResource method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.IO.
     */
    public void testIOCloseResourceCloseableNull() {
        PolicyUtils.IO.closeResource((Closeable) null);
    }

    /**
     * Test of closeResource method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.IO.
     */
    public void testIOCloseResourceCloseable() {
        final MockCloseable closeable = new MockCloseable();
        PolicyUtils.IO.closeResource(closeable);
        assertTrue(closeable.isClosed());
    }

    /**
     * Test of closeResource method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.IO.
     */
    public void testIOCloseResourceXMLStreamReaderNull() {
        PolicyUtils.IO.closeResource((XMLStreamReader) null);
    }

    /**
     * Test of createIndent method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.Text.
     */
    public void testTextCreateIndent() {
        int indentLevel;
        String expResult, result;

        indentLevel = 0;
        expResult = "";
        result = PolicyUtils.Text.createIndent(indentLevel);
        assertEquals(expResult, result);


        indentLevel = 1;
        expResult = "    ";
        result = PolicyUtils.Text.createIndent(indentLevel);
        assertEquals(expResult, result);

        indentLevel = 2;
        expResult = "        ";
        result = PolicyUtils.Text.createIndent(indentLevel);
        assertEquals(expResult, result);
    }

    /**
     * Test of compareBoolean method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.Comparison.
     */
    public void testComparisonCompareBoolean() {
        boolean b1, b2;
        int expResult, result;

        b1 = true;
        b2 = true;
        expResult = 0;
        result = PolicyUtils.Comparison.compareBoolean(b1, b2);
        assertEquals(expResult, result);

        b1 = false;
        b2 = false;
        expResult = 0;
        result = PolicyUtils.Comparison.compareBoolean(b1, b2);
        assertEquals(expResult, result);

        b1 = false;
        b2 = true;
        expResult = -1;
        result = PolicyUtils.Comparison.compareBoolean(b1, b2);
        assertEquals(expResult, result);

        b1 = true;
        b2 = false;
        expResult = 1;
        result = PolicyUtils.Comparison.compareBoolean(b1, b2);
        assertEquals(expResult, result);
    }

    /**
     * Test of compareNullableStrings method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.Comparison.
     */
    public void testComparisonCompareNullableStrings() {
        String s1, s2;
        int expResult, result;

        s1 = null;
        s2 = null;
        expResult = 0;
        result = PolicyUtils.Comparison.compareNullableStrings(s1, s2);
        assertEquals(expResult, result);

        s1 = "";
        s2 = "";
        expResult = 0;
        result = PolicyUtils.Comparison.compareNullableStrings(s1, s2);
        assertEquals(expResult, result);

        s1 = "abc";
        s2 = "abc";
        expResult = 0;
        result = PolicyUtils.Comparison.compareNullableStrings(s1, s2);
        assertEquals(expResult, result);

        s1 = null;
        s2 = "";
        expResult = -1;
        result = PolicyUtils.Comparison.compareNullableStrings(s1, s2);
        assertEquals(expResult, result);

        s1 = null;
        s2 = "abc";
        expResult = -1;
        result = PolicyUtils.Comparison.compareNullableStrings(s1, s2);
        assertEquals(expResult, result);

        s1 = "abc";
        s2 = "abd";
        expResult = -1;
        result = PolicyUtils.Comparison.compareNullableStrings(s1, s2);
        assertEquals(expResult, result);

        s1 = "";
        s2 = null;
        expResult = 1;
        result = PolicyUtils.Comparison.compareNullableStrings(s1, s2);
        assertEquals(expResult, result);

        s1 = "abc";
        s2 = null;
        expResult = 1;
        result = PolicyUtils.Comparison.compareNullableStrings(s1, s2);
        assertEquals(expResult, result);

        s1 = "abd";
        s2 = "abc";
        expResult = 1;
        result = PolicyUtils.Comparison.compareNullableStrings(s1, s2);
        assertEquals(expResult, result);
    }

    /**
     * Test of combine method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.Collections.
     */
    public void testCollectionsCombine() {
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of invoke method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.Reflection.
     */
    public void testReflectionInvoke() {
        final Object target = new NamedObject();
        final String result = PolicyUtils.Reflection.invoke(target, "hum", String.class, null, null);
        assertEquals("hum", result);
    }

    /**
     * Test of invoke method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.Reflection.
     */
    public void testReflectionInvokeFail() {
        final Object target = new Object() { public String hum() { return "hum"; } };
        try {
            final String result = PolicyUtils.Reflection.invoke(target, "humv", String.class, null, null);
            fail("Expected RuntimePolicyUtilsException, instead got " + result);
        } catch (RuntimePolicyUtilsException e) {
            // expected
        }
    }

    /**
     * Test of generateFullName method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.ConfigFile.
     */
    public void testConfigFileGenerateFullName() throws PolicyException {
        String configFileIdentifier = "test";

        String expResult = "wsit-test.xml";
        String result = PolicyUtils.ConfigFile.generateFullName(configFileIdentifier);
        assertEquals(expResult, result);
    }

    /**
     * Test of generateFullName method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.ConfigFile.
     */
    public void testConfigFileGenerateFullNameNull() {
        String configFileIdentifier = null;
        try {
           String result = PolicyUtils.ConfigFile.generateFullName(configFileIdentifier);
           fail("Expected PolicyException, got result = " + result);
        } catch (PolicyException e) {
            // expected an exception
        }
    }

    /**
     * Test of load method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.ServiceProvider.
     */
    public void testServiceProviderLoad() {
        List<PolicyAssertionCreator> result = ServiceLoader.load(PolicyAssertionCreator.class, this.getClass().getClassLoader())
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
        assertEquals(1, result.size());

        assertEquals(MockPolicyAssertionCreator.class, result.get(0).getClass());
    }

    public void testRtf2396Unquote() {
        assertEquals("hello Vasku", PolicyUtils.Rfc2396.unquote("hello%20Vasku"));
    }

    public void testRtf2396UnquoteCutOff() {
        try {
            final String result = PolicyUtils.Rfc2396.unquote("hello%2");
            fail("Expected RuntimePolicyUtilsException, got " + result);
        } catch (RuntimePolicyUtilsException e) {
            // expected
        }
    }


    private class MockCloseable implements Closeable {

        private boolean isClosed = false;

        public void close() {
            isClosed = true;
        }

        public boolean isClosed() {
            return isClosed;
        }

    }

    public static class NamedObject {
        public String hum() { return "hum"; };
    }

}
