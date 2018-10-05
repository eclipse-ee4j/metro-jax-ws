/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.handler_singlepipe.client;

import fromwsdl.handler_singlepipe.common.HandlerTracker;
import static fromwsdl.handler_singlepipe.common.TestConstants.*;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.xml.ws.soap.SOAPFaultException;
import java.util.Arrays;
import java.util.List;

/*
 * These tests are for basic handler cases in many different
 * settings. They test the runtime around the handler mostly,
 * instead of testing the behavior of the handlers themselves.
 *
 * The detailed tests of handler execution are in fromwsdl/handler.
 */

public class HandlerClient extends TestCaseBase {

    /*
     * main() method used during debugging
     */
    public static void main(String [] args) {
        try {
            HandlerClient test = new HandlerClient("HandlerClient");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HandlerClient(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(fromwsdl.handler_singlepipe.client.HandlerClient.class);
        return suite;
    }

    //report.setInstruction(SERVER_PREFIX + 2, HA_THROW_PROTOCOL_EXCEPTION_INBOUND);
    //tracker.setHandlerAction(CLIENT_PREFIX + 1, HA_THROW_PROTOCOL_EXCEPTION_INBOUND);

    public void testSimple1() throws Exception {
        HandlerTracker tracker = HandlerTracker.getClientInstance();
        tracker.clearAll();
        Hello stub = createStub();
        ReportService report = createReportStub();
        report.clearHandlerTracker();
        int foo = 1;
        int bar = stub.hello(foo);
        assertTrue(foo == bar);
        System.out.println("ok");

        List<String> expClientCalled = Arrays.asList(new String[]{"client1", "client1"});
        List<String> gotClientCalled = tracker.getCalledHandlers();
        assertTrue(ERR_CLIENT_CALLED_HANDLERS, checkEqual(expClientCalled, gotClientCalled));

        List<String> expServerCalled = Arrays.asList(new String[]{"server2", "server2"});
        List<String> gotServerCalled = report.getReport(REPORT_CALLED_HANDLERS);
        assertTrue(ERR_SERVER_CALLED_HANDLERS, checkEqual(expServerCalled, gotServerCalled));

        List<String> expClientClosed = Arrays.asList(new String[]{"client1"});
        List<String> gotClientClosed = tracker.getClosedHandlers();
        assertTrue(ERR_CLIENT_CLOSED_HANDLERS, checkEqual(expClientClosed, gotClientClosed));

        List<String> expServerClosed = Arrays.asList(new String[]{"server2"});
        List<String> gotServerClosed = report.getReport(REPORT_CLOSED_HANDLERS);
        assertTrue(ERR_SERVER_CLOSED_HANDLERS, checkEqual(expServerClosed, gotServerClosed));
    }

    public void testSimple2() throws Exception {
        HandlerTracker tracker = HandlerTracker.getClientInstance();
        tracker.clearAll();
        Hello12 stub = create12Stub();
        ReportService report = createReportStub();
        report.clearHandlerTracker();
        int foo = 1;
        int bar = stub.hello12(foo);
        assertTrue(foo == bar);
        System.out.println("ok");

        List<String> expClientCalled = Arrays.asList(new String[]{"client2", "client2"});
        List<String> gotClientCalled = tracker.getCalledHandlers();
        assertTrue(ERR_CLIENT_CALLED_HANDLERS, checkEqual(expClientCalled, gotClientCalled));

        List<String> expServerCalled = Arrays.asList(new String[]{"server1", "server1"});
        List<String> gotServerCalled = report.getReport(REPORT_CALLED_HANDLERS);
        assertTrue(ERR_SERVER_CALLED_HANDLERS, checkEqual(expServerCalled, gotServerCalled));

        List<String> expClientClosed = Arrays.asList(new String[]{"client2"});
        List<String> gotClientClosed = tracker.getClosedHandlers();
        assertTrue(ERR_CLIENT_CLOSED_HANDLERS, checkEqual(expClientClosed, gotClientClosed));

        List<String> expServerClosed = Arrays.asList(new String[]{"server1"});
        List<String> gotServerClosed = report.getReport(REPORT_CLOSED_HANDLERS);
        assertTrue(ERR_SERVER_CLOSED_HANDLERS, checkEqual(expServerClosed, gotServerClosed));

    }

    public void testServerRtException1() throws Exception {
        HandlerTracker tracker = HandlerTracker.getClientInstance();
        tracker.clearAll();
        Hello stub = createStub();
        ReportService report = createReportStub();
        report.clearHandlerTracker();
        try {
            int bar = stub.hello(SERVER_THROW_RUNTIME_EXCEPTION);
            assert(false);
        } catch (SOAPFaultException e) {
            //as expected.
            System.out.println("ok");
        }
        List<String> expClientCalled = Arrays.asList(new String[]{"client1", "client1_FAULT"});
        List<String> gotClientCalled = tracker.getCalledHandlers();
        assertTrue(ERR_CLIENT_CALLED_HANDLERS, checkEqual(expClientCalled, gotClientCalled));

        List<String> expServerCalled = Arrays.asList(new String[]{"server2", "server2_FAULT"});
        List<String> gotServerCalled = report.getReport(REPORT_CALLED_HANDLERS);
        assertTrue(ERR_SERVER_CALLED_HANDLERS, checkEqual(expServerCalled, gotServerCalled));

        List<String> expClientClosed = Arrays.asList(new String[]{"client1"});
        List<String> gotClientClosed = tracker.getClosedHandlers();
        assertTrue(ERR_CLIENT_CLOSED_HANDLERS, checkEqual(expClientClosed, gotClientClosed));

        List<String> expServerClosed = Arrays.asList(new String[]{"server2"});
        List<String> gotServerClosed = report.getReport(REPORT_CLOSED_HANDLERS);
        assertTrue(ERR_SERVER_CLOSED_HANDLERS, checkEqual(expServerClosed, gotServerClosed));

    }

    public void testServerRtException2() throws Exception {
        HandlerTracker tracker = HandlerTracker.getClientInstance();
        tracker.clearAll();
        Hello12 stub = create12Stub();
        ReportService report = createReportStub();
        report.clearHandlerTracker();
        try {
            int bar = stub.hello12(SERVER_THROW_RUNTIME_EXCEPTION);
            assert(false);
        } catch (SOAPFaultException e) {
            //as expected.
            System.out.println("ok");
        }
        List<String> expClientCalled = Arrays.asList(new String[]{"client2", "client2_FAULT"});
        List<String> gotClientCalled = tracker.getCalledHandlers();
        assertTrue(ERR_CLIENT_CALLED_HANDLERS, checkEqual(expClientCalled, gotClientCalled));

        List<String> expServerCalled = Arrays.asList(new String[]{"server1", "server1_FAULT"});
        List<String> gotServerCalled = report.getReport(REPORT_CALLED_HANDLERS);
        assertTrue(ERR_SERVER_CALLED_HANDLERS, checkEqual(expServerCalled, gotServerCalled));

        List<String> expClientClosed = Arrays.asList(new String[]{"client2"});
        List<String> gotClientClosed = tracker.getClosedHandlers();
        assertTrue(ERR_CLIENT_CLOSED_HANDLERS, checkEqual(expClientClosed, gotClientClosed));

        List<String> expServerClosed = Arrays.asList(new String[]{"server1"});
        List<String> gotServerClosed = report.getReport(REPORT_CLOSED_HANDLERS);
        assertTrue(ERR_SERVER_CLOSED_HANDLERS, checkEqual(expServerClosed, gotServerClosed));

    }

    boolean checkEqual(List<String> exp, List<String> got) {
        if ((exp == null) && (got == null))
            return true;
        if (exp.size() != got.size()) {
            printMatchError(exp, got);
            return false;
        }
        for (int i = 0; i < exp.size(); i++) {
            if (!exp.get(i).equals(got.get(i))) {
                printMatchError(exp, got);
                return false;
            }
        }
        return true;
    }

    void printMatchError(List<String> exp, List<String> got) {
        System.out.print("Expected:");
        for (String str : exp) {
            System.out.print(" " + str + " ");
        }
        System.out.println();
        System.out.print("Got     :");
        for (String str : got) {
            System.out.print(" " + str + " ");
        }
        System.out.println();
    }
}
