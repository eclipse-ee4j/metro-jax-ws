/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.fastinfoset;

import com.sun.xml.ws.api.FeatureConstructor;

import javax.xml.ws.WebServiceFeature;

import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedData;

/**
 * Enable or disable Fast Infoset on a Web service.
 * <p>
 * The following describes the affects of this feature with respect
 * to being enabled or disabled:
 * <ul>
 *  <li> ENABLED: In this Mode, Fast Infoset will be enabled.
 *  <li> DISABLED: In this Mode, Fast Infoset will be disabled and the 
 *       Web service will not process incoming messages or produce outgoing
 *       messages encoded using Fast Infoset.
 * </ul>
 * <p>
 * If this feature is not present on a Web service then the default behaviour
 * is equivalent to this feature being present and enabled.
 * @author Paul.Sandoz@Sun.Com
 */
@ManagedData
public class FastInfosetFeature extends WebServiceFeature {
    /**
     * Constant value identifying the {@link FastInfosetFeature}
     */
    public static final String ID = "http://java.sun.com/xml/ns/jaxws/fastinfoset";

    /**
     * Create a {@link FastInfosetFeature}. The instance created will be enabled.
     */
    public FastInfosetFeature() {
        this.enabled = true;
    }

    /**
     * Create a {@link FastInfosetFeature}
     *
     * @param enabled specifies whether this feature should
     *                be enabled or not.
     */
    @FeatureConstructor({"enabled"})
    public FastInfosetFeature(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * {@inheritDoc}
     */
    @ManagedAttribute
    public String getID() {
        return ID;
    }
}
