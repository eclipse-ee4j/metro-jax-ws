/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.server;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.Component;

/**
 * Interface that allows components around {@link WSEndpoint} to hook up
 * with each other.
 *
 * @author Kohsuke Kawaguchi
 * @since 2.1.2
 * @see WSEndpoint#getComponentRegistry()
 * @deprecated replaced by {@link Component}
 */
public interface EndpointComponent {
    /**
     * Gets the specified SPI.
     *
     * <p>
     * This method works as a kind of directory service
     * for SPIs, allowing various components to define private contract
     * and talk to each other.
     *
     * @return
     *      null if such an SPI is not provided by this object.
     */
    @Nullable <T> T getSPI(@NotNull Class<T> spiType);
}
