/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.api;

import javax.xml.ws.WebServiceFeature;

public class EnvelopeStyleFeature extends WebServiceFeature {
    
    private EnvelopeStyle.Style[] styles;
    
    public EnvelopeStyleFeature(EnvelopeStyle.Style... s) {
        styles = s;
    }
    
    public EnvelopeStyle.Style[] getStyles() {
        return styles;
    }
    
    public String getID() {
        return EnvelopeStyleFeature.class.getName();
    }
}
