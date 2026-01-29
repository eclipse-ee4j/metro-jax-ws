/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.databinding;

import com.oracle.webservices.api.databinding.WSDLResolver;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;

/**
 * WSDLGenInfo provides the WSDL generation options
 *
 * @author shih-chang.chen@oracle.com
 */
public class WSDLGenInfo {
	WSDLResolver wsdlResolver;
	Container container;
    boolean inlineSchemas;
    boolean secureXmlProcessingDisabled;
    WSDLGeneratorExtension[] extensions;

	/**
	 * Default constructor.
	 */
	public WSDLGenInfo() {}

	public WSDLResolver getWsdlResolver() {
		return wsdlResolver;
	}
	public void setWsdlResolver(WSDLResolver wsdlResolver) {
		this.wsdlResolver = wsdlResolver;
	}
	public Container getContainer() {
		return container;
	}
	public void setContainer(Container container) {
		this.container = container;
	}
	public boolean isInlineSchemas() {
		return inlineSchemas;
	}
	public void setInlineSchemas(boolean inlineSchemas) {
		this.inlineSchemas = inlineSchemas;
	}
	public WSDLGeneratorExtension[] getExtensions() {
	    if (extensions == null) return new WSDLGeneratorExtension[0];
		return extensions;
	}
	public void setExtensions(WSDLGeneratorExtension[] extensions) {
		this.extensions = extensions;
	}

    public void setSecureXmlProcessingDisabled(boolean secureXmlProcessingDisabled) {
        this.secureXmlProcessingDisabled = secureXmlProcessingDisabled;
    }

    public boolean isSecureXmlProcessingDisabled() {
        return secureXmlProcessingDisabled;
    }
}
