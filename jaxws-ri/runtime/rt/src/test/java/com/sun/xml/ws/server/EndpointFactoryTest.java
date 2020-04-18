/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import junit.framework.TestCase;

import jakarta.jws.WebService;

public class EndpointFactoryTest extends TestCase {

    public void testVerifyImplementorClass1() {
        try {
            boolean condition = EndpointFactory.verifyImplementorClass(MyImpl1.class);
            // returns true if Provider or AsyncProvider
            assertFalse(condition);
        } catch (Throwable t) {
            fail("Unexpected exception cought");
            t.printStackTrace();
        }
    }
    public void testVerifyImplementorClass2() {
        try {
            EndpointFactory.verifyImplementorClass(MyImpl2.class);
            fail("The given class isn't correct Web Service; verification should fail");
        } catch (IllegalArgumentException ignored) {
            assertTrue(true);
        } catch (Throwable t) {
            fail("Unexpected exception cought");
            t.printStackTrace();
        }
    }

}

@WebService
class MyImpl1 { }

class MyImpl2 { }
