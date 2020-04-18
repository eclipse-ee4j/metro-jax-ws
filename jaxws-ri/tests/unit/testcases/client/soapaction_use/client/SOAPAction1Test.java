/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.soapaction_use.client;

import junit.framework.TestCase;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.JAXBElement;


/**
 * @author Rama Pulavarthi
 */
public class SOAPAction1Test extends TestCase {

    private final String action = "\"http://example.com/action/echoSOAPAction\"";
    private final String empty_action = "\"\"";
    private final String dummy_action = "dummy";

    public void testSOAPActionWithWSDL1() {
        TestEndpoint1 port = new TestEndpointService1().getTestEndpointPort1();
        String response = port.echoSOAPAction("foo");
        assertEquals(action, response);

    }
    // since use property is default (false), action property is ineffective
    public void testSOAPActionWithDispatch_WithoutUse() throws JAXBException {
        TestEndpoint1 port = new TestEndpointService1().getTestEndpointPort1();
        String address = (String) ((BindingProvider) port).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        Service s = Service.create(new QName("http://client.soapaction_use.server/", "TestEndpointService1"));
        s.addPort(new QName("http://client.soapaction_use.server/", "TestEndpointPort1"), SOAPBinding.SOAP11HTTP_BINDING, address);
        Dispatch<Object> d = s.createDispatch(new QName("http://client.soapaction_use.server/", "TestEndpointPort1"), JAXBContext.newInstance(ObjectFactory.class), Service.Mode.PAYLOAD);
        ((BindingProvider) d).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, action);
        JAXBElement<String> r = (JAXBElement<String>) d.invoke(new ObjectFactory().createEchoSOAPAction("dummy"));
        assertEquals(empty_action, r.getValue());

    }
    // use is set to true, so action property is effective
    public void testSOAPActionWithDispatch_WithUse_true() throws JAXBException {
        TestEndpoint1 port = new TestEndpointService1().getTestEndpointPort1();
        String address = (String) ((BindingProvider) port).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        Service s = Service.create(new QName("http://client.soapaction_use.server/", "TestEndpointService1"));
        s.addPort(new QName("http://client.soapaction_use.server/", "TestEndpointPort1"), SOAPBinding.SOAP11HTTP_BINDING, address);
        Dispatch<Object> d = s.createDispatch(new QName("http://client.soapaction_use.server/", "TestEndpointPort1"), JAXBContext.newInstance(ObjectFactory.class), Service.Mode.PAYLOAD);
        ((BindingProvider) d).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, action);
        ((BindingProvider) d).getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, true);
        JAXBElement<String> r = (JAXBElement<String>) d.invoke(new ObjectFactory().createEchoSOAPAction("dummy"));
        assertEquals(action, r.getValue());

    }
    // since use property is false, action property is ineffective
    public void testSOAPActionWithDispatch_WithUse_false() throws JAXBException {
        TestEndpoint1 port = new TestEndpointService1().getTestEndpointPort1();
        String address = (String) ((BindingProvider) port).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        Service s = Service.create(new QName("http://client.soapaction_use.server/", "TestEndpointService1"));
        s.addPort(new QName("http://client.soapaction_use.server/", "TestEndpointPort1"), SOAPBinding.SOAP11HTTP_BINDING, address);
        Dispatch<Object> d = s.createDispatch(new QName("http://client.soapaction_use.server/", "TestEndpointPort1"), JAXBContext.newInstance(ObjectFactory.class), Service.Mode.PAYLOAD);
        ((BindingProvider) d).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "http://example/action");
        ((BindingProvider) d).getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, false);
        JAXBElement<String> r = (JAXBElement<String>) d.invoke(new ObjectFactory().createEchoSOAPAction("dummy"));
        assertEquals(empty_action, r.getValue());
    }

}
