/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.handler_processing.client;

import java.util.List;

import handler.handler_processing.common.HandlerTracker;
import handler.handler_processing.common.TestConstants;

import junit.framework.*;

/*
 * Simple class used for working on new runtime. Does not test
 * handler functionality
 *
 * @author Rama Pulavarthi
 */
public class SimpleHelloTest extends TestCaseBase {

    /*
     * main method for debugging
     */
    public static void main(String [] args) {
        try {
            //System.setProperty("uselocal", "true");
            SimpleHelloTest tester =
                new SimpleHelloTest("SimpleHelloTest");
            tester.testHello();
            tester.testHelloOneWay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SimpleHelloTest(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(SimpleHelloTest.class);
        return suite;
    }

    /*
     * Simple end to end test (mostly for debug work)
     */
    public void testHello() throws Exception {
        HandlerTracker tracker = HandlerTracker.getClientInstance();
        tracker.clearAll();

        TestService_Service service = getService();
        TestService test = getTestStub(service);
        ReportService report = getReportStub(service);
        
        report.clearHandlerTracker();

        int foo = -1;
        int bar = test.testInt(foo);
        assertTrue(foo == bar);
        System.out.println("ok");
        
        List<String> closedHandlers =
            report.getReport(TestConstants.REPORT_CLOSED_HANDLERS);
        
        List<String> handlers =
            report.getReport(TestConstants.REPORT_CALLED_HANDLERS);
        assertNotNull("received null list back from server", handlers);
    }

    /*
     * Simple end to end test (mostly for debug work)
     */
    public void testHelloOneWay() throws Exception {
        HandlerTracker tracker = HandlerTracker.getClientInstance();
        tracker.clearAll();

        TestService_Service service = getService();
        TestService test = getTestStub(service);
        ReportService report = getReportStub(service);
        
        report.clearHandlerTracker();

        test.testIntOneWay(0);
        
        // make normal call after
        assertEquals("did not get expected response",
            4, test.testInt(4));
        
        System.out.println("ok");
    }

}
