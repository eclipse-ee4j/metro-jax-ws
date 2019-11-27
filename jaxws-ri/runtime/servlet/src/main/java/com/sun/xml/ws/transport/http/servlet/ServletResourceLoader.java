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

import com.sun.xml.ws.transport.http.ResourceLoader;

import javax.servlet.ServletContext;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Set;

/**
 * {@link ResourceLoader} backed by {@link ServletContext}.
 *
 * @author Kohsuke Kawaguchi
 */
final class ServletResourceLoader implements ResourceLoader {
    private final ServletContext context;

    public ServletResourceLoader(ServletContext context) {
        this.context = context;
    }

    public URL getResource(String path) throws MalformedURLException {
        return context.getResource(path);
    }

    public URL getCatalogFile() throws MalformedURLException {
        return getResource("/WEB-INF/jax-ws-catalog.xml");
    }

    public Set<String> getResourcePaths(String path) {
        return context.getResourcePaths(path);
    }
}
