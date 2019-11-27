/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.server;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.message.Packet;

import javax.xml.ws.WebServiceContext;

/**
 * {@link WebServiceContext} that exposes JAX-WS RI specific additions.
 *
 * <p>
 * {@link WebServiceContext} instances that JAX-WS injects always
 * implement this interface.
 *
 * <p>
 * The JAX-WS RI may add methods on this interface, so do not implement
 * this interface in your code, or risk {@link LinkageError}.
 *
 * @author Kohsuke Kawaguchi
 */
public interface WSWebServiceContext extends WebServiceContext {
    /**
     * Obtains the request packet that is being processed.
     * @return Packet for the request
     */
    @Nullable Packet getRequestPacket();
}
