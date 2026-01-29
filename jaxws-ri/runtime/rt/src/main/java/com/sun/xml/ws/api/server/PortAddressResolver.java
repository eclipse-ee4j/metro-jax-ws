/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.server;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.xml.namespace.QName;

/**
 * Resolves port address for an endpoint. A WSDL may contain multiple
 * endpoints, and some of the endpoints may be packaged in a single WAR file.
 * If an endpoint is serving the WSDL, it would be nice to fill the port addresses
 * of other endpoints in the WAR.
 *
 * <p>
 * This interface is implemented by the caller of
 * {@link SDDocument#writeTo} method so
 * that the {@link SDDocument} can correctly fills the addresses of known
 * endpoints.
 *
 *
 * @author Jitendra Kotamraju
 */
public abstract class PortAddressResolver {
    /**
     * Default constructor.
     */
    public PortAddressResolver() {}
    /**
     * Gets the endpoint address for a WSDL port
     *
     * @param serviceName
     *       WSDL service name(wsd:service in WSDL) for which address is needed. Always non-null.
     * @param portName
     *       WSDL port name(wsdl:port in WSDL) for which address is needed. Always non-null.
     * @return
     *      The address needs to be put in WSDL for port element's location
     *      attribute. Can be null. If it is null, existing port address
     *      is written as it is (without any patching).
     */
    public abstract @Nullable String getAddressFor(@NotNull QName serviceName, @NotNull String portName);

    /**
     * Gets the endpoint address for a WSDL port
     *
     * @param serviceName
     *       WSDL service name(wsd:service in WSDL) for which address is needed. Always non-null.
     * @param portName
     *       WSDL port name(wsdl:port in WSDL) for which address is needed. Always non-null.
     * @param currentAddress
     *       Whatever current address specified for the port in the WSDL
     * @return
     *      The address needs to be put in WSDL for port element's location
     *      attribute. Can be null. If it is null, existing port address
     *      is written as it is (without any patching).
     */
    public @Nullable String getAddressFor(@NotNull QName serviceName, @NotNull String portName, String currentAddress) {
        return getAddressFor(serviceName, portName);
    }
}
