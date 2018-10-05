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

import java.util.Set;

import com.sun.istack.NotNull;

/**
 * Registry for component delegates.  It is expected that implementations of
 * ComponentRegistry will delegate to registered {@link Component}s in its own
 * implementation of {@link Component#getSPI(java.lang.Class)}, either before or after it
 * considers its own SPI implementations.
 * 
 * @since 2.2.6
 */
public interface ComponentRegistry extends Component {
	/**
	 * Returns the set of {@link Component}s registered with this object
	 * @return set of registered components
	 */
    public @NotNull Set<Component> getComponents();
}
