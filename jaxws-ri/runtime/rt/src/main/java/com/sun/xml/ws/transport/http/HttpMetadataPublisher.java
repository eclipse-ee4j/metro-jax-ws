/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.Component;
import com.sun.xml.ws.api.server.WSEndpoint;

import java.io.IOException;

/**
 * Intercepts GET HTTP requests to process the requests.
 *
 * <p>
 * {@link HttpAdapter} looks for this SPI in {@link WSEndpoint#getComponents()}
 * to allow components to expose additional information through HTTP.
 *
 * @author Kohsuke Kawaguchi
 * @see Component#getSPI(Class)
 * @since 2.1.2
 */
public abstract class HttpMetadataPublisher {
    /**
     * When {@link HttpAdapter} receives a GET request with a query string
     * (which is a convention for metadata requests, such as '?wsdl' or '?xsd=...'),
     * then this method is invoked to allow components to intercept the request.
     *
     * @param adapter
     *      Adapter that accepted the connection.
     * @param connection
     *      Represents the current connection.
     * @return
     *      true if the request is processed. If false is returned the default processing kicks in.
     */
    public abstract boolean handleMetadataRequest(@NotNull HttpAdapter adapter, @NotNull WSHTTPConnection connection) throws IOException;
}
