/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.spi;

import com.sun.xml.ws.policy.PolicyAssertion;
import com.sun.xml.ws.policy.sourcemodel.AssertionData;
import com.sun.xml.ws.policy.spi.PolicyAssertionValidator.Fitness;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.xml.namespace.QName;
import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class AbstractQNameValidatorTest extends TestCase {
    
    private AbstractQNameValidator mockValidator;
    
    public AbstractQNameValidatorTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        Collection<QName> serverSideAssertions = new ArrayList<QName>();
        Collection<QName> clientSideAssertions = new ArrayList<QName>();
        serverSideAssertions.add(new QName("testns", "testlocal"));
        clientSideAssertions.add(new QName("testclientns", "testclientlocal"));
        mockValidator = new MockQNameValidator(serverSideAssertions, clientSideAssertions);
    }

    /**
     * Test of declareSupportedDomains method, of class AbstractQNameValidator.
     */
    public void testDeclareSupportedDomains() {
        AbstractQNameValidator instance = mockValidator;
        String[] expResult = { "testns", "testclientns" };
        Arrays.sort(expResult);
        String[] result = instance.declareSupportedDomains();
        Arrays.sort(result);
        assertEquals(Arrays.asList(expResult), Arrays.asList(result));
    }

    /**
     * Test of validateClientSide method, of class AbstractQNameValidator.
     */
    public void testValidateClientSide() {
        PolicyAssertion assertion = new MockPolicyAssertion(AssertionData.createAssertionData(new QName("test1", "test2")));
        AbstractQNameValidator instance = mockValidator;
        Fitness expResult = PolicyAssertionValidator.Fitness.UNKNOWN;
        Fitness result = instance.validateClientSide(assertion);
        assertEquals(expResult, result);
    }

    public void testValidateClientSideEmpty() {
        PolicyAssertion assertion = new MockPolicyAssertion(AssertionData.createAssertionData(new QName("test1", "test2")));
        AbstractQNameValidator instance = new MockQNameValidator(null, null);
        Fitness expResult = PolicyAssertionValidator.Fitness.UNKNOWN;
        Fitness result = instance.validateClientSide(assertion);
        assertEquals(expResult, result);
    }

    public void testValidateClientSideSupported() {
        PolicyAssertion assertion = new MockPolicyAssertion(AssertionData.createAssertionData(MockPolicyAssertionValidator.CLIENT_ASSERTION_NAME));
        AbstractQNameValidator instance = new MockPolicyAssertionValidator();
        Fitness expResult = PolicyAssertionValidator.Fitness.SUPPORTED;
        Fitness result = instance.validateClientSide(assertion);
        assertEquals(expResult, result);
    }

    /**
     * Test of validateServerSide method, of class AbstractQNameValidator.
     */
    public void testValidateServerSide() {
        PolicyAssertion assertion = new MockPolicyAssertion(AssertionData.createAssertionData(new QName("testa", "testb")));
        AbstractQNameValidator instance = mockValidator;
        Fitness expResult = PolicyAssertionValidator.Fitness.UNKNOWN;
        Fitness result = instance.validateServerSide(assertion);
        assertEquals(expResult, result);
    }

    public void testValidateServerSideEmpty() {
        PolicyAssertion assertion = new MockPolicyAssertion(AssertionData.createAssertionData(new QName("testa", "testb")));
        AbstractQNameValidator instance = new MockQNameValidator(null, null);
        Fitness expResult = PolicyAssertionValidator.Fitness.UNKNOWN;
        Fitness result = instance.validateServerSide(assertion);
        assertEquals(expResult, result);
    }

    public void testValidateServerSideSupported() {
        PolicyAssertion assertion = new MockPolicyAssertion(AssertionData.createAssertionData(MockPolicyAssertionValidator.SERVER_ASSERTION_NAME));
        AbstractQNameValidator instance = new MockPolicyAssertionValidator();
        Fitness expResult = PolicyAssertionValidator.Fitness.SUPPORTED;
        Fitness result = instance.validateServerSide(assertion);
        assertEquals(expResult, result);
    }

    
    private static class MockQNameValidator extends AbstractQNameValidator {
    
        MockQNameValidator(Collection<QName> serverSideAssertions, Collection<QName> clientSideAssertions) {
            super(serverSideAssertions, clientSideAssertions);
        }

    }
    
    
    private static class MockPolicyAssertion extends PolicyAssertion {
        
        MockPolicyAssertion(AssertionData assertion) {
            super(assertion, null);
        }
    }

}
