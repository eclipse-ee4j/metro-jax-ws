/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.handler.client;

import junit.framework.*;

public class AllTests extends TestCase {

    public AllTests(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(SimpleHelloTester.class);
        suite.addTestSuite(BindingTester.class);
        suite.addTestSuite(EndToEndTester.class);
        suite.addTestSuite(EndToEndErrorTester.class);
        suite.addTestSuite(HandleFaultTester.class);
        return suite;
    }
}

