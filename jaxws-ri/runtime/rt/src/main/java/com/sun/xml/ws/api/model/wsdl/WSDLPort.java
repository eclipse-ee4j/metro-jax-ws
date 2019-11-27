/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
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
import com.sun.xml.ws.api.EndpointAddress;
import com.sun.xml.ws.api.addressing.WSEndpointReference;

import javax.xml.namespace.QName;

/**
 * Abstracts wsdl:service/wsdl:port
 *
 * @author Vivek Pandey
 */
public interface WSDLPort extends WSDLFeaturedObject, WSDLExtensible {
    /**
     * Gets wsdl:port@name attribute value as local name and wsdl:definitions@targetNamespace
     * as the namespace uri.
     */
    QName getName();

    /**
     * Gets {@link WSDLBoundPortType} associated with the {@link WSDLPort}.
     */
    @NotNull
    WSDLBoundPortType getBinding();

    /**
     * Gets endpoint address of this port.
     *
     * @return
     *      always non-null.
     */
    EndpointAddress getAddress();

    /**
     * Gets the {@link WSDLService} that owns this port.
     *
     * @return
     *      always non-null.
     */
    @NotNull
    WSDLService getOwner();
    
    /**
     * Returns endpoint reference
     * @return Endpoint reference
     */
    public @Nullable WSEndpointReference getEPR();
}
