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

import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.URI;

import jakarta.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.HandlerResolver;
import jakarta.xml.ws.handler.PortInfo;
import jakarta.xml.ws.soap.SOAPBinding;
import static jakarta.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING;

import junit.framework.*;

import fromwsdl.handler.common.TestConstants;

import testutil.ClientServerTestUtil;

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
    static final String endpointAddress;

    static final String bindingIdString = SOAPBinding.SOAP11HTTP_BINDING;
    
    static String NEXT_1_1;
    static String NEXT_1_2;
    static String NONE;
    static String ULTIMATE_RECEIVER;
    
    static {
        // we'll fix the test harness correctly later,
        // so that test code won't have to hard code any endpoint address nor transport,
        // but for now let's just support local and HTTP to make unit tests happier.
        // this is not a good code, but it's just a bandaid solutino that works for now.
        if(ClientServerTestUtil.useLocal())
            endpointAddress = "local://"+System.getProperty("tempdir");
        else
            endpointAddress = "http://localhost:8080/jaxrpc-fromwsdl_handler/test";
    }

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

    TestService_Service getService() {
        TestService_Service service = new TestService_Service();
        return service;
    }

    TestService getTestStub(TestService_Service service) throws Exception {
        TestService stub = service.getTestServicePort();
        ClientServerTestUtil.setTransport(stub);
        return stub;
    }

    ReportService getReportStub(TestService_Service service) throws Exception {
        ReportService stub = service.getReportServicePort();
        ClientServerTestUtil.setTransport(stub);
        return stub;
    }

    // create service with just qname -- no handlers in that case
    Dispatch<Object> getDispatchJAXB(QName name) throws Exception {
        QName serviceQName = new QName("urn:test", "Hello");
        Service service = Service.create(serviceQName);
        service.addPort(name, bindingIdString, endpointAddress);
        JAXBContext jaxbContext =
            JAXBContext.newInstance(ObjectFactory.class);
        Dispatch<Object> dispatch = service.createDispatch(name,
            jaxbContext, Service.Mode.PAYLOAD);
        ClientServerTestUtil.setTransport(dispatch, null);
        return dispatch;
    }
    
    void clearHandlersInService(Service service) {
        service.setHandlerResolver(new HandlerResolver(){
            public List<Handler> getHandlerChain(PortInfo pi) {
                return new ArrayList<Handler>();
            }
        });
    }
    
}


