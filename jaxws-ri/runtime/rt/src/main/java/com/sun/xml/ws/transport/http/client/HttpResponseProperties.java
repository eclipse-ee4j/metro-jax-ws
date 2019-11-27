/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.client;

import com.oracle.webservices.api.message.BasePropertySet;
import com.oracle.webservices.api.message.PropertySet;
import com.sun.istack.NotNull;
import com.sun.xml.ws.client.ResponseContext;

import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;


/**
 * Properties exposed from {@link HttpTransportPipe} for {@link ResponseContext}.
 *
 * @author Kohsuke Kawaguchi
 */
final class HttpResponseProperties extends BasePropertySet {

    private final HttpClientTransport deferedCon;

    public HttpResponseProperties(@NotNull HttpClientTransport con) {
        this.deferedCon = con;
    }

    @Property(MessageContext.HTTP_RESPONSE_HEADERS)
    public Map<String, List<String>> getResponseHeaders() {
        return deferedCon.getHeaders();
    }

    @Property(MessageContext.HTTP_RESPONSE_CODE)
    public int getResponseCode() {
        return deferedCon.statusCode;
    }

    @Override
    protected PropertyMap getPropertyMap() {
        return model;
    }
        
    private static final PropertyMap model;

    static {
        model = parse(HttpResponseProperties.class);
    }
}
