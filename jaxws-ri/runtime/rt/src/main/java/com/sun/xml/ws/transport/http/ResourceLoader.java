/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.Set;

/**
 * Used to locate resources for {@link DeploymentDescriptorParser}.
 *
 * <p>
 * This allows {@link DeploymentDescriptorParser} to be used outside a servlet container,
 * but it still needs to work with a layout similar to the web application.
 * If this can be abstracted away better, that would be nice.
 *
 * @author Kohsuke Kawaguchi
 */
public interface ResourceLoader {
    /**
     * Returns the actual location of the resource from the 'path'
     * that represents a virtual locaion of a file inside a web application.
     *
     * @param path
     *      Desiganates an absolute path within an web application, such as:
     *      '/WEB-INF/web.xml' or some such.
     *
     * @return
     *      the actual location, if found, or null if not found.
     */
    URL getResource(String path) throws MalformedURLException;

    /**
     * Gets the catalog XML file that should be consulted when
     * loading resources from this {@link ResourceLoader}.
     */
    URL getCatalogFile() throws MalformedURLException;

    /**
     * Returns the list of files in the given directory.
     *
     * @return
     *      null if the path is invalid. empty if the path didn't contain
     *      any entry in it.
     *
     * @see javax.servlet.ServletContext#getResourcePaths(String)
     */
    Set<String> getResourcePaths(String path);
}
