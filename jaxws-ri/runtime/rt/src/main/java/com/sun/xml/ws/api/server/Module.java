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
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.Component;
import com.sun.xml.ws.transport.http.HttpAdapterList;

import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;
import java.util.List;

/**
 * Represents an object scoped to the current "module" (like a JavaEE web appliation).
 *
 * <p>
 * This object can be obtained from {@link Container#getSPI(Class)}.
 *
 * <p>
 * The scope of the module is driven by {@link W3CEndpointReferenceBuilder#build()}'s
 * requirement that we need to identify a {@link WSEndpoint} that has a specific
 * service/port name.
 *
 * <p>
 * For JavaEE containers this should be scoped to a JavaEE application. For
 * other environment, this could be scoped to any similar notion. If no such
 * notion is available, the implementation of {@link Container} can return
 * a new {@link Module} object each time {@link Container#getSPI(Class)} is invoked.
 *
 * <p>
 * There's a considerable overlap between this and {@link HttpAdapterList}.
 * The SPI really needs to be reconsidered 
 * 
 *
 * @see Container
 * @author Kohsuke Kawaguchi
 * @since 2.1 EA3
 */
public abstract class Module implements Component {
    /**
     * Gets the list of {@link BoundEndpoint} deployed in this module.
     *
     * <p>
     * From the point of view of the {@link Module} implementation,
     * it really only needs to provide a {@link List} object as a data store.
     * JAX-WS will update this list accordingly. 
     *
     * @return
     *      always return the same non-null instance.
     */
    public abstract @NotNull List<BoundEndpoint> getBoundEndpoints();
    
    public @Nullable <S> S getSPI(@NotNull Class<S> spiType) {
    	return null;
    }

}
