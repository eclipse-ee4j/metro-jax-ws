/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.misc.client; 

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class AllTests extends TestCase {

    public AllTests(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(NegativeTest.class);
        suite.addTestSuite(MultiThreadTest.class);
        suite.addTestSuite(LifeCycleTest.class);
        return suite;
    }
}
