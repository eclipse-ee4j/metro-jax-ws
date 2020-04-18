/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.test;

import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceFeature;

import com.oracle.webservices.api.databinding.DatabindingModeFeature;

import com.sun.xml.ws.base.DummyAnnotations;
import com.sun.xml.ws.base.WsDatabindingTestBase;
import com.sun.xml.ws.api.databinding.Databinding;
import com.sun.xml.ws.api.databinding.DatabindingConfig;
import com.sun.xml.ws.spi.db.BindingContext;
import com.sun.xml.ws.spi.db.BindingContextFactory;
import com.sun.xml.ws.test.HelloImpl;
import com.sun.xml.ws.test.HelloPort;
import com.sun.xml.ws.test.xbeandoc.Countries;
import com.sun.xml.ws.test.xbeandoc.CountryInfoType;
import com.sun.xml.ws.test.xbeandoc.TypedXmlBeansDOC;
import com.sun.xml.ws.test.xbeandoc.TypedXmlBeansDOCImpl;

/**
 * 1. @WebService
 * 2. Default BindingID = BindingID.parse(endpointClass)
 * 3. Default from WebServiceFeatureList
 * 4. Default WSDLGeneratorExtension
 * 5. setInlineSchemas(true) -> WSDLGenerator line1025 result = new TXWResult
 * - EntityResolverWrapper InputSource for in-vm wsdl
 * 
 *   @author shih-chang.chen@oracle.com
 */
public abstract class BasicDatabindingTestBase extends WsDatabindingTestBase  {
	abstract protected DatabindingModeFeature databindingMode();
	
	protected String _testHelloEcho() throws Exception {
		Class<?> endpointClass = HelloImpl.class;
		Class<?> proxySEIClass = HelloPort.class;
		DatabindingConfig srvConfig = new DatabindingConfig();
		srvConfig.setEndpointClass(endpointClass);
		srvConfig.setMetadataReader(new DummyAnnotations());
		DatabindingModeFeature dbm = databindingMode();

        DatabindingConfig cliConfig = new DatabindingConfig();
		cliConfig.setMetadataReader(new DummyAnnotations());
		cliConfig.setContractClass(proxySEIClass);
		
		// Honor system property if present, otherwise set feature.
		WebServiceFeature[] f = null;
        String dbProperty = System
                .getProperty(BindingContextFactory.JAXB_CONTEXT_FACTORY_PROPERTY);
        if (dbProperty == null)
            f = new WebServiceFeature[] { dbm };
        else
            f = new WebServiceFeature[0];
        srvConfig.setFeatures(f);
        cliConfig.setFeatures(f);
		
		HelloPort hp = createProxy(HelloPort.class, srvConfig, cliConfig, false);
		String req = "testInVM " + dbm.getMode();
		String res = hp.echoS(req);
		assertEquals(req, res);
        return srvConfig.properties().get(BindingContext.class.getName())
                .getClass().getName();
	}
    
    public void testWebParamHolder() throws Exception {
        DatabindingModeFeature dbm = databindingMode();
        WebServiceFeature[] f = { dbm };
        DatabindingConfig cliConfig = new DatabindingConfig();
        cliConfig.setEndpointClass(WebParamHolderSEB.class);
        cliConfig.setFeatures(f);  
        Databinding db = (Databinding) factory.createRuntime(cliConfig);     
        assertNotNull(db);
    }

    /**
     * Test topdown xbean TypedXmlBeansDOC
     */
    public void testGlobalElementParamXmlBeansTopdown() throws Exception {
        Class endpointClass =  TypedXmlBeansDOCImpl.class;
        Class proxySEIClass =  TypedXmlBeansDOC.class;
        DatabindingConfig srvConfig = new DatabindingConfig();
        srvConfig.setEndpointClass(endpointClass);
        DatabindingModeFeature dbf = databindingMode();
        dbf.getProperties().put("com.sun.xml.ws.api.model.DocWrappeeNamespapceQualified",   true);
        srvConfig.setMetadataReader(new JWSAnnotationReader());
        WebServiceFeature[] f = { dbf };
        srvConfig.setFeatures(f);

        DatabindingConfig cliConfig = new DatabindingConfig();
        cliConfig.setContractClass(proxySEIClass);
        cliConfig.setFeatures(f);
        cliConfig.setMetadataReader(new JWSAnnotationReader());
        TypedXmlBeansDOC proxy = createProxy(TypedXmlBeansDOC.class, srvConfig, cliConfig, true);
        {
            Holder<Countries> countries = new Holder<Countries>(new Countries());
            countries.value.getCountry().add(countryInfo("1", "banana"));
            countries.value.getCountry().add(countryInfo("2", "apple"));
            countries.value.getCountry().add(countryInfo("3", "peach"));
            proxy.addCountry(countries, countryInfo("x", "foo"));
            assertEquals(4, countries.value.getCountry().size());
        }
        {
            Countries countries = new Countries();
            countries.getCountry().add(countryInfo("1", "banana"));
            countries.getCountry().add(countryInfo("2", "apple"));
            String res = proxy.getCountryName(countries, "hello");
            assertEquals("hello2", res);
        }
    }
    
    CountryInfoType countryInfo(String code, String name) {
        CountryInfoType c = new CountryInfoType();
        c.setCode(code);
        c.setName(name);
        return c;
    }
}
