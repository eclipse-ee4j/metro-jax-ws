/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api;

import java.util.List;

import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.client.Stub;
import javax.xml.ws.WebServiceFeature;

/**
 * Allows registration of multiple {@link Component}s against the {@link ComponentRegistry} implementations
 * of the {@link Container}, {@link WSEndpoint}, {@link WSService}, or {@link Stub}.  The
 * registration is guaranteed to occur early in the initialization of these objects prior to tubeline creation
 * (applicable to endpoint and stub only).
 * <p>
 * Because the Container is shared among all Stubs created from a common WSService object, this feature must 
 * be passed during WSService initialization in order to register a Component against the client-side Container.
 * <p>
 * IllegalArgumentException will be thrown if the feature is used with an inappropriate target, e.g. stub target
 * used during WSEndpoint initialization.
 * 
 * @since 2.2.8
 */
public class ComponentsFeature extends WebServiceFeature implements ServiceSharedFeatureMarker {
    private final List<ComponentFeature> componentFeatures;

    /**
     * Constructs ComponentFeature with indicated component and target
     * @param componentFeatures components
     */
    public ComponentsFeature(List<ComponentFeature> componentFeatures) {
        this.enabled = true;
        this.componentFeatures = componentFeatures;
    }

    @Override
    public String getID() {
        return ComponentsFeature.class.getName();
    }

    /**
     * Retrieves component
     * @return component
     */
    public List<ComponentFeature> getComponentFeatures() {
        return componentFeatures;
    }
}
