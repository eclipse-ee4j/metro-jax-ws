/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.server;

import com.sun.istack.NotNull;
import com.sun.xml.ws.transport.http.HttpAdapter;

/**
 * Light-weight http server transport for {@link WSEndpoint}.
 * It provides a way to start the transport at a local http address and
 * to stop the transport.
 *
 * @author Jitendra Kotamraju
 */
public abstract class HttpEndpoint {

    /**
     * Factory to deploy {@link WSEndpoint} on light-weight
     * http server container.
     *
     * @param endpoint that needs to be deployed at http server
     * @return transport object for the endpoint
     */
    public static HttpEndpoint create(@NotNull WSEndpoint endpoint) {
        return new com.sun.xml.ws.transport.http.server.HttpEndpoint(null, HttpAdapter.createAlone(endpoint));
    }

    /**
     * Publishes this endpoint at a localhost's http address.
     *
     * @param address endpoint's http address
     *        for e.g http://localhost:8080/ctxt/pattern
     */
    public abstract void publish(@NotNull String address);

    /**
     * Stops the published endpoint
     */
    public abstract void stop();
    
}
