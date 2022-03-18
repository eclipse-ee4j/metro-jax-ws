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

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceFeature;

import org.xml.sax.EntityResolver;

import com.oracle.webservices.api.databinding.Databinding;
import com.oracle.webservices.api.databinding.Databinding.Builder;
import com.oracle.webservices.api.databinding.WSDLGenerator;
import com.oracle.webservices.api.databinding.DatabindingModeFeature;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.databinding.DatabindingConfig;
import com.sun.xml.ws.api.databinding.DatabindingFactory;
import com.sun.xml.ws.api.databinding.MetadataReader;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.spi.db.DatabindingProvider;
import com.sun.xml.ws.util.ServiceFinder;

/**
 * DatabindingFactoryImpl
 * 
 * @author shih-chang.chen@oracle.com
 */
public class DatabindingFactoryImpl extends DatabindingFactory {

	static final String WsRuntimeFactoryDefaultImpl = "com.sun.xml.ws.db.DatabindingProviderImpl";

	protected Map<String, Object> properties = new HashMap<>();
	protected DatabindingProvider defaultRuntimeFactory;
	protected List<DatabindingProvider> providers;

    static private List<DatabindingProvider> providers() {
        List<DatabindingProvider> factories = new java.util.ArrayList<>();
        for (DatabindingProvider p : ServiceFinder.find(DatabindingProvider.class)) {
            factories.add(p);
        }
        return factories;
    }

	public DatabindingFactoryImpl() {
	}

	@Override
    public Map<String, Object> properties() {
		return properties;
	}

	<T> T property(Class<T> propType, String propName) {
		if (propName == null) propName = propType.getName();
		return propType.cast(properties.get(propName));
	}
    
    public DatabindingProvider provider(DatabindingConfig config) {
        String mode = databindingMode(config);
        if (providers == null)
            providers = providers();
        DatabindingProvider provider = null;
        if (providers != null) {
            for (DatabindingProvider p : providers)
                if (p.isFor(mode))
                    provider = p;
        } if (provider == null) {
            provider = new DatabindingProviderImpl();
        }
        return provider;
    }
	
	@Override
    public Databinding createRuntime(DatabindingConfig config) {
	    DatabindingProvider provider = provider(config);
		return provider.create(config);
	}
    
    public WSDLGenerator createWsdlGen(DatabindingConfig config) {
        DatabindingProvider provider = provider(config);
        return provider.wsdlGen(config);
    }

	String databindingMode(DatabindingConfig config) {
		if ( config.getMappingInfo() != null && 
		     config.getMappingInfo().getDatabindingMode() != null)
			return config.getMappingInfo().getDatabindingMode();
        if ( config.getFeatures() != null) for (WebServiceFeature f : config.getFeatures()) {
            if (f instanceof DatabindingModeFeature) {
                DatabindingModeFeature dmf = (DatabindingModeFeature) f;
                config.properties().putAll(dmf.getProperties());
                return dmf.getMode();
            }
        }
		return null;
	}
	
	ClassLoader classLoader() {
		ClassLoader classLoader = property(ClassLoader.class, null);
		if (classLoader == null) classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader;
	}

	Properties loadPropertiesFile(String fileName) {
		ClassLoader classLoader = classLoader();
		Properties p = new Properties();
		try {
			InputStream is = null;
			if (classLoader == null) {
				is = ClassLoader.getSystemResourceAsStream(fileName);
			} else {
				is = classLoader.getResourceAsStream(fileName);
			}
			if (is != null) {
				p.load(is);
			}
		} catch (Exception e) {
			throw new WebServiceException(e);
		}
		return p;
	}

    @Override
    public Builder createBuilder(Class<?> contractClass, Class<?> endpointClass) {
        return new ConfigBuilder(this, contractClass, endpointClass);
    }
    
    static class ConfigBuilder implements Builder {
        DatabindingConfig config;
        DatabindingFactoryImpl factory;
        
        ConfigBuilder(DatabindingFactoryImpl f, Class<?> contractClass, Class<?> implBeanClass) {
            factory = f;
            config = new DatabindingConfig();
            config.setContractClass(contractClass);
            config.setEndpointClass(implBeanClass);
        }
        @Override
        public Builder targetNamespace(String targetNamespace) {
            config.getMappingInfo().setTargetNamespace(targetNamespace);
            return this;
        }
        @Override
        public Builder serviceName(QName serviceName) {
            config.getMappingInfo().setServiceName(serviceName);
            return this;
        }
        @Override
        public Builder portName(QName portName) {
            config.getMappingInfo().setPortName(portName);
            return this;
        }
        @Override
        public Builder wsdlURL(URL wsdlURL) {
            config.setWsdlURL(wsdlURL);
            return this;
        }
        @Override
        public Builder wsdlSource(Source wsdlSource) {
            config.setWsdlSource(wsdlSource);
            return this;
        }
        @Override
        public Builder entityResolver(EntityResolver entityResolver) {
            config.setEntityResolver(entityResolver);
            return this;
        }
        @Override
        public Builder classLoader(ClassLoader classLoader) {
            config.setClassLoader(classLoader);
            return this;
        }
        @Override
        public Builder feature(WebServiceFeature... f) {
            config.setFeatures(f);
            return this;
        }
        @Override
        public Builder property(String name, Object value) {
            config.properties().put(name, value);
            if (isfor(BindingID.class, name, value)) {
                config.getMappingInfo().setBindingID((BindingID)value);
            }
            if (isfor(WSBinding.class, name, value)) {
                config.setWSBinding((WSBinding)value);
            }
            if (isfor(WSDLPort.class, name, value)) {
                config.setWsdlPort((WSDLPort)value);
            }
            if (isfor(MetadataReader.class, name, value)) {
                config.setMetadataReader((MetadataReader)value);
            }
            return this;
        }
        boolean isfor(Class<?> type, String name, Object value) {
            return type.getName().equals(name) && type.isInstance(value);
        }

        @Override
        public com.oracle.webservices.api.databinding.Databinding build() {
            return factory.createRuntime(config);
        }
        @Override
        public com.oracle.webservices.api.databinding.WSDLGenerator createWSDLGenerator() {
            return factory.createWsdlGen(config);
        }       
    }
}
