/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model.wsdl;

import com.sun.xml.ws.api.model.ParameterBinding;

/**
 * Abstracts wsdl:part after applying binding information from wsdl:binding.
 *
 * @author Vivek Pandey
 */
public interface WSDLPart extends WSDLObject {
    /**
     * Gets wsdl:part@name attribute value.
     */
    String getName();

    /**
     * Gets the wsdl:part binding as seen thru wsdl:binding
     */
    ParameterBinding getBinding();

    /**
     * Index value is as the order in which the wsdl:part appears inside the input or output wsdl:message.
     * @return n where n {@literal >=} 0
     */
    int getIndex();

    /**
     * Gives the XML Schema descriptor referenced using either wsdl:part@element or wsdl:part@type.
     */
    public WSDLPartDescriptor getDescriptor();
}
