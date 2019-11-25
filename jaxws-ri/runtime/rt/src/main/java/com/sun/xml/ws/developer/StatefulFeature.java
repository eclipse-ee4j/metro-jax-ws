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

import javax.jws.WebService;
import javax.xml.ws.WebServiceFeature;

import com.sun.xml.ws.api.ha.StickyFeature;
import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedData;


/**
 * Designates a stateful {@link WebService}.
 * A service class that has this feature on will behave as a stateful web service.
 *
 * @since 2.1
 * @see StatefulWebServiceManager
 */
@ManagedData
public class StatefulFeature extends WebServiceFeature implements StickyFeature {
    /**
     * Constant value identifying the StatefulFeature
     */
    public static final String ID = "http://jax-ws.dev.java.net/features/stateful";

    /**
     * Create an <code>StatefulFeature</code>.
     * The instance created will be enabled.
     */
    @FeatureConstructor
    public StatefulFeature() {
        this.enabled = true;
    }

    @ManagedAttribute
    public String getID() {
        return ID;
    }
}
