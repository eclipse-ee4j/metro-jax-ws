/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.epr.client;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Vivek Pandey
 */

public class AllTests extends TestCase{
    public AllTests(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ReferenceParametersTest.class);
        suite.addTestSuite(ClientEpr.class);
        suite.addTestSuite(EprMarshalUnmarshalTester.class);
        suite.addTestSuite(WSEndpointReferenceTest.class);
        return suite;
    }
}
