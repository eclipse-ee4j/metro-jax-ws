/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

import com.sun.xml.ws.api.WSService;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.binding.SOAPBindingImpl;
import com.sun.xml.ws.binding.WebServiceFeatureList;
import com.sun.xml.ws.client.seiportinfo.Hello;
import com.sun.xml.ws.model.SOAPSEIModel;

import junit.framework.TestCase;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceFeature;

import java.net.MalformedURLException;
import java.net.URL;

public class SEIPortInfoTest extends TestCase {

    static final URL WSDL_URL = SEIPortInfoTest.class.getResource("hello_literal.wsdl");

    static final QName SERVICE_NAME = new QName("urn:test", "Hello");
    static final QName PORT_NAME = new QName("urn:test", "HelloPort");
    static final QName EXTRA_HEADER = new QName("urn:test:types", "Extra");

    static final Class PORT_INTERFACE = Hello.class;

    public void testCreateBindingWSFList() throws MalformedURLException {
        SEIPortInfo seiPortInfo = createSEIPortInfo();
        BindingImpl b = seiPortInfo.createBinding(new WebServiceFeatureList(), PORT_INTERFACE);

        boolean understands = ((SOAPBindingImpl) b).understandsHeader(EXTRA_HEADER);
        assertTrue("header " + EXTRA_HEADER + " must be understood", understands);
    }

    public void testCreateBindingWSFArray() throws MalformedURLException {
        SEIPortInfo seiPortInfo = createSEIPortInfo();
        BindingImpl b = seiPortInfo.createBinding(new WebServiceFeature[]{}, PORT_INTERFACE);

        boolean understands = ((SOAPBindingImpl) b).understandsHeader(EXTRA_HEADER);
        assertTrue("header " + EXTRA_HEADER + " must be understood", understands);
    }

    private SEIPortInfo createSEIPortInfo() throws MalformedURLException {
        WSServiceDelegate delegate = (WSServiceDelegate) WSService.create(WSDL_URL, SERVICE_NAME);

        WSDLPort wsdlPort = delegate.getPortModel(delegate.getWsdlService(), PORT_NAME);
        SEIModel model = delegate.buildRuntimeModel(delegate.getServiceName(), PORT_NAME, PORT_INTERFACE, wsdlPort, new WebServiceFeatureList());

        return new SEIPortInfo(delegate, PORT_INTERFACE, (SOAPSEIModel) model, wsdlPort);
    }

}

