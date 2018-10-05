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
 * $Id: AllTests.java,v 1.5.2.18 2007/07/09 20:37:29 jk144508 Exp $
 */
package whitebox.endpoint.client;

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
        TestSuite suite = new TestSuite();
        suite.addTestSuite(EndpointAPITester.class);
        suite.addTestSuite(EndpointMetadataTester.class);
        suite.addTestSuite(ProviderTester.class);
        suite.addTestSuite(EndpointExecutorTester.class);
        suite.addTestSuite(HttpContextTester.class);
        suite.addTestSuite(EndpointWsdlLocationTester.class);
        suite.addTestSuite(EndpointEPRTester.class);
        suite.addTestSuite(MessageContextTester.class);
        suite.addTestSuite(R2001Tester.class);
        suite.addTestSuite(UnicodeServiceNameTester.class);
        suite.addTestSuite(SourceTester.class);
        suite.addTestSuite(HttpStatusCodes.class);
        suite.addTestSuite(OnewayTester.class);
        suite.addTestSuite(DocBareTester.class);
        suite.addTestSuite(EnumTester.class);
        suite.addTestSuite(WhitespaceTester.class);
        suite.addTestSuite(SchemaRedefineTester.class);
        return suite;
    }
}
