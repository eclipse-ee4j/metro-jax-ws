/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.server;

import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
import com.sun.istack.Nullable;

import javax.xml.namespace.QName;

/**
 * Implementations of this class can contribute properties associated with an Endpoint. The properties appear as
 * extensibility elements inside the EndpointReference of the endpoint. If any EPR extensibility elements are configured
 * for an endpoint, the EndpointReference is published inside the WSDL.
 * 
 * @since JAX-WS 2.2
 * @author Rama Pulavarthi
 */
public abstract class EndpointReferenceExtensionContributor {
    /**
     *
     * @param extension EPRExtension is passed if an extension with same QName is already configured on the endpoint
     *      via other means (one possible way is by embedding EndpointReference in WSDL).
     *
     * @return  EPRExtension that should be finally configured on an Endpoint.
     */
    public abstract WSEndpointReference.EPRExtension getEPRExtension(WSEndpoint endpoint, @Nullable WSEndpointReference.EPRExtension extension );

    /**
     *
     * @return QName of the extensibility element that is contributed by this extension.
     */
    public abstract QName getQName();
}
