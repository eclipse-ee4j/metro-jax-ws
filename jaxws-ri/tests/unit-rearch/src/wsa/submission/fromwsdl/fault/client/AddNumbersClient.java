/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.fault.client;

import jakarta.xml.ws.soap.AddressingFeature;

import junit.framework.TestCase;
import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;


/**
 * @author Arun Gupta
 */
public class AddNumbersClient extends TestCase {
    public AddNumbersClient(String name) {
        super(name);
    }
    private static final MemberSubmissionAddressingFeature ENABLED_ADDRESSING_FEATURE = new MemberSubmissionAddressingFeature(true);

    private AddNumbersPortType createStub() throws Exception {
        return new AddNumbersService().getPort(AddNumbersPortType.class, ENABLED_ADDRESSING_FEATURE);
    }

    public void testAddNumbersDefaultAddNumbersAction() throws Exception {
        try {
            createStub().addNumbers(-10, 10);
            fail("AddNumbersFault_Exception must be thrown");
        } catch (AddNumbersFault_Exception ex)  {
            assertTrue(true);
        }
    }

    public void testAddNumbersDefaultTooBigNumbersAction() throws Exception {
        try {
            createStub().addNumbers(20, 20);
            fail("TooBigNumbersFault_Exception must be thrown");
        } catch (TooBigNumbersFault_Exception ex)  {
            assertTrue(true);
        }
    }

    public void testAddNumbers2ExplicitAddNumbersAction() throws Exception {
        try {
            createStub().addNumbers2(-10, 10);
            fail("AddNumbers2Fault must be thrown");
        } catch (AddNumbers2Fault ex)  {
            assertTrue(true);
        }
    }

    public void testAddNumbers2ExplicitTooBigNumbersAction() throws Exception {
        try {
            createStub().addNumbers2(20, 20);
            fail("TooBigNumbers2Fault must be thrown");
        } catch (TooBigNumbers2Fault ex)  {
            assertTrue(true);
        }
    }

    public void testAddNumbers3ExplicitAddNumbersAction() throws Exception {
        try {
            createStub().addNumbers3(-10, 10);
            fail("AddNumbers3Fault must be thrown");
        } catch (AddNumbers3Fault ex)  {
            assertTrue(true);
        }
    }

    public void testAddNumbers3DefaultTooBigNumbersAction() throws Exception {
        try {
            createStub().addNumbers3(20, 20);
            fail("TooBigNumbers3Fault must be thrown");
        } catch (TooBigNumbers3Fault ex)  {
            assertTrue(true);
        }
    }

    public void testAddNumbers4DefaultAddNumbersAction() throws Exception {
        try {
            createStub().addNumbers4(-10, 10);
            fail("AddNumbersFault must be thrown");
        } catch (AddNumbers4Fault ex)  {
            assertTrue(true);
        }
    }

    public void testAddNumbers4ExplicitTooBigNumbersAction() throws Exception {
        try {
            createStub().addNumbers4(20, 20);
            fail("TooBigNumbers4Fault must be thrown");
        } catch (TooBigNumbers4Fault ex)  {
            assertTrue(true);
        }
    }

    public void testAddNumbers5ExplicitAddNumbersAction() throws Exception {
        try {
            createStub().addNumbers5(-10, 20);
            fail("AddNumbers5Fault must be thrown");
        } catch (AddNumbers5Fault ex)  {
            assertTrue(true);
        }
    }

    public void testAddNumbers6EmptyAddNumbersAction() throws Exception {
        try {
            createStub().addNumbers6(-10, 20);
            fail("AddNumbers6Fault must be thrown");
        } catch (AddNumbers6Fault ex)  {
            assertTrue(true);
        }
    }
}
