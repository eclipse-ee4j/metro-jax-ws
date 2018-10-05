/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.handlers.client;

import java.io.File;
import java.net.URL;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import junit.framework.TestCase;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import com.sun.xml.ws.handler.PortInfoImpl;
import com.sun.xml.ws.api.BindingID;

public class HandlerChainTester extends TestCase{
    TestService_Service service;
    private final static URL WSDL_LOCATION;
    private final static QName TESTSERVICE = new QName("urn:test", "TestService");
    private final static QName TESTSERVICEPORT = new QName("urn:test", "TestServicePort");
    private final static QName REPORTSERVICEPORT = new QName("urn:test", "ReportServicePort");
    private final QName UNKNOWNPORT = new QName("urn:test", "UnknownPort");
    String endpointAddress = "http://fakeaddress.com/boo";
    static {
        URL url = null;
        try {
            File f = new File("src/whitebox/handlers/config/service.wsdl");
            url = f.toURL();
        } catch(Exception e){
            e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }
    public HandlerChainTester(String s) {
        super(s);
    }

    public TestService_Service getService() {
        if(service == null)
            service = new TestService_Service();
        return service;
    }

    public void testHandlersOnTestPort(){
        TestService_Service service = getService();
        TestService testStub = service.getTestServicePort();
        Binding testBinding = ((BindingProvider) testStub).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(3,chain.size());
    }

    public void testHandlersOnTestPort1(){
        Service service = Service.create(WSDL_LOCATION,TESTSERVICE);
        TestService testStub = service.getPort(TestService.class);
        Binding testBinding = ((BindingProvider) testStub).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(0,chain.size());
    }

    public void testHandlersOnTestPort2(){
        TestService_Service service = getService();
        TestService testStub = service.getPort(TestService.class);
        Binding testBinding = ((BindingProvider) testStub).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(3,chain.size());
    }

    public void testHandlersOnTestPort3(){
        Service service = Service.create(WSDL_LOCATION, TESTSERVICE);
        TestService testStub = service.getPort(TESTSERVICEPORT, TestService.class);
        Binding testBinding = ((BindingProvider) testStub).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(0,chain.size());
    }

    public void testHandlersOnReportPort(){
        TestService_Service service = getService();
        ReportService testStub = service.getReportServicePort();
        Binding testBinding = ((BindingProvider) testStub).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(2,chain.size());
    }

    public void testHandlersOnReportPort1(){
        Service service = Service.create(WSDL_LOCATION, TESTSERVICE);
        ReportService testStub = service.getPort(ReportService.class);
        Binding testBinding = ((BindingProvider) testStub).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(0,chain.size());
    }

    public void testHandlersOnReportPort2(){
        TestService_Service service = getService();
        ReportService testStub = service.getPort(ReportService.class);
        Binding testBinding = ((BindingProvider) testStub).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(2,chain.size());
    }

    public void testHandlersOnReportPort3(){
        Service service = Service.create(WSDL_LOCATION, TESTSERVICE);
        ReportService testStub = service.getPort(REPORTSERVICEPORT, ReportService.class);
        Binding testBinding = ((BindingProvider) testStub).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(0,chain.size());
    }
    public void testHandlersOnTestPortDispatch(){
        TestService_Service service = getService();
        Dispatch<Source> dispatch = service.createDispatch(TESTSERVICEPORT, Source.class,Service.Mode.PAYLOAD);
        Binding testBinding = ((BindingProvider) dispatch).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(3,chain.size());
    }

    public void testHandlersOnTestPortDispatch1(){
        Service service = Service.create(TESTSERVICE);
        String bindingId = SOAPBinding.SOAP11HTTP_BINDING;
        service.addPort(TESTSERVICEPORT, bindingId, endpointAddress);
        Dispatch<Source> dispatch = service.createDispatch(TESTSERVICEPORT, Source.class,Service.Mode.PAYLOAD);
        Binding testBinding = ((BindingProvider) dispatch).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(0,chain.size());
    }
    public void testHandlersOnReportPortDispatch(){
        TestService_Service service = getService();
        Dispatch<Source> dispatch = service.createDispatch(REPORTSERVICEPORT, Source.class,Service.Mode.PAYLOAD);
        Binding testBinding = ((BindingProvider) dispatch).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(2,chain.size());
    }
    /*
    Commenting this test as this no longer is useful.
    In 2.1, When we try to add a invalid Port to a service with wsdl, now we throw WebServiceException,
    during addPort() itself saying Port name is not a valid port in the wsdl.

    public void testHandlersOnUnknownPortDispatch(){
        TestService_Service service = getService();
        String bindingId = SOAPBinding.SOAP11HTTP_BINDING;
        service.addPort(UNKNOWNPORT, bindingId, endpointAddress);
        Dispatch<Source> dispatch = service.createDispatch(UNKNOWNPORT, Source.class,Service.Mode.PAYLOAD);
        Binding testBinding = ((BindingProvider) dispatch).getBinding();
        List<Handler> chain = testBinding.getHandlerChain();
        //System.out.println(chain.size());
        assertEquals(2,chain.size());
    }
    */
    public void testPortInfoImpl() {
        PortInfo portInfo = new PortInfoImpl(BindingID.SOAP11_HTTP, new QName("http://example.com/", "EchoPort"),
new QName("http://example.com/", "EchoService"));
        assertTrue(portInfo.equals(portInfo));
    }
}
