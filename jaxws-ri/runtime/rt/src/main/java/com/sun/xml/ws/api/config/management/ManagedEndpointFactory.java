/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.config.management;

import com.sun.xml.ws.api.server.WSEndpoint;

/**
 * Interface to create a new WSEndpoint wrapper. This is intended to be implemented
 * by the configuration management to return a ManagedEndpoint that wraps the
 * original WSEndpoint instance.
 *
 * @author Fabian Ritzmann
 */
public interface ManagedEndpointFactory {

    /**
     * This method may return a WSEndpoint implementation that wraps the original
     * WSEndpoint instance. This allows to interject e.g. management code. If
     * management has not been enabled for this endpoint, it will return the original
     * WSEndpoint instance.
     *
     * @param <T> The endpoint implementation type.
     * @param endpoint The endpoint instance.
     * @param attributes The parameters with which the original endpoint instance
     *   was created.
     * @return A WSEndpoint that wraps the original WSEndpoint instance or the
     *   original WSEndpoint instance.
     */
    public <T> WSEndpoint<T> createEndpoint(WSEndpoint<T> endpoint, EndpointCreationAttributes attributes);

}
