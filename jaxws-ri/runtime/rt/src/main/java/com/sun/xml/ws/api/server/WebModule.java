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

/**
 * {@link Module} that is an HTTP container.
 *
 * @author Kohsuke Kawaguchi
 * @since 2.1 EA3
 */
public abstract class WebModule extends Module {
    /**
     * Default constructor.
     */
    protected WebModule() {}
    /**
     * Gets the host, port, and context path portion of this module.
     *
     * <p>
     * For example, if this is an web appliation running in a servlet
     * container "http://myhost/myapp", then this method should return
     * this URI.
     *
     * <p>
     * This method follows the convention of the {@code HttpServletRequest.getContextPath()},
     * and accepts strings like "http://myhost" (for web applications that are deployed
     * to the root context path), or "http://myhost/foobar" (for web applications
     * that are deployed to context path "/foobar")
     *
     * <p>
     * Notice that this method involves in determining the machine name
     * without relying on HTTP "Host" header. 
     */
    public abstract @NotNull String getContextPath();
}
