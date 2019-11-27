/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.servlet;

import com.sun.xml.ws.transport.http.HttpAdapterList;
import com.sun.xml.ws.api.server.WSEndpoint;

import javax.servlet.ServletContext;

/**
 * List (and a factory) of {@link ServletAdapter}.
 *
 * @author Jitendra Kotamraju
 */
public class ServletAdapterList extends HttpAdapterList<ServletAdapter> {
    private final ServletContext context;

    /**
     * Keeping it for GlassFishv2 compatibility. Move to
     * {@link #ServletAdapterList(ServletContext) }
     *
     * @deprecated
     */
    @Deprecated
    public ServletAdapterList() {
        context = null;
    }

    public ServletAdapterList(ServletContext ctxt) {
        this.context = ctxt;
    }

    /* package */ ServletContext getServletContext() {
        return context;
    }
    
    @Override
    protected ServletAdapter createHttpAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
        return new ServletAdapter(name, urlPattern, endpoint, this);
    }
}
