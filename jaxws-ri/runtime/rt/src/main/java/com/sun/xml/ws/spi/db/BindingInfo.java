/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.spi.db;

import java.net.URL;
import java.util.Collection;
import java.util.Map;

import com.sun.xml.ws.api.model.SEIModel;

/**
 * BindingInfo
 *
 * @author shih-chang.chen@oracle.com
 */
public class BindingInfo {
	
	private String databindingMode;
	private String defaultNamespace;
	
	private Collection<Class> contentClasses = new java.util.ArrayList<>();
    private Collection<TypeInfo> typeInfos = new java.util.ArrayList<>();
    private Map<Class,Class> subclassReplacements = new java.util.HashMap<>();
    private Map<String, Object> properties = new java.util.HashMap<>();
	protected ClassLoader classLoader;
    
    private SEIModel seiModel;
    private URL wsdlURL;

    public String getDatabindingMode() {
		return databindingMode;
	}
	public void setDatabindingMode(String databindingMode) {
		this.databindingMode = databindingMode;
	}
    
	public String getDefaultNamespace() {
		return defaultNamespace;
	}
	public void setDefaultNamespace(String defaultNamespace) {
		this.defaultNamespace = defaultNamespace;
	}

	public Collection<Class> contentClasses() {
		return contentClasses;
	}	
	public Collection<TypeInfo> typeInfos() {
		return typeInfos;
	}	
	public Map<Class, Class> subclassReplacements() {
		return subclassReplacements;
	}	
	public Map<String, Object> properties() {
		return properties;
	}
	
	public SEIModel getSEIModel() {
		return seiModel;
	}
	public void setSEIModel(SEIModel seiModel) {
		this.seiModel = seiModel;
	}
	public ClassLoader getClassLoader() {
		return classLoader;
	}
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
    public URL getWsdlURL() {
        return wsdlURL;
    }
    public void setWsdlURL(URL wsdlURL) {
        this.wsdlURL = wsdlURL;
    }
}
