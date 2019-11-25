/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.config.management;

import com.sun.xml.ws.api.server.Invoker;

import org.xml.sax.EntityResolver;

/**
 * Store the parameters that were passed into the original WSEndpoint instance
 * upon creation. This allows us to instantiate a new instance with the same
 * parameters.
 *
 * @author Fabian Ritzmann
 */
public class EndpointCreationAttributes {

    private final boolean processHandlerAnnotation;
    private final Invoker invoker;
    private final EntityResolver entityResolver;
    private final boolean isTransportSynchronous;

    /**
     * Instantiate this data access object.
     *
     * @param processHandlerAnnotation The original processHandlerAnnotation setting.
     * @param invoker The original Invoker instance.
     * @param resolver The original EntityResolver instance.
     * @param isTransportSynchronous The original isTransportSynchronous setting.
     */
    public EndpointCreationAttributes(final boolean processHandlerAnnotation,
            final Invoker invoker,
            final EntityResolver resolver,
            final boolean isTransportSynchronous) {
        this.processHandlerAnnotation = processHandlerAnnotation;
        this.invoker = invoker;
        this.entityResolver = resolver;
        this.isTransportSynchronous = isTransportSynchronous;
    }

    /**
     * Return the original processHandlerAnnotation setting.
     *
     * @return The original processHandlerAnnotation setting.
     */
    public boolean isProcessHandlerAnnotation() {
        return this.processHandlerAnnotation;
    }

    /**
     * Return the original Invoker instance.
     *
     * @return The original Invoker instance.
     */
    public Invoker getInvoker() {
        return this.invoker;
    }

    /**
     * Return the original EntityResolver instance.
     *
     * @return The original EntityResolver instance.
     */
    public EntityResolver getEntityResolver() {
        return this.entityResolver;
    }

    /**
     * Return the original isTransportSynchronous setting.
     *
     * @return The original isTransportSynchronous setting.
     */
    public boolean isTransportSynchronous() {
        return this.isTransportSynchronous;
    }
}
