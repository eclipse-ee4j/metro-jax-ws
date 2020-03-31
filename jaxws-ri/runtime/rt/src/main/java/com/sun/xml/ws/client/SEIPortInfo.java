/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.binding.SOAPBindingImpl;
import com.sun.xml.ws.binding.WebServiceFeatureList;
import com.sun.xml.ws.model.SOAPSEIModel;

import jakarta.xml.ws.WebServiceFeature;

/**
 * {@link PortInfo} that has {@link SEIModel}.
 *
 * This object is created statically when {@link WSServiceDelegate} is created
 * with an service interface.
 *
 * NOTE: Made this class public so that Dispatch instances derived from a
 *       'parent' SEI-based port instance (generally for sending protocol
 *       messages or request retries) can still know what the parent's SEI was.
 *
 * @author Kohsuke Kawaguchi
 */
public final class SEIPortInfo extends PortInfo {

    public final Class sei;

    /**
     * Model of {@link #sei}.
     */
    public final SOAPSEIModel model;

    public SEIPortInfo(WSServiceDelegate owner, Class sei, SOAPSEIModel model, @NotNull WSDLPort portModel) {
        super(owner, portModel);
        this.sei = sei;
        this.model = model;
        assert sei != null && model != null;
    }

    @Override
    public BindingImpl createBinding(WebServiceFeature[] webServiceFeatures, Class<?> portInterface) {
        BindingImpl binding = super.createBinding(webServiceFeatures, portInterface);
        return setKnownHeaders(binding);
    }

    public BindingImpl createBinding(WebServiceFeatureList webServiceFeatures, Class<?> portInterface) {
        // not to pass in (BindingImpl) model.getWSBinding()
        BindingImpl binding = super.createBinding(webServiceFeatures, portInterface, null);
        return setKnownHeaders(binding);
    }

    private BindingImpl setKnownHeaders(BindingImpl binding) {
        if (binding instanceof SOAPBindingImpl) {
            ((SOAPBindingImpl) binding).setPortKnownHeaders(model.getKnownHeaders());
        }
        return binding;
    }
}
