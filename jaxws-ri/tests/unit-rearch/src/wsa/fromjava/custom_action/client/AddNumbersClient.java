/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromjava.custom_action.client;

import java.util.Map;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Binding;
import jakarta.xml.ws.soap.AddressingFeature;

import junit.framework.TestCase;

/**
 * @author Rama Pulavarthi
 */
public class AddNumbersClient extends TestCase {
    public AddNumbersClient(String name) {
        super(name);
    }

    private AddNumbers createStub() throws Exception {
        return new AddNumbersService().getAddNumbersPort(new AddressingFeature());
    }

    public void testDefaultActions() throws Exception {
        int result = createStub().addNumbersNoAction(10, 10);
        assertEquals(20, result);
    }

    public void testEmptyActions() throws Exception {
        int result = createStub().addNumbersEmptyAction(10, 10);
        assertEquals(20, result);
    }

    public void testExplicitActions() throws Exception {
        AddNumbers port = createStub();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "http://example.com/input");
        int result = port.addNumbers(10, 10);
        assertEquals(20, result);
    }

    public void testExplicitActions2() throws Exception {
        AddNumbers port = createStub();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "http://example.com/input2");
        int result = port.addNumbers2(10, 10);
        assertEquals(20, result);
    }

    public void testDefaultOutputActionWithInputSpecified() throws Exception {
        AddNumbers port = createStub();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "http://example.com/input3");
        int result = port.addNumbers3(10, 10);
        assertEquals(20, result);
    }

    public void testOneFault() throws Exception {
        try {
            AddNumbers port = createStub();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "finput1");
            port.addNumbersFault1(-10, 10);
            fail("AddNumbersException_Exception MUST be thrown");
        } catch (AddNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testTwoFaults_ExplicitAddNumbers() throws Exception {
        try {
            AddNumbers port = createStub();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "finput2");
            port.addNumbersFault2(-10, 10);
            fail("AddNumbersException_Exception MUST be thrown");
        } catch (AddNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testTwoFaults_ExplicitTooBigNumbers() throws Exception {
        try {
            AddNumbers port = createStub();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "finput2");
            port.addNumbersFault2(20, 10);
            fail("TooBigNumbersException_Exception MUST be thrown");
        } catch (TooBigNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testTwoFaults_OnlyAddNumbersSpecified_AddNumbers() throws Exception {
        try {
            AddNumbers port = createStub();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "finput3");
            port.addNumbersFault3(-10, 10);
            fail("AddNumbersException_Exception MUST be thrown");
        } catch (AddNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testTwoFaults_OnlyAddNumbersSpecified_TooBigNumbers() throws Exception {
        try {
            AddNumbers port = createStub();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "finput3");
            port.addNumbersFault3(20, 10);
            fail("TooBigNumbersException_Exception MUST be thrown");
        } catch (TooBigNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testOnlyFaultActions_OnlyAddNumbersSpecified_AddNumbers() throws Exception {
        try {
            createStub().addNumbersFault4(-10, 10);
            fail("AddNumbersException_Exception MUST be thrown");
        } catch (AddNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testOnlyFaultActions_OnlyAddNumbersSpecified_TooBigNumbers() throws Exception {
        try {
            createStub().addNumbersFault4(20, 10);
            fail("TooBigNumbersException_Exception MUST be thrown");
        } catch (TooBigNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testOnlyFaultActions_OnlyTooBigNumbersSpecified_AddNumbers() throws Exception {
        try {
            createStub().addNumbersFault5(-10, 10);
            fail("AddNumbersException_Exception MUST be thrown");
        } catch (AddNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testOnlyFaultActions_OnlyTooBigNumbersSpecified_TooBigNumbers() throws Exception {
        try {
            createStub().addNumbersFault5(20, 10);
            fail("TooBigNumbersException_Exception MUST be thrown");
        } catch (TooBigNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testOnlyFaultActions_BothSpecified_AddNumbers() throws Exception {
        try {
            createStub().addNumbersFault6(-10, 10);
            fail("AddNumbersException_Exception MUST be thrown");
        } catch (AddNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testOnlyFaultActions_BothSpecified_TooBigNumbers() throws Exception {
        try {
            createStub().addNumbersFault6(20, 10);
            fail("TooBigNumbersException_Exception MUST be thrown");
        } catch (TooBigNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testOnlyFaultActions_BothEmptyString_AddNumbers() throws Exception {
        try {
            createStub().addNumbersFault7(-10, 10);
            fail("AddNumbersException_Exception MUST be thrown");
        } catch (AddNumbersException_Exception ex) {
            assertTrue(true);
        }
    }

    public void testOnlyFaultActions_BothEmptyString_TooBigNumbers() throws Exception {
        try {
            createStub().addNumbersFault7(20, 10);
            fail("TooBigNumbersException_Exception MUST be thrown");
        } catch (TooBigNumbersException_Exception ex) {
            assertTrue(true);
        }
    }
}
