/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.AddressingFeature;

public class TestFeatureListValidator implements FeatureListValidator {

    @Override
    public void validate(WSFeatureList list) {
        if (list.isEnabled(AddsAddressingFeature.class)) {
            AddressingFeature af = list.get(AddressingFeature.class);
            if (af == null) {
                list.mergeFeatures(new WebServiceFeature[] {new AddressingFeature()}, true);
            } else if (!af.isEnabled()) {
                throw new WebServiceException("AddressingFeature is disabled; however, AddsAddressingFeature requires it");
            }
        }
        
        if (list.isEnabled(InvalidFeature.class)) {
            throw new WebServiceException("List contains InvalidFeature");
        }
    }

}
