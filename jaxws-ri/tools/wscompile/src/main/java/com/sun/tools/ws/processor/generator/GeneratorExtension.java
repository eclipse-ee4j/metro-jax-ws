/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.generator;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.tools.ws.processor.model.Model;
import com.sun.tools.ws.processor.model.Port;
import com.sun.tools.ws.wscompile.WsimportOptions;
import com.sun.xml.ws.api.SOAPVersion;

/**
 * Service Generator Extension for Custom Binding and Transport
 * 
 * @since 2.2.6
 * @see JwsImplGenerator
 */
public abstract class GeneratorExtension {
    
    /**
     * Derive  Binding ID based on transport and SOAP version 
     * @return BindingID
     */
    public String getBindingValue(String transport, SOAPVersion soapVersion) {
      return null;
    }
    
    /**
     * Create annotations in service JWS generated
     */
    public void writeWebServiceAnnotation(Model model, JCodeModel cm, JDefinedClass cls, Port port) {
    }
    
    /**
     * Allow additional wsimport options 
     * @param name for instance, "-neoption"
     * @return whether the name specifies an option recognized by the extension
     */
    public boolean validateOption(String name) {
      return false;
    }
    
    /**
     * Create annotations in service client generated
     */
    public void writeWebServiceClientAnnotation(WsimportOptions options, JCodeModel cm, JDefinedClass cls) {
    }
}
