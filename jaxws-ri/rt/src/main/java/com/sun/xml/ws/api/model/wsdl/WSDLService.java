/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model.wsdl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.xml.namespace.QName;

/**
 * Abstracts wsdl:service.
 *
 * @author Vivek Pandey
 */
public interface WSDLService extends WSDLObject, WSDLExtensible {
    /**
     * Gets the {@link WSDLModel} that owns this service.
     */
    @NotNull
    WSDLModel getParent();

    /**
     * Gets the name of the wsdl:service@name attribute value as local name and wsdl:definitions@targetNamespace
     * as the namespace uri.
     */
    @NotNull
    QName getName();

    /**
     * Gets the {@link WSDLPort} for a given port name
     *
     * @param portName non-null operationName
     * @return null if a {@link WSDLPort} is not found
     */
    WSDLPort get(QName portName);

    /**
     * Gets the first {@link WSDLPort} if any, or otherwise null.
     */
    WSDLPort getFirstPort();
    
    /**
    * Gets the first port in this service which matches the portType
    */
    @Nullable
    WSDLPort getMatchingPort(QName portTypeName);

    /**
     * Gives all the {@link WSDLPort} in a wsdl:service {@link WSDLService}
     */
    Iterable<? extends WSDLPort> getPorts();
}
