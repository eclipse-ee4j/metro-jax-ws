/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.subject;

import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.PolicyMapKey;
import javax.xml.namespace.QName;
import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class PolicyMapKeyConverterTest extends TestCase {
    
    private QName serviceName;
    private QName portName;
    private QName bindingName;
    private QName operationName;
    private QName portTypeName;
    private QName faultName;
    private QName messageName;
    private PolicyMapKeyConverter converter;

    public PolicyMapKeyConverterTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        serviceName = new QName("namesp", "service");
        portName = new QName("namesp", "port");
        bindingName = new QName("namesp", "binding");
        operationName = new QName("namesp", "bindingoperation");
        portTypeName = new QName("namesp", "porttype");
        faultName = new QName("namesp", "fault");
        messageName = new QName("namesp", "message");
        this.converter = new PolicyMapKeyConverter(serviceName, portName);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getPolicyMapKey method, of class PolicyMapKeyConverter.
     */
    public void testGetPolicyMapKeyBinding() {
        WsdlBindingSubject subject = WsdlBindingSubject.createBindingSubject(bindingName);
        PolicyMapKey expResult = PolicyMap.createWsdlEndpointScopeKey(serviceName, portName);
        PolicyMapKey result = converter.getPolicyMapKey(subject);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPolicyMapKey method, of class PolicyMapKeyConverter.
     */
    public void testGetPolicyMapKeyBindingOperation() {
        WsdlBindingSubject subject = WsdlBindingSubject.createBindingOperationSubject(bindingName, operationName);
        PolicyMapKey expResult = PolicyMap.createWsdlOperationScopeKey(serviceName, portName, operationName);
        PolicyMapKey result = converter.getPolicyMapKey(subject);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPolicyMapKey method, of class PolicyMapKeyConverter.
     */
    public void testGetPolicyMapKeyBindingInput() {
        WsdlBindingSubject subject = WsdlBindingSubject.createBindingMessageSubject(bindingName, operationName, null, WsdlBindingSubject.WsdlMessageType.INPUT);
        PolicyMapKey expResult = PolicyMap.createWsdlMessageScopeKey(serviceName, portName, operationName);
        PolicyMapKey result = converter.getPolicyMapKey(subject);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPolicyMapKey method, of class PolicyMapKeyConverter.
     */
    public void testGetPolicyMapKeyBindingOutput() {
        WsdlBindingSubject subject = WsdlBindingSubject.createBindingMessageSubject(bindingName, operationName, null, WsdlBindingSubject.WsdlMessageType.OUTPUT);
        PolicyMapKey expResult = PolicyMap.createWsdlMessageScopeKey(serviceName, portName, operationName);
        PolicyMapKey result = converter.getPolicyMapKey(subject);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPolicyMapKey method, of class PolicyMapKeyConverter.
     */
    public void testGetPolicyMapKeyBindingFault() {
        WsdlBindingSubject subject = WsdlBindingSubject.createBindingMessageSubject(bindingName, operationName, faultName, WsdlBindingSubject.WsdlMessageType.FAULT);
        PolicyMapKey expResult = PolicyMap.createWsdlFaultMessageScopeKey(serviceName, portName, operationName, faultName);
        PolicyMapKey result = converter.getPolicyMapKey(subject);
        assertEquals(expResult, result);
    }

}
