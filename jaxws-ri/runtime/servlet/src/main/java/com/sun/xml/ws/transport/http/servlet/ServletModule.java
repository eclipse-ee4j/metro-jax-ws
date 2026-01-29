/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.servlet;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.server.Module;
import com.sun.xml.ws.api.server.WebModule;
import jakarta.servlet.http.HttpServletRequest;

/**
 * {@link WebModule} that is a servlet container.
 *
 * @see WebModule
 * @see Module
 *
 * @author Jitendra Kotamraju
 */
public abstract class ServletModule extends WebModule {

    /**
     * Default constructor.
     */
    protected ServletModule() {}

    /**
     * Gets the host, port, and context path portion of this module using
     * {@link HttpServletRequest}
     *
     * <p>
     * This method follows the convention of the <code>HttpServletRequest.getContextPath()</code>,
     * and accepts strings like "http://myhost" (for web applications that are deployed
     * to the root context path), or "http://myhost/foobar" (for web applications
     * that are deployed to context path "/foobar")
     *
     * TODO should we generify WebModule so that we could use
     * TODO WebModule&lt;HttpServletRequest&gt; ??
     * @param req the HTTP request object
     * @return the host, port, and context path
     */
    public @NotNull String getContextPath(HttpServletRequest req) {
        return ServletConnectionImpl.getBaseAddress(req);
    }

}
