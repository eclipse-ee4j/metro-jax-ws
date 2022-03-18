/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.runtime.config;

import com.sun.xml.ws.api.FeatureConstructor;
import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedData;

import jakarta.xml.ws.WebServiceFeature;
import java.util.List;

/**
 * WebServiceFeature for the Tubeline {@link jakarta.xml.ws.WebServiceFeature}
 *
 * @author Fabian Ritzmann
 */
@ManagedData
public class TubelineFeature extends WebServiceFeature {

    public static final String ID = "com.sun.xml.ws.runtime.config.TubelineFeature";

    @FeatureConstructor({
        "enabled"
    })
    public TubelineFeature(boolean enabled) {
        super.enabled = enabled;
    }

    @Override
    @ManagedAttribute
    public String getID() {
        return ID;
    }

    // TODO implement
    List<String> getTubeFactories() {
        return null;
    }

}
