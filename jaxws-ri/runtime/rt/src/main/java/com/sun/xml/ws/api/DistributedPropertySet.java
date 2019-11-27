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

import com.sun.istack.NotNull;

/**
 * Placeholder for backwards compatibility.
 *
 * @deprecated Use com.oracle.webservices.api.message.DistributedPropertySet instead.
 * @author Kohsuke Kawaguchi
 */
public abstract class DistributedPropertySet extends com.oracle.webservices.api.message.BaseDistributedPropertySet {
    
    /**
     * @deprecated
     */
    public void addSatellite(@NotNull PropertySet satellite) {
        super.addSatellite(satellite);
    }
    
    /**
     * @deprecated
     */
    public void addSatellite(@NotNull Class keyClass, @NotNull PropertySet satellite) {
        super.addSatellite(keyClass, satellite);
    }
    
    /**
     * @deprecated
     */
    public void copySatelliteInto(@NotNull DistributedPropertySet r) {
        super.copySatelliteInto(r);
    }
    
    /**
     * @deprecated
     */
    public void removeSatellite(PropertySet satellite) {
        super.removeSatellite(satellite);
    }
}
