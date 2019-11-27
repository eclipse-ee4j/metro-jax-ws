/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.httpspi.servlet;

import javax.xml.ws.Endpoint;
import javax.xml.ws.EndpointContext;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Jitendra Kotamraju
 */
public class EndpointContextImpl extends EndpointContext {

    private final Set<Endpoint> set = new HashSet<Endpoint>();

    void add(Endpoint endpoint) {
        set.add(endpoint);
    }

    public Set<Endpoint> getEndpoints() {
        return set;
    }
}
