/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.jws_webparam1;

import com.sun.xml.ws.db.toplink.JAXBContextFactory;
import jakarta.xml.ws.WebServiceFeature;

import com.oracle.webservices.api.databinding.DatabindingModeFeature;

import com.sun.xml.ws.base.WsDatabindingTestBase;
import com.sun.xml.ws.api.databinding.DatabindingConfig;
import com.sun.xml.ws.binding.WebServiceFeatureList;

public class WebParamTest extends WsDatabindingTestBase {
    boolean debug = false;
    
    public void testWebParam1_toplink() throws Exception {
        testWebParam1(JAXBContextFactory.ECLIPSELINK_JAXB);
    }
    
    //TODO How does jaxb-ri defaultNamespaceRemap work?
    public void TODOtestWebParam1_jaxbri() throws Exception {
        testWebParam1(DatabindingModeFeature.GLASSFISH_JAXB);
    }
    
    void testWebParam1(String dbmode) throws Exception {
        DatabindingConfig srvConfig = new DatabindingConfig();
        srvConfig.setEndpointClass(WebParamWebServiceImpl.class);
        srvConfig.getMappingInfo().setDefaultSchemaNamespaceSuffix("types");
        DatabindingModeFeature dbf = new DatabindingModeFeature(dbmode); 
        WebServiceFeatureList wsfeatures = new WebServiceFeatureList(WebParamWebServiceImpl.class);
        WebServiceFeature[] f = { dbf };
        srvConfig.setFeatures(f);   

        DatabindingConfig cliConfig = new DatabindingConfig();
        cliConfig.setContractClass(WebParamWebService.class);
        cliConfig.setFeatures(f);     
        WebParamWebService port = createProxy(WebParamWebService.class, srvConfig, cliConfig, debug);
        {
            jakarta.xml.ws.Holder<Employee> employeeHolder = new jakarta.xml.ws.Holder<>();
            port.helloString4("jsr181", employeeHolder);
            Employee employee = employeeHolder.value;
            Name output = employee.getName();
            assertEquals(output.getFirstName(), "jsr181");
            assertEquals(output.getLastName(),  "jaxws");
        }
        {
            jakarta.xml.ws.Holder<Employee> employeeHolder = new jakarta.xml.ws.Holder<>();
            Name name = new Name();
            name.setFirstName("jsr181");
            name.setLastName("jsr109");
            port.helloString7("jsr181", name, employeeHolder);
            Employee employee = employeeHolder.value;
            Name output = employee.getName();
            assertEquals(output.getFirstName(), "jsr181");
            assertEquals(output.getLastName(),  "jsr109");
        }
    }
}
