/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api;

import javax.xml.ws.WebServiceFeature;

@FeatureListValidatorAnnotation(bean = TestFeatureListValidator.class)
public class AddsAddressingFeature extends WebServiceFeature {

    public AddsAddressingFeature() {
        this.enabled = true;
    }
    
    @Override
    public String getID() {
        return AddsAddressingFeature.class.getName();
    }

}
