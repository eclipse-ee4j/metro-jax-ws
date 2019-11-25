/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.developer;

import com.sun.xml.ws.api.FeatureConstructor;
import com.sun.xml.ws.server.DraconianValidationErrorHandler;

import javax.xml.ws.WebServiceFeature;

import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedData;

/**
 * {@link WebServiceFeature} for schema validation.
 *
 * @since JAX-WS 2.1.3
 * @author Jitendra Kotamraju
 * @see SchemaValidation
 */
@ManagedData
public class SchemaValidationFeature extends WebServiceFeature {
    /**
     * Constant value identifying the SchemaValidationFeature
     */
    public static final String ID = "http://jax-ws.dev.java.net/features/schema-validation";

    private final Class<? extends ValidationErrorHandler> clazz;
    private final boolean inbound;
    private final boolean outbound;

    public SchemaValidationFeature() {
        this(true, true, DraconianValidationErrorHandler.class);
    }

    /**
     * Create an <code>SchemaValidationFeature</code>.
     * The instance created will be enabled.
     */
    public SchemaValidationFeature(Class<? extends ValidationErrorHandler> clazz) {
        this(true, true, clazz);
    }

    /**
     * @since JAX-WS RI 2.2.2
     */
    public SchemaValidationFeature(boolean inbound, boolean outbound) {
        this(inbound, outbound, DraconianValidationErrorHandler.class);
    }

    /**
     * @since JAX-WS RI 2.2.2
     */
    @FeatureConstructor({"inbound", "outbound", "handler"})
    public SchemaValidationFeature(boolean inbound, boolean outbound, Class<? extends ValidationErrorHandler> clazz) {
        this.enabled = true;
        this.inbound = inbound;
        this.outbound = outbound;
        this.clazz = clazz;
    }

    @ManagedAttribute
    @Override
    public String getID() {
        return ID;
    }

    /**
     * Invalid schema instances are rejected, a SOAP fault message is created
     * for any invalid request and response message. If it is set to false, schema
     * validation messages are just logged.
     */
    @ManagedAttribute
    public Class<? extends ValidationErrorHandler> getErrorHandler() {
        return clazz;
    }

    /**
     * Turns validation on/off for inbound messages
     *
     * @since JAX-WS RI 2.2.2
     */
    public boolean isInbound() {
        return inbound;
    }

    /**
     * Turns validation on/off for outbound messages
     *
     * @since JAX-WS RI 2.2.2
     */
    public boolean isOutbound() {
        return outbound;
    }
}
