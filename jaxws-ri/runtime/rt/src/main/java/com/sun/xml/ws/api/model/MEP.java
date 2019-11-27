/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model;

/**
 * Constants that represents four message exchange patterns.
 *
 * @author Kohsuke Kawaguchi
 */
public enum MEP {
    REQUEST_RESPONSE(false),
    ONE_WAY(false),
    ASYNC_POLL(true),
    ASYNC_CALLBACK(true);

    /**
     * True for {@link #ASYNC_CALLBACK} and {@link #ASYNC_POLL}.
     */
    public final boolean isAsync;

    MEP(boolean async) {
        isAsync = async;
    }

    public final boolean isOneWay() {
        return this==ONE_WAY;
    }
}
