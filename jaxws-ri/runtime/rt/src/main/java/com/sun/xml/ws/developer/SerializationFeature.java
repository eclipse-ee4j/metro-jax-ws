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

import javax.xml.ws.WebServiceFeature;

/**
 * {@link javax.xml.ws.WebServiceFeature} for configuration serialization.
 *
 * @since JAX-WS RI 2.2.6
 * @author Jitendra Kotamraju
 * @see com.sun.xml.ws.developer.Serialization
 */
public class SerializationFeature extends WebServiceFeature {
    /**
     * Constant value identifying this feature
     */
    public static final String ID = "http://jax-ws.java.net/features/serialization";
    private final String encoding;

    public SerializationFeature() {
        this("");
    }

    @FeatureConstructor({"encoding"})
    public SerializationFeature(String encoding) {
        this.encoding = encoding;
    }

    public String getID() {
        return ID;
    }

    public String getEncoding() {
        return encoding;
    }
}
