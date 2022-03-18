/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.requiredfalse.client;

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

    public void testDefaultActions() throws Exception {
        int result = createStub().addNumbers(10, 10);
        assertTrue(result == 20);
    }

    public void testActionWithExplicitNames() throws Exception {
        int result = createStub().addNumbers2(10, 10);
        assertEquals(20, result);
    }

    public void testActionWithInputNameOnly() throws Exception {
        int result = createStub().addNumbers3(10, 10);
        assertEquals(20, result);
    }

    public void testActionWithOutputNameOnly() throws Exception {
        int result = createStub().addNumbers4(10, 10);
        assertEquals(20, result);
    }

    public void testExplicitActionsBoth() throws Exception {
        int result = createStub().addNumbers5(10, 10);
        assertEquals(20, result);
    }

    public void testExplicitActionsInputOnly() throws Exception {
        int result = createStub().addNumbers6(10, 10);
        assertEquals(20, result);
    }

    public void testExplicitActionsOutputOnly() throws Exception {
        int result = createStub().addNumbers7(10, 10);
        assertEquals(20, result);
    }

    public void testEmptyActions() throws Exception {
        int result = createStub().addNumbers8(10, 10);
        assertEquals(20, result);
    }
}
