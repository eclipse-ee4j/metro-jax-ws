/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.server;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.server.BoundEndpoint;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.api.server.Module;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jitendra Kotamraju
 */
class ServerContainer extends Container {

    private final Module module = new Module() {
        private final List<BoundEndpoint> endpoints = new ArrayList<BoundEndpoint>();

        public @NotNull List<BoundEndpoint> getBoundEndpoints() {
            return endpoints;
        }
    };

    public <T> T getSPI(Class<T> spiType) {
        T t = super.getSPI(spiType);
        if (t != null)
            return t;
        if (spiType == Module.class) {
            return spiType.cast(module);
        }
        return null;
    }
    
}
