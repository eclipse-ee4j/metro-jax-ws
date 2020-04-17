/*
 * Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.handler_processing.client;

import handler.handler_processing.common.HandlerTracker;
import handler.handler_processing.common.TestConstants;
import junit.framework.TestCase;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.HandlerResolver;
import jakarta.xml.ws.handler.PortInfo;
import jakarta.xml.ws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

public abstract class TestCaseBase extends TestCase implements TestConstants {

    // check that the lists have the right number of handlers
    static final int SERVICE_HANDLERS = 2;
    static final int TEST_PORT_HANDLERS = 2;
    static final int REPORT_PORT_HANDLERS = 2;
    static final int PROTOCOL_HANDLERS = 2;
    static final int SERVER_SERVICE_HANDLERS = 2;
    static final int SERVER_TEST_PORT_HANDLERS = 2;
    static final int SERVER_REPORT_PORT_HANDLERS = 1;

    // test *may* want to change these
    int numTestHandlers;
    int numTotalHandlers;
    int numTestServerHandlers;
    int numTotalServerHandlers;

    // Dispatch creation use
    static final QName serviceQName = new QName("urn:test", "TestService");
    static final QName testPortQName = new QName("urn:test", "TestServicePort");
    static final QName reportPortQName =
            new QName("urn:test", "ReportServicePort");

    static final String bindingIdString = SOAPBinding.SOAP11HTTP_BINDING;

    static String NEXT_1_1;
    static String NEXT_1_2;
    static String NONE;
    static String ULTIMATE_RECEIVER;

    public TestCaseBase(String name) {
        super(name);

        numTestHandlers = SERVICE_HANDLERS + TEST_PORT_HANDLERS +
                PROTOCOL_HANDLERS;
        numTotalHandlers = numTestHandlers + REPORT_PORT_HANDLERS;
        numTestServerHandlers = SERVER_SERVICE_HANDLERS +
                SERVER_TEST_PORT_HANDLERS;
        numTotalServerHandlers = numTestServerHandlers +
                SERVER_REPORT_PORT_HANDLERS;
        NEXT_1_1 = "http://schemas.xmlsoap.org/soap/actor/next";
        NEXT_1_2 = "http://www.w3.org/2003/05/soap-envelope/role/next";
        NONE = "http://www.w3.org/2003/05/soap-envelope/role/none";
        ULTIMATE_RECEIVER =
                "http://www.w3.org/2003/05/soap-envelope/role/ultimateReceiver";
    }

    @Override
    protected void setUp() throws Exception {

        // clear server tracker
        getReportStub(getService()).clearHandlerTracker();

        // clear client tracker
        HandlerTracker.getClientInstance().clearAll();
        HandlerTracker.getServerInstance().clearAll();

        if (HandlerTracker.VERBOSE_HANDLERS) {
            System.out.println("---------------------------------------------------------------------------------------------");
            System.out.println("TestCaseBase.setUp: " + getName() + ", " + getClass().getName());
            System.out.println("---------------------------------------------------------------------------------------------");
            HandlerTracker.getClientInstance().info("Client");
            HandlerTracker.getServerInstance().info("Server");
            System.out.println("---------------------------------------------------------------------------------------------");
        }
    }

    TestService_Service getService() {
        TestService_Service service = new TestService_Service();
        return service;
    }

    TestService getTestStub(TestService_Service service) throws Exception {
        TestService stub = service.getTestServicePort();
        return stub;
    }

    ReportService getReportStub(TestService_Service service) throws Exception {
        ReportService stub = service.getReportServicePort();
        return stub;
    }

/*
    // create service with just qname -- no handlers in that case
    Dispatch<Object> getDispatchJAXB(QName name) throws Exception {
        TestService stub1 = service.getTestServicePort();
        String address = (String)((BindingProvider)stub1).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);

        QName serviceQName = new QName("urn:test", "Hello");
        Service service = Service.create(serviceQName);
        service.addPort(name, bindingIdString, address);
        JAXBContext jaxbContext =
            JAXBContext.newInstance(ObjectFactory.class);
        Dispatch<Object> dispatch = service.createDispatch(name,
            jaxbContext, Service.Mode.PAYLOAD);
        ClientServerTestUtil.setTransport(dispatch, null);
        return dispatch;
    }
*/

    void clearHandlersInService(Service service) {
        service.setHandlerResolver(new HandlerResolver() {
            public List<Handler> getHandlerChain(PortInfo pi) {
                return new ArrayList<Handler>();
            }
        });
    }

}


