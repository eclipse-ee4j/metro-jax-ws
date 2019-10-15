/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.api.databinding;

import java.io.File;

/**
 * WSDLGenerator is used to generate the WSDL representation of the service 
 * endpoint interface of the parent Databinding object.
 */
public interface WSDLGenerator {

	/**
	 * Sets the inlineSchema boolean. When the inlineSchema is true, the 
	 * generated schema documents are embedded within the type element of
	 * the generated WSDL. When the inlineSchema is false, the generated 
	 * schema documents are generated as standalone schema documents and
	 * imported into the generated WSDL.
	 * 
	 * @param inline the inlineSchema boolean.
	 * @return
	 */
	WSDLGenerator inlineSchema(boolean inline);

	/**
	 * Sets A property of the WSDLGenerator
	 * 
	 * @param name The name of the property
	 * @param value The value of the property
	 * 
     * @return this WSDLGenerator instance
	 */
	WSDLGenerator property(String name, Object value);
	
	/**
	 * Generates the WSDL using the wsdlResolver to output the generated
	 * documents.
	 * 
	 * @param wsdlResolver The WSDLResolver
	 */
	void generate(com.oracle.webservices.api.databinding.WSDLResolver wsdlResolver);
	
	/**
	 * Generates the WSDL into the file directory
	 * 
	 * @param outputDir The output file directory
	 * @param name The file name of the main WSDL document
	 */		
	void generate(File outputDir, String name);
}
