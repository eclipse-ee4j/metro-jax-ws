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
import com.sun.xml.ws.api.pipe.Codec;

/**
 * Implemented by {@link Codec}s that want to have access to
 * {@link WSEndpoint} object.
 *
 * @author Kohsuke Kawaguchi
 * @since 2.1.1
 */
public interface EndpointAwareCodec extends Codec {
    /**
     * Called by the {@link WSEndpoint} implementation
     * when the codec is associated with an endpoint.
     */
    void setEndpoint(@NotNull WSEndpoint endpoint);
}
