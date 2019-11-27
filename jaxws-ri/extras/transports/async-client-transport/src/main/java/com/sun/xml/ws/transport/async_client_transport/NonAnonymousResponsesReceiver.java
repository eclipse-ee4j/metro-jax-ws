/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.async_client_transport;

/**
 * @author Rama.Pulavarthi@sun.com
 */
public interface NonAnonymousResponsesReceiver<T> {
    void register(NonAnonymousResponseHandler<T> handler);
    void unregister(NonAnonymousResponseHandler<T> handler);
    String getAddress();
}
