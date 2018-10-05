/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.soapaction.client;

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

    public void testDefaultActionSOAPAction() throws Exception {
        int result = createStub().addNumbers(10, 10);
        assertTrue(result == 20);
    }

    public void testExplicitActionSOAPAction() throws Exception {
        int result = createStub().addNumbers2(10, 10);
        assertTrue(result == 20);
    }

    public void testDefaultActionEmptySOAPAction() throws Exception {
        int result = createStub().addNumbers3(10, 10);
        assertTrue(result == 20);
    }

    public void testDefaultActionNoSOAPAction() throws Exception {
        int result = createStub().addNumbers4(10, 10);
        assertTrue(result == 20);
    }
}
