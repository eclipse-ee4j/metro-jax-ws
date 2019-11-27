/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.eclipselink;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.WebServiceFeature;

import com.oracle.webservices.api.databinding.DatabindingModeFeature;

import com.sun.xml.ws.api.databinding.DatabindingConfig;
import com.sun.xml.ws.spi.db.BindingContextFactory;
import com.sun.xml.ws.test.BasicDatabindingTestBase;
import com.sun.xml.ws.test.CollectionMap;
import com.sun.xml.ws.test.CollectionMapImpl;

/**
 * EclipselinkJAXBBasicTest
 * 
 * @author shih-chang.chen@oracle.com
 */
public class EclipselinkJAXBBasicTest extends BasicDatabindingTestBase  {
    static public final String ECLIPSELINK_JAXB = "eclipselink.jaxb";
	
	protected DatabindingModeFeature databindingMode() {
		return new DatabindingModeFeature(ECLIPSELINK_JAXB); 
	}
	
	public void testHelloEcho() throws Exception {
	    String wrapperName = _testHelloEcho();
        assertTrue(wrapperName != null && wrapperName.endsWith("JAXBContextWrapper"));
	}
	
	public void testHelloEchoWithProperty() throws Exception {
	    String propName = BindingContextFactory.JAXB_CONTEXT_FACTORY_PROPERTY;
	    String oldProp = System.getProperty(propName);
	    try {
	        System.setProperty(propName, ECLIPSELINK_JAXB);
	        String wrapperName = _testHelloEcho();
	        assertTrue(wrapperName != null && wrapperName.endsWith("JAXBContextWrapper"));
	    } finally {
	        if (oldProp != null)
	            System.setProperty(propName, oldProp);
	        else
	            System.clearProperty(propName);
	    }
    }

	public void testCollectionMap() throws Exception {
		Class<?> endpointClass = CollectionMapImpl.class;
		Class<?> proxySEIClass = CollectionMap.class;
		DatabindingConfig srvConfig = new DatabindingConfig();
		srvConfig.setEndpointClass(endpointClass);
		DatabindingModeFeature dbm = databindingMode();
		WebServiceFeature[] f = { dbm };
		srvConfig.setFeatures(f);	

        DatabindingConfig cliConfig = new DatabindingConfig();
		cliConfig.setContractClass(proxySEIClass);
		cliConfig.setFeatures(f);	
		
		CollectionMap p = createProxy(CollectionMap.class, srvConfig, cliConfig, false);
		{
    	    List<String> req = Arrays.asList("x", "Eclipselink", "parameterized", "List");
    	    List<String> res = p.echoListOfString(req);
    	    assertEqualList(req, res);
		}
		{
    	    Integer[] num = {123, -456, 789, 0};
    	    Map<String, Integer> req = new HashMap<String, Integer>();
    	    for (Integer i : num) req.put(i.toString(), i);
            Map<Integer, String> res = p.echoMapOfString(req);
            Map<Integer, String> ans = new HashMap<Integer, String>();
            for (Integer i : num) ans.put(i, i.toString());
            assertTrue(equalsMap(ans, res));
		}
	}
}

