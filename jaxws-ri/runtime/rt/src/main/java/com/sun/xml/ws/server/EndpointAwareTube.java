/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.server.WSEndpoint;

/**
 * Tubes that implement this interface will receive notification of the WSEndpoint
 * holding the tubeline after successful endpoint creation.
 * 
 * @since 2.2.6
 */
public interface EndpointAwareTube extends Tube {
	/**
	 * Setter for WSEndpoint holding this tube
	 * @param endpoint WSEndpoint holding this tube
	 */
    void setEndpoint(WSEndpoint<?> endpoint);
}
