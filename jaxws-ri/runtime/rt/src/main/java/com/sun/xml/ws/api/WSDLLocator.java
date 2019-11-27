/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Service;

/**
 * Used to locate WSDL documents; particularly useful for J2EE deployment archives
 *
 * @since 2.2.6
 */
public abstract class WSDLLocator {

    /**
     * Returns the actual WSDL location
     *
     * @param service Service class
     * @param wsdlLoc Designates the WSDL location either from the service class
     * or through other means
     * @return the actual WSDL location, if found, or null if not found.
     * @throws MalformedURLException if there is an error in creating URL
     */
    public abstract URL locateWSDL(Class<Service> service, String wsdlLoc) throws MalformedURLException;

}
