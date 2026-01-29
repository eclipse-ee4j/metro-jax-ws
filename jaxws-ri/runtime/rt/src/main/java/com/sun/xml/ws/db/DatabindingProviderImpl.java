/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db;

import java.io.File;
import java.util.Map;

import com.oracle.webservices.api.databinding.WSDLGenerator;
import com.oracle.webservices.api.databinding.WSDLResolver;
import com.sun.xml.ws.api.databinding.Databinding;
import com.sun.xml.ws.api.databinding.DatabindingConfig;
import com.sun.xml.ws.api.databinding.WSDLGenInfo;
import com.sun.xml.ws.spi.db.DatabindingProvider;

/**
 * DatabindingProviderImpl is the  default JAXWS implementation of DatabindingProvider
 * 
 * @author shih-chang.chen@oracle.com
 */
public class DatabindingProviderImpl implements DatabindingProvider {
    static final private String CachedDatabinding = "com.sun.xml.ws.db.DatabindingProviderImpl";
	Map<String, Object> properties;

    /**
     * Default constructor.
     */
    public DatabindingProviderImpl() {}

	@Override
    public void init(Map<String, Object> p) {
            properties = p;
	}
	
	DatabindingImpl getCachedDatabindingImpl(DatabindingConfig config) {
	    Object object = config.properties().get(CachedDatabinding);
	    return (object instanceof DatabindingImpl)? (DatabindingImpl)object : null;
	}

	@Override
    public Databinding create(DatabindingConfig config) {
	    DatabindingImpl impl = getCachedDatabindingImpl(config);
	    if (impl == null) {
	        impl = new DatabindingImpl(this, config);
	        config.properties().put(CachedDatabinding, impl);
	    }
		return impl;
	}

    @Override
    public WSDLGenerator wsdlGen(DatabindingConfig config) {
        DatabindingImpl impl = (DatabindingImpl)create(config);
        return new JaxwsWsdlGen(impl);
    }

    @Override
    public boolean isFor(String databindingMode) {
        //This is the default one, so it always return true
        return true;
    }

    static public class JaxwsWsdlGen implements WSDLGenerator {
        DatabindingImpl databinding;
        WSDLGenInfo wsdlGenInfo;
        
        JaxwsWsdlGen(DatabindingImpl impl) {
            databinding = impl;
            wsdlGenInfo = new WSDLGenInfo();
        }
        
        @Override
        public WSDLGenerator inlineSchema(boolean inline) {
            wsdlGenInfo.setInlineSchemas(inline); 
            return this;
        }

        @Override
        public WSDLGenerator property(String name, Object value) {
            // TODO wsdlGenInfo.set...
            return this;
        }

        @Override
        public void generate(WSDLResolver wsdlResolver) {
            wsdlGenInfo.setWsdlResolver(wsdlResolver);
            databinding.generateWSDL(wsdlGenInfo);
        }
        
        @Override
        public void generate(File outputDir, String name) {
            // TODO Auto-generated method stub
            databinding.generateWSDL(wsdlGenInfo);            
        }        
    }
}
