/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api;

import com.sun.xml.ws.api.server.Container;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Used to locate resources for jax-ws extensions. Using this, extensions
 * do not to have to write container specific code to locate resources.
 *
 * @author Jitendra Kotamraju
 */
public abstract class ResourceLoader {

    /**
     * Returns the actual location of the resource from the 'resource' arg
     * that represents a virtual locaion of a file understood by a container.
     * ResourceLoader impl for a Container knows how to map this
     * virtual location to actual location.
     * <p>
     * Extensions can get hold of this object using {@link Container}.
     * <br>
     * for e.g.:
     * <pre>
     * ResourceLoader loader = container.getSPI(ResourceLoader.class);
     * URL catalog = loader.get("jax-ws-catalog.xml");
     * </pre>
     * A ResourceLoader for servlet environment, may do the following.
     * <pre>
     * URL getResource(String resource) {
     *     return servletContext.getResource("/WEB-INF/"+resource);
     * }
     * </pre>
     *
     * @param resource Designates a path that is understood by the container. The
     *             implementations must support "jax-ws-catalog.xml" resource.
     * @return the actual location, if found, or null if not found.
     * @throws MalformedURLException if there is an error in creating URL
     */
    public abstract URL getResource(String resource) throws MalformedURLException;

}
