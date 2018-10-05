/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * $Id: AllTests.java,v 1.1.2.1 2007/07/27 18:10:28 jk144508 Exp $
 */
package whitebox.wseprsecurity.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * @author Jitendra Kotamraju
 */
public class AllTests extends TestCase {

    public AllTests(String name) {
        super(name);
    }

    public static Test suite() {
        // DO NOT add anymore tests here. WSEPRTester sets up Security Manager
        // We want the runtime classes to be loaded after security mgr is
        // set up.
        // Also run this test in its own JVM (junit fork=true)
        TestSuite suite = new TestSuite();
        suite.addTestSuite(WSEPRTester.class);
        return suite;
    }

}
