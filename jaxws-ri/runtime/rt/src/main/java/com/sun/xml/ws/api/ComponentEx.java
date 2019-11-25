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

import com.sun.istack.NotNull;

/**
 * Extended version of {@link Component}.  Allows component to return multiple
 * SPI implementations through an {@link Iterable}.
 * 
 * @since 2.2.6
 */
public interface ComponentEx extends Component {
    /**
     * Gets an iterator of implementations of the specified SPI.
     *
     * <p>
     * This method works as a kind of directory service
     * for SPIs, allowing various components to define private contract
     * and talk to each other.  However unlike {@link Component#getSPI(java.lang.Class)}, this
     * method can support cases where there is an ordered collection (defined
     * by {@link Iterable} of implementations.  The SPI contract should define
     * whether lookups are for the first appropriate implementation or whether 
     * all returned implementations should be used.
     *
     * @return
     *      non-null {@link Iterable} of the SPI's provided by this object.  Iterator may have no values.
     */
    @NotNull <S> Iterable<S> getIterableSPI(@NotNull Class<S> spiType);
}
