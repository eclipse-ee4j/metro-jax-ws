/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.delimiter.client;

import junit.framework.TestCase;

/**
 * @author Arun Gupta
 */
public class AddNumbersClient extends TestCase {
    public AddNumbersClient(String name) {
        super(name);
    }

    private AddNumbersPortType createStub() throws Exception {
        return new AddNumbersService().getAddNumbersPort();
    }

    public void testDefaultActions() throws Exception {
        int result = createStub().addNumbers(10, 10);
        assertEquals(20, result);
    }

    public void testDefaultAddFaultAction() throws Exception {
        try {
            createStub().addNumbers(-10, 10);
            fail("AddNumbersFault_Exception must be thrown");
        } catch (AddNumbersFault_Exception ex)  {
            assertTrue(true);
        }
    }

    public void testDefaultTooBigFaultAction() throws Exception {
        try {
            createStub().addNumbers(20, 20);
            fail("TooBigNumbersFault_Exception must be thrown");
        } catch (TooBigNumbersFault_Exception ex)  {
            assertTrue(true);
        }
    }

    public void testExplicitActions() throws Exception {
        int result = createStub().addNumbers2(10, 10);
        assertEquals(20, result);
    }

    public void testExplicitAddFaultAction() throws Exception {
        try {
            createStub().addNumbers2(-10, 10);
            fail("AddNumbersFault_Exception must be thrown");
        } catch (AddNumbersFault_Exception ex)  {
            assertTrue(true);
        }
    }

    public void testExplicitTooBigFaultAction() throws Exception {
        try {
            createStub().addNumbers2(20, 20);
            fail("TooBigNumbersFault_Exception must be thrown");
        } catch (TooBigNumbersFault_Exception ex)  {
            assertTrue(true);
        }
    }
}
