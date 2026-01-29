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
import com.sun.xml.ws.api.ResourceLoader;
import com.sun.xml.ws.api.server.BoundEndpoint;
import com.sun.xml.ws.api.server.Container;

import jakarta.servlet.ServletContext;
import jakarta.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * Provides access to {@link ServletContext} via {@link Container}. Pipes
 * can get ServletContext from Container and use it to load some resources.
 */
class ServletContainer extends Container {
    private final ServletContext servletContext;

    private final ServletModule module = new ServletModule() {
        private final List<BoundEndpoint> endpoints = new ArrayList<>();

        @Override
        public @NotNull List<BoundEndpoint> getBoundEndpoints() {
            return endpoints;
        }

        @Override
        public @NotNull String getContextPath() {
            // Cannot compute this since we don't know about hostname and port etc
            throw new WebServiceException("Container "+ServletContainer.class.getName()+" doesn't support getContextPath()");
        }
    };

    private final ResourceLoader loader = new ResourceLoader() {
        @Override
        public URL getResource(String resource) throws MalformedURLException {
            return servletContext.getResource("/WEB-INF/"+resource);
        }
    };

    ServletContainer(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public <T> T getSPI(Class<T> spiType) {
        if (spiType == ServletContext.class) {
            return spiType.cast(servletContext);
        }
        if (spiType.isAssignableFrom(ServletModule.class)) {
            return spiType.cast(module);
        }
        if (spiType == ResourceLoader.class) {
            return spiType.cast(loader);
        }
        return null;
    }
}
