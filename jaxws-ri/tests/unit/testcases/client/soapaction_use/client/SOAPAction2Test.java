/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.soapaction_use.client;

import junit.framework.TestCase;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.Dispatch;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.soap.AddressingFeature;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBElement;


/**
 * @author Rama Pulavarthi
 */
public class SOAPAction2Test extends TestCase {

    private final String action = "\"http://example.com/action/echoSOAPAction\"";
    private final String empty_action = "\"\"";
    private final String dummy_action = "dummy";

    public void testSOAPActionWithWSDL2() {
        TestEndpoint2 port = new TestEndpointService2().getTestEndpointPort2();
        String response = port.echoSOAPAction("foo");
        assertEquals(action, response);

    }
    // when WSA is enabled, use property defaults to true, so action is effective
    public void testSOAPActionWithDispatch_WithoutUse_WithWSA() throws JAXBException {
        TestEndpoint2 port = new TestEndpointService2().getTestEndpointPort2();
        String address = (String) ((BindingProvider) port).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        Service s = Service.create(new QName("http://client.soapaction_use.server/", "TestEndpointService2"));
        s.addPort(new QName("http://client.soapaction_use.server/", "TestEndpointPort2"), SOAPBinding.SOAP11HTTP_BINDING, address);
        Dispatch<Object> d = s.createDispatch(new QName("http://client.soapaction_use.server/", "TestEndpointPort2"), JAXBContext.newInstance(ObjectFactory.class), Service.Mode.PAYLOAD, new AddressingFeature());
        ((BindingProvider) d).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, action);
        JAXBElement<String> r = (JAXBElement<String>) d.invoke(new ObjectFactory().createEchoSOAPAction("dummy"));
        assertEquals(action, r.getValue());

    }
    // when use is true, so action is effective
    public void testSOAPActionWithDispatch_WithUse_true_WithWSA() throws JAXBException {
        TestEndpoint2 port = new TestEndpointService2().getTestEndpointPort2();
        String address = (String) ((BindingProvider) port).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        Service s = Service.create(new QName("http://client.soapaction_use.server/", "TestEndpointService2"));
        s.addPort(new QName("http://client.soapaction_use.server/", "TestEndpointPort2"), SOAPBinding.SOAP11HTTP_BINDING, address);
        Dispatch<Object> d = s.createDispatch(new QName("http://client.soapaction_use.server/", "TestEndpointPort2"), JAXBContext.newInstance(ObjectFactory.class), Service.Mode.PAYLOAD, new AddressingFeature());
        ((BindingProvider) d).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, action);
        ((BindingProvider) d).getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, true);
        JAXBElement<String> r = (JAXBElement<String>) d.invoke(new ObjectFactory().createEchoSOAPAction("dummy"));
        assertEquals(action, r.getValue());

    }

    // use is false but WSA is enabled, so action is ineffective
    public void testSOAPActionWithDispatch_WithUse_false_WithWSA() throws JAXBException {
        TestEndpoint2 port = new TestEndpointService2().getTestEndpointPort2();
        String address = (String) ((BindingProvider) port).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        Service s = Service.create(new QName("http://client.soapaction_use.server/", "TestEndpointService2"));
        s.addPort(new QName("http://client.soapaction_use.server/", "TestEndpointPort2"), SOAPBinding.SOAP11HTTP_BINDING, address);
        Dispatch<Object> d = new TestEndpointService2().createDispatch(new QName("http://client.soapaction_use.server/", "TestEndpointPort2"), JAXBContext.newInstance(ObjectFactory.class), Service.Mode.PAYLOAD, new AddressingFeature());
        ((BindingProvider) d).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, dummy_action);
        ((BindingProvider) d).getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, false);
        JAXBElement<String> r = (JAXBElement<String>) d.invoke(new ObjectFactory().createEchoSOAPAction("dummy"));
        assertEquals(action, r.getValue());

    }


}
