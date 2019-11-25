/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.databinding;

import java.util.Map;

/**
 * WsFactory is the entry point of all the ws-databinding APIs. A WsFactory
 * instance can be used to create <code>WsTool</code>, <code>WsRuntime</code>,
 * <code>XsTool</code>, and <code>XsRuntime</code> instances.
 * <p>
 *
 * <blockquote>
 * Following is an example that creates a {@code WsTool} which provides the
 * operations for "WSDL to JAVA" and "JAVA to WSDL":<br>
 * <pre>
 *       WsFactory wsfac = WsFactory.newInstance();
 *       WsTool tool = wsfac.createTool();
 *       GenerationStatus status = tool.generateWsdl(javaToWsdkInfo);
 * </pre>
 * </blockquote>
 * 
 * <blockquote>
 * Following is an example that creates a {@code WsRuntime} which provides the
 * operations to serialize/deserialize a JavaCallInfo to/from a SOAP message:<br>
 * <pre>
 *       WsFactory wsfac = WsFactory.newInstance();
 *       WsRuntime rt = wsfac.createRuntime(wsRuntimeConfig);
 * </pre>
 * </blockquote>
 * 
 * @see com.sun.xml.ws.api.databinding.Databinding
 * 
 * @author shih-chang.chen@oracle.com
 */
public abstract class DatabindingFactory extends com.oracle.webservices.api.databinding.DatabindingFactory {

  /**
   * Creates a new instance of a <code>WsTool</code>. 
   * 
   * @return New instance of a <code>WsTool</code>
   */
//	abstract public WsTool createTool();

  /**
   * Creates a new instance of a <code>WsRuntime</code> which is initialized 
   * with the specified configuration object. 
   * 
   * @param config
   *          the EndpointRuntimeConfig to init this WsRuntime
   * @return New instance of a <code>WsRuntime</code>
   */
  abstract public com.oracle.webservices.api.databinding.Databinding createRuntime(DatabindingConfig config);
	
  /**
   * Creates a new instance of a <code>XsTool</code>. 
   * 
   * @return New instance of a <code>XsTool</code>
   */
//	abstract public XsTool createXsTool(String mode);

  /**
   * Creates a new instance of a <code>XsRuntime</code>. 
   * 
   * @return New instance of a <code>XsRuntime</code>
   */
//	abstract public XsRuntime createXsRuntime(String mode);

  /**
   * Access properties on the <code>WsFactory</code> instance.
   *
   * @return properties of this WsFactory
   */
	abstract public Map<String, Object> properties();

	/**
	 * The default implementation class name.
	 */
	static final String ImplClass = com.sun.xml.ws.db.DatabindingFactoryImpl.class.getName();

  /**
   * Create a new instance of a <code>WsFactory</code>. This static method 
   * creates a new factory instance.
   * 
   * Once an application has obtained a reference to a <code>WsFactory</code> 
   * it can use the factory to configure and obtain <code>WsTool</code> and
   * <code>WsRuntime</code> instances.
   * 
   * @return New instance of a <code>WsFactory</code>
   */
	static public DatabindingFactory newInstance() {
		try {
			Class<?> cls = Class.forName(ImplClass);
			return (DatabindingFactory) cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
