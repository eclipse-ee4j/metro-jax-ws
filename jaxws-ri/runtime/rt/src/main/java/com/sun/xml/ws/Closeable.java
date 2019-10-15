/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.Dispatch;
import java.io.IOException;

/**
 * Closeable JAX-WS proxy object.
 *
 * @author Kohsuke Kawaguchi
 * @since JAX-WS 2.0.2
 */
// this interface is exposed to applications.
public interface Closeable extends java.io.Closeable {
    /**
     * Closes this object and cleans up any resources
     * it holds, such as network connections.
     *
     * <p>
     * This interface is implemented by a port proxy
     * or {@link Dispatch}. In particular, this signals
     * the implementation of certain specs (like WS-ReliableMessaging
     * and WS-SecureConversation) to terminate sessions that they
     * create during the life time of a proxy object.
     *
     * <p>
     * This is not a mandatory operation, so the application
     * does not have to call this method.
     *
     *
     * @throws WebServiceException
     *      If clean up fails unexpectedly, this exception
     *      will be thrown (instead of {@link IOException}.
     */
    public void close() throws WebServiceException;
}
