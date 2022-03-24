/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.sun.xml.ws.jaxbri;

import jakarta.xml.ws.WebServiceFeature;

import com.oracle.webservices.api.databinding.DatabindingModeFeature;

import com.sun.xml.ws.base.DummyAnnotations;
import com.sun.xml.ws.api.databinding.DatabindingConfig;
import com.sun.xml.ws.spi.db.BindingContext;
import com.sun.xml.ws.spi.db.BindingContextFactory;
import com.sun.xml.ws.spi.db.DatabindingException;
import com.sun.xml.ws.test.BasicDatabindingTestBase;
import com.sun.xml.ws.test.HelloImpl;
import com.sun.xml.ws.test.HelloPort;

/**
 * JAXBRIBasicTest
 *
 * @author shih-chang.chen@oracle.com
 */
public class JAXBRIBasicTest extends BasicDatabindingTestBase {

    protected DatabindingModeFeature databindingMode() {
        return new DatabindingModeFeature(DatabindingModeFeature.GLASSFISH_JAXB);
    }

    public void testHelloEcho() throws Exception {
        String wrapperName = _testHelloEcho();
        assertTrue(wrapperName != null && wrapperName.endsWith("JAXBRIContextWrapper"));
    }

    public void testHelloEchoNoMode() throws Exception {
        Class<HelloImpl> endpointClass = HelloImpl.class;
        Class<HelloPort> proxySEIClass = HelloPort.class;
        DatabindingConfig srvConfig = new DatabindingConfig();
        srvConfig.setEndpointClass(endpointClass);
        srvConfig.setMetadataReader(new DummyAnnotations());
        WebServiceFeature[] f = {};
        srvConfig.setFeatures(f);

        DatabindingConfig cliConfig = new DatabindingConfig();
        cliConfig.setMetadataReader(new DummyAnnotations());
        cliConfig.setContractClass(proxySEIClass);
        cliConfig.setFeatures(f);

        HelloPort hp = createProxy(HelloPort.class, srvConfig, cliConfig, false);
        String req = "testInVM " + databindingMode().getMode();
        String res = hp.echoS(req);
        assertEquals(req, res);
        String wrapperName = srvConfig.properties().get(
                BindingContext.class.getName()).getClass().getName();
        assertTrue("Wrapper: " + wrapperName, wrapperName != null && wrapperName.endsWith("JAXBRIContextWrapper"));
    }

    public void testHelloEchoInvalidDB() throws Exception {
        Class<HelloImpl> endpointClass = HelloImpl.class;
        Class<HelloPort> proxySEIClass = HelloPort.class;
        DatabindingConfig srvConfig = new DatabindingConfig();
        srvConfig.setEndpointClass(endpointClass);
        srvConfig.setMetadataReader(new DummyAnnotations());
        WebServiceFeature[] f = {new DatabindingModeFeature("invalid.db")};
        srvConfig.setFeatures(f);

        DatabindingConfig cliConfig = new DatabindingConfig();
        cliConfig.setMetadataReader(new DummyAnnotations());
        cliConfig.setContractClass(proxySEIClass);
        cliConfig.setFeatures(f);

        try {
            HelloPort hp = createProxy(HelloPort.class, srvConfig, cliConfig, false);
            fail("Expected DatabindingException not thrown");
        } catch (DatabindingException e) {
            // expected exception.
        }
    }
}
