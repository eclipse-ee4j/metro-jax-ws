/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

/**
 * Receives {@link ResponseContext} at the end of
 * the message invocation.
 *
 * @author Kohsuke Kawaguchi
 */
public interface ResponseContextReceiver {
    /**
     * Called upon the completion of the invocation
     * to set a {@link ResponseContext}.
     *
     * <p>
     * This method is invoked even when the invocation fails.
     */
    void setResponseContext(ResponseContext rc);
}
