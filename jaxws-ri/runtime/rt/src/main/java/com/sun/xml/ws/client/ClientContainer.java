/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

import com.sun.xml.ws.api.ResourceLoader;
import com.sun.xml.ws.api.server.Container;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Jitendra Kotamraju
 */
final class ClientContainer extends Container {

    private final ResourceLoader loader = new ResourceLoader() {
        public URL getResource(String resource) throws MalformedURLException {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl == null) {
                cl = this.getClass().getClassLoader();
            }
            return cl.getResource("META-INF/"+resource);
        }
    };

    public <T> T getSPI(Class<T> spiType) {
        T t = super.getSPI(spiType);
        if (t != null)
            return t;
        if (spiType == ResourceLoader.class) {
            return spiType.cast(loader);
        }
        return null;
    }

}

