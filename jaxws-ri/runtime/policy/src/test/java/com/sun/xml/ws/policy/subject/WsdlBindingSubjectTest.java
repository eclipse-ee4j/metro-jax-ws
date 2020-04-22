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

import com.sun.xml.ws.policy.subject.WsdlBindingSubject.WsdlMessageType;
import javax.xml.namespace.QName;
import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class WsdlBindingSubjectTest extends TestCase {

    private QName serviceName;
    private QName portName;

    public WsdlBindingSubjectTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        serviceName = new QName("namesp", "service");
        portName = new QName("namesp", "port");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of createBindingSubject method, of class WsdlRoot.
     */
    public void testCreateBindingSubject() {
        QName bindingName = new QName("namesp", "binding");
        WsdlBindingSubject result = WsdlBindingSubject.createBindingSubject(bindingName);
        assertEquals(bindingName, result.getName());
    }

    /**
     * Test of createBindingOperationSubject method, of class WsdlRoot.
     */
    public void testCreateBindingOperationSubject() {
        QName bindingName = new QName("namesp", "binding");
        QName operationName = new QName("namesp", "operation");
        WsdlBindingSubject result = WsdlBindingSubject.createBindingOperationSubject(bindingName, operationName);
        assertEquals(operationName, result.getName());
    }

    /**
     * Test of createBindingMessageSubject method, of class WsdlRoot.
     */
    public void testCreateBindingMessageSubjectInput() {
        QName bindingName = new QName("namesp", "binding");
        QName operationName = new QName("namesp", "operation");
        QName messageName = new QName("namesp", "message");
        WsdlMessageType messageType = WsdlMessageType.INPUT;
        WsdlBindingSubject result = WsdlBindingSubject.createBindingMessageSubject(bindingName, operationName, messageName, messageType);
        assertEquals(messageName, result.getName());
    }

    /**
     * Test of createBindingMessageSubject method, of class WsdlRoot.
     */
    public void testCreateBindingMessageSubjectOutput() {
        QName bindingName = new QName("namesp", "binding");
        QName operationName = new QName("namesp", "operation");
        QName messageName = new QName("namesp", "message");
        WsdlMessageType messageType = WsdlMessageType.OUTPUT;
        WsdlBindingSubject result = WsdlBindingSubject.createBindingMessageSubject(bindingName, operationName, messageName, messageType);
        assertEquals(messageName, result.getName());
    }

    /**
     * Test of createBindingMessageSubject method, of class WsdlRoot.
     */
    public void testCreateBindingMessageSubjectFault() {
        QName bindingName = new QName("namesp", "binding");
        QName operationName = new QName("namesp", "operation");
        QName messageName = new QName("namesp", "message");
        WsdlMessageType messageType = WsdlMessageType.FAULT;
        WsdlBindingSubject result = WsdlBindingSubject.createBindingMessageSubject(bindingName, operationName, messageName, messageType);
        assertEquals(messageName, result.getName());
    }

    /**
     * Test of createBindingMessageSubject method, of class WsdlRoot.
     */
    public void testCreateBindingMessageSubjectNoMessage() {
        QName bindingName = new QName("namesp", "binding");
        QName operationName = new QName("namesp", "operation");
        QName messageName = new QName("namesp", "message");
        WsdlMessageType messageType = WsdlMessageType.NO_MESSAGE;
        try {
            WsdlBindingSubject result = WsdlBindingSubject.createBindingMessageSubject(bindingName, operationName, messageName, messageType);
            fail("Expected an IllegalArgumentException, instead got result = " + result);
        } catch (IllegalArgumentException e) {
            // Expected this exception
        }
    }

    /**
     * Test of createBindingMessageSubject method, of class WsdlRoot.
     */
    public void testCreateBindingMessageSubjectNull() {
        QName bindingName = new QName("namesp", "binding");
        QName operationName = new QName("namesp", "operation");
        QName messageName = new QName("namesp", "message");
        WsdlMessageType messageType = null;
        try {
            WsdlBindingSubject result = WsdlBindingSubject.createBindingMessageSubject(bindingName, operationName, messageName, messageType);
            fail("Expected an IllegalArgumentException, instead got result = " + result);
        } catch (IllegalArgumentException e) {
            // Expected this exception
        }
    }

    /**
     * Test of getMessageType method, of class WsdlSubject.
     */
    public void testGetMessageTypeOutput() {
        QName bindingName = new QName("namesp", "binding");
        QName operationName = new QName("namesp", "operation");
        QName messageName = new QName("namesp", "message");
        WsdlMessageType expResult = WsdlMessageType.OUTPUT;
        WsdlBindingSubject instance = WsdlBindingSubject.createBindingMessageSubject(bindingName, operationName, messageName, expResult);
        WsdlMessageType result = instance.getMessageType();
        assertEquals(expResult, result);
    }

    /**
     * Test of isBindingSubject method, of class WsdlSubject.
     */
    public void testIsBindingSubject() {
        QName bindingName = new QName("namesp", "binding");
        WsdlBindingSubject instance = WsdlBindingSubject.createBindingSubject(bindingName);
        boolean result = instance.isBindingSubject();
        assertTrue(result);
    }

    /**
     * Test of isBindingOperationSubject method, of class WsdlSubject.
     */
    public void testIsBindingOperationSubject() {
        QName bindingName = new QName("namesp", "binding");
        QName operationName = new QName("namesp", "operation");
        WsdlBindingSubject instance = WsdlBindingSubject.createBindingOperationSubject(bindingName, operationName);
        boolean result = instance.isBindingOperationSubject();
        assertTrue(result);
    }

    /**
     * Test of isBindingMessageSubject method, of class WsdlSubject.
     */
    public void testIsBindingMessageSubject() {
        QName bindingName = new QName("namesp", "binding");
        QName operationName = new QName("namesp", "operation");
        QName messageName = new QName("namesp", "message");
        WsdlMessageType messageType = WsdlMessageType.INPUT;
        WsdlBindingSubject instance = WsdlBindingSubject.createBindingMessageSubject(bindingName, operationName, messageName, messageType);
        boolean result = instance.isBindingMessageSubject();
        assertTrue(result);
    }

    /**
     * Test of equals method, of class WsdlSubject.
     */
    public void testEqualsEqual() {
        QName portTypeName = new QName("namesp", "porttype");
        QName operationName = new QName("namesp", "operation");
        QName messageName = new QName("namesp", "message");
        WsdlMessageType messageType = WsdlMessageType.OUTPUT;
        Object that = WsdlBindingSubject.createBindingMessageSubject(portTypeName, operationName, messageName, messageType);
        WsdlBindingSubject instance = WsdlBindingSubject.createBindingMessageSubject(portTypeName, operationName, messageName, messageType);
        assertEquals(instance, that);
    }

    /**
     * Test of equals method, of class WsdlSubject.
     */
    public void testEqualsNotEqual() {
        QName portTypeName = new QName("namesp", "porttype");
        QName operationName = new QName("namesp", "operation");
        QName messageName = new QName("namesp", "message");
        Object that = WsdlBindingSubject.createBindingMessageSubject(portTypeName, operationName, messageName, WsdlMessageType.OUTPUT);
        WsdlBindingSubject instance = WsdlBindingSubject.createBindingMessageSubject(portTypeName, operationName, messageName, WsdlMessageType.INPUT);
        boolean result = instance.equals(that);
        assertFalse(result);
    }

    /**
     * Test of hashCode method, of class WsdlSubject.
     */
    public void testHashCode() {
        QName bindingName = new QName("namesp", "binding");
        QName operationName = new QName("namesp", "operation");
        WsdlBindingSubject instance1 = WsdlBindingSubject.createBindingOperationSubject(bindingName, operationName);
        WsdlBindingSubject instance2 = WsdlBindingSubject.createBindingOperationSubject(bindingName, operationName);
        assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    /**
     * Test of toString method, of class WsdlSubject.
     */
    public void testToString() {
        QName bindingName = new QName("namesp", "binding");
        QName operationName = new QName("namesp", "operation");
        WsdlBindingSubject instance = WsdlBindingSubject.createBindingOperationSubject(bindingName, operationName);
        String result = instance.toString();
        assertNotNull(result);
    }

}
