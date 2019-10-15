/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler;

import com.sun.xml.ws.api.ResourceLoader;
import com.sun.xml.ws.api.server.Container;

import java.net.MalformedURLException;
import java.net.URL;

class MockupMetroConfigLoader extends ResourceLoader {

    private static final String UNIT_TEST_RESOURCE_ROOT = "tubes-config/";
    private final String resourceName;

    private MockupMetroConfigLoader(String resourceName) {
        this.resourceName = UNIT_TEST_RESOURCE_ROOT + resourceName;
    }

    @Override
    public URL getResource(String resource) throws MalformedURLException {
        if ("jaxws-tubes.xml".equals(resource)) {
            return Thread.currentThread().getContextClassLoader().getResource(resourceName);
        } else if ("tubes-config/jaxws-tubes-default.xml".equals(resource)) {
            return Thread.currentThread().getContextClassLoader().getResource(UNIT_TEST_RESOURCE_ROOT + "jaxws-tubes-default.xml");
        } else {
            return Thread.currentThread().getContextClassLoader().getResource(resource);
        }
    }

    public static Container createMockupContainer(final String metroConfigResource) {
        return new Container() {

            private final ResourceLoader loader = new MockupMetroConfigLoader(metroConfigResource);

            @Override
            public <T> T getSPI(Class<T> spiType) {
                if (ResourceLoader.class.isAssignableFrom(spiType)) {
                    return spiType.cast(loader);
                } else {
                    return null;
                }
            }
        };
    }
}
