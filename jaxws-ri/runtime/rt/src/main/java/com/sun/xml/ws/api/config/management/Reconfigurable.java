/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.config.management;

import jakarta.xml.ws.WebServiceException;

/**
 * Allows to trigger a reconfiguration action on an object.
 *
 * @author Fabian Ritzmann
 */
public interface Reconfigurable {

    /**
     * Executes any action when an endpoint is reconfigured.
     *
     * @throws WebServiceException Thrown if the reconfiguration failed.
     */
    void reconfigure() throws WebServiceException;
    
}
