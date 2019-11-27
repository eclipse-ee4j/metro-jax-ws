/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.developer.servlet;

import com.sun.xml.ws.api.FeatureConstructor;

import javax.xml.ws.WebServiceFeature;

/**
 * {@link WebServiceFeature} for {@link HttpSessionScope}.
 * @author Kohsuke Kawaguchi
 */
public class HttpSessionScopeFeature extends WebServiceFeature {
    /**
     * Constant value identifying the {@link HttpSessionScope} feature.
     */
    public static final String ID = "http://jax-ws.dev.java.net/features/servlet/httpSessionScope";

    @FeatureConstructor
    public HttpSessionScopeFeature() {
        this.enabled = true;
    }

    public String getID() {
        return ID;
    }
}
