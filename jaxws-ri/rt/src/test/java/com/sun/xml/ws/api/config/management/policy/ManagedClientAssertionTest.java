/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.config.management.policy;

import com.sun.xml.ws.api.config.management.policy.ManagementAssertion.Setting;
import com.sun.xml.ws.policy.sourcemodel.AssertionData;
import com.sun.xml.ws.policy.spi.AssertionCreationException;

import java.util.HashMap;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class ManagedClientAssertionTest extends TestCase {
    /**
     * The name of the id attribute of the ManagedClient policy assertion.
     */
    private static final QName ID_ATTRIBUTE_QNAME = new QName("", "id");
    /**
     * The name of the start attribute of the ManagedClient policy assertion.
     */
    private static final QName START_ATTRIBUTE_QNAME = new QName("start");
    private static final QName MANAGEMENT_ATTRIBUTE_QNAME = new QName("management");
    private static final QName MONITORING_ATTRIBUTE_QNAME = new QName("monitoring");

    public ManagedClientAssertionTest(String testName) {
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
     * Test of getId method, of class ManagedClientAssertion.
     * @throws AssertionCreationException
     */
    public void testGetId() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        String expResult = "id1";
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class ManagedClientAssertion.
     * @throws AssertionCreationException 
     */
    public void testNoId() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        final ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        final String result = instance.getId();
        assertNull(result);
    }

    /**
     * Test of getId method, of class ManagedClientAssertion.
     * @throws AssertionCreationException
     */
    public void testNoIdManagementDisabled() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(MANAGEMENT_ATTRIBUTE_QNAME, "false");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        String result = instance.getId();
        assertNull(result);
    }

    /**
     * Test of getStart method, of class ManagedClientAssertion.
     * @throws AssertionCreationException
     */
    public void testGetStart() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        attributes.put(START_ATTRIBUTE_QNAME, "notify");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        String expResult = "notify";
        String result = instance.getStart();
        assertEquals(expResult, result);
    }

    public void testIsManagementEnabled() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        final ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        final boolean result = instance.isManagementEnabled();
        assertFalse(result);
    }

    public void testIsManagementEnabledTrue() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        attributes.put(MANAGEMENT_ATTRIBUTE_QNAME, "true");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        final ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        final boolean result = instance.isManagementEnabled();
        assertFalse(result);
    }

    public void testIsManagementEnabledOn() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        attributes.put(MANAGEMENT_ATTRIBUTE_QNAME, "on");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        final ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        final boolean result = instance.isManagementEnabled();
        assertFalse(result);
    }

    public void testIsManagementEnabledFalse() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        attributes.put(MANAGEMENT_ATTRIBUTE_QNAME, "false");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        final ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        final boolean result = instance.isManagementEnabled();
        assertFalse(result);
    }

    public void testIsManagementEnabledOff() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        attributes.put(MANAGEMENT_ATTRIBUTE_QNAME, "off");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        final ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        final boolean result = instance.isManagementEnabled();
        assertFalse(result);
    }

    public void testMonitoringAttribute() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        final ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        final Setting result = instance.monitoringAttribute();
        assertSame(result, Setting.NOT_SET);
    }

    public void testMonitoringAttributeTrue() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        attributes.put(MONITORING_ATTRIBUTE_QNAME, "true");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        final ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        final Setting result = instance.monitoringAttribute();
        assertSame(result, Setting.ON);
    }

    public void testMonitoringAttributeOn() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        attributes.put(MONITORING_ATTRIBUTE_QNAME, "on");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        final ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        final Setting result = instance.monitoringAttribute();
        assertSame(result, Setting.ON);
    }

    public void testMonitoringAttributeFalse() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        attributes.put(MONITORING_ATTRIBUTE_QNAME, "false");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        final ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        final Setting result = instance.monitoringAttribute();
        assertSame(result, Setting.OFF);
    }

    public void testMonitoringAttributeOff() throws AssertionCreationException {
        final HashMap<QName, String> attributes = new HashMap<QName, String>();
        attributes.put(ID_ATTRIBUTE_QNAME, "id1");
        attributes.put(MONITORING_ATTRIBUTE_QNAME, "off");
        final AssertionData data = AssertionData.createAssertionData(ManagedClientAssertion.MANAGED_CLIENT_QNAME,
                null, attributes, false, false);
        final ManagedClientAssertion instance = new ManagedClientAssertion(data, null);
        final Setting result = instance.monitoringAttribute();
        assertSame(result, Setting.OFF);
    }

}
