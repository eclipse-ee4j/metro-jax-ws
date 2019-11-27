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
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.server.WSEndpoint;

/**
 * Interface that allows components to hook up with each other.
 * Replaces {@link com.sun.xml.ws.api.server.EndpointComponent} so that component
 * pattern can apply to more RI types.
 *
 * @since 2.2.6
 * @see WSEndpoint#getComponents()
 * @see ComponentRegistry
 */
public interface Component {
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
    @Nullable <S> S getSPI(@NotNull Class<S> spiType);
}
