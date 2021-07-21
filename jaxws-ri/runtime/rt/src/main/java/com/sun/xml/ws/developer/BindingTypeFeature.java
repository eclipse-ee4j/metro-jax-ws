/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.developer;


import javax.xml.ws.WebServiceFeature;

import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedData;

/**
 * Using this feature, the application could override the binding used by
 * the runtime(usually determined from WSDL).
 *
 * @author Jitendra Kotamraju
 */
@ManagedData
public final class BindingTypeFeature extends WebServiceFeature {

    public static final String ID = "http://jax-ws.dev.java.net/features/binding";

    private final String bindingId;

    public BindingTypeFeature(String bindingId) {
        this.bindingId = bindingId;
    }

    @ManagedAttribute
    public String getID() {
        return ID;
    }

    @ManagedAttribute
    public String getBindingId() {
        return bindingId;
    }

}
