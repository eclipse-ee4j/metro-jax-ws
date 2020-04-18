/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.handler_singlepipe.client;

import junit.framework.TestCase;
import javax.xml.namespace.QName;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.HandlerResolver;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.PortInfo;
import jakarta.xml.bind.JAXBContext;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import static fromwsdl.handler_singlepipe.common.TestConstants.*;
import testutil.ClientServerTestUtil;

public abstract class TestCaseBase extends TestCase{

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
        fromwsdl.handler_singlepipe.client.TestCaseBase.NEXT_1_1 = "http://schemas.xmlsoap.org/soap/actor/next";
        fromwsdl.handler_singlepipe.client.TestCaseBase.NEXT_1_2 = "http://www.w3.org/2003/05/soap-envelope/role/next";
        fromwsdl.handler_singlepipe.client.TestCaseBase.NONE = "http://www.w3.org/2003/05/soap-envelope/role/none";
        fromwsdl.handler_singlepipe.client.TestCaseBase.ULTIMATE_RECEIVER =
            "http://www.w3.org/2003/05/soap-envelope/role/ultimateReceiver";
    }

    Hello_Service createService() throws Exception {
        return new Hello_Service();
    }

    // util method when the service isn't needed
    Hello createStub() throws Exception {
        return createStub(createService());
    }

    Hello12 create12Stub() throws Exception {
        return create12Stub(createService());
    }

    ReportService createReportStub() throws Exception {
        return createReportStub(createService());
    }
    Hello createStub(Hello_Service service) throws Exception {
        Hello stub = service.getHelloPort();
        ClientServerTestUtil.setTransport(stub);
        return stub;
    }

    Hello12 create12Stub(Hello_Service service) throws Exception {
        Hello12 stub = service.getHelloPort12();
        ClientServerTestUtil.setTransport(stub);
        return stub;
    }

    private String getEndpointAddress(String defaultAddress) {
        if(ClientServerTestUtil.useLocal())
            return "local://"+new File(System.getProperty("tempdir")).getAbsolutePath().replace('\\','/');
        else
            return defaultAddress;
    }

    ReportService createReportStub(Hello_Service service) throws Exception {
        ReportService stub = service.getReportServicePort();
        ClientServerTestUtil.setTransport(stub);
        ((BindingProvider) stub).getBinding().setHandlerChain(Collections.EMPTY_LIST);
        return stub;
    }
}
