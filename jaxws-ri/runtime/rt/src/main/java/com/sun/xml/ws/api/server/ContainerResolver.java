/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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
 * This class determines an instance of {@link Container} for the runtime.
 * It applies for both server and client runtimes(for e.g in Servlet could
 * be accessing a Web Service).
 *
 * A client that is invoking a web service may be running in a
 * container(for e.g servlet). T
 *
 * <p>
 * ContainerResolver uses a static field to keep the instance of the resolver object.
 * Typically appserver may set its custom container resolver using the static method
 * {@link #setInstance(ContainerResolver)}
 *
 * @author Jitendra Kotamraju
 */
public abstract class ContainerResolver {

    private static final ThreadLocalContainerResolver DEFAULT = new ThreadLocalContainerResolver();

    private static volatile ContainerResolver theResolver = DEFAULT;

    /**
     * Sets the custom container resolver which can be used to get client's
     * {@link Container}.
     *
     * @param resolver container resolver
     */
    public static void setInstance(ContainerResolver resolver) {
        if(resolver==null)
            resolver = DEFAULT;
        theResolver = resolver;
    }

    /**
     * Returns the container resolver which can be used to get client's {@link Container}.
     *
     * @return container resolver instance
     */
    public static @NotNull ContainerResolver getInstance() {
        return theResolver;
    }

    /**
     * Returns the default container resolver which can be used to get {@link Container}.
     *
     * @return default container resolver
     */
    public static ThreadLocalContainerResolver getDefault() {
        return DEFAULT;
    }

    /**
     * Returns the {@link Container} context in which client is running.
     *
     * @return container instance for the client
     */
    public abstract @NotNull Container getContainer();

}
