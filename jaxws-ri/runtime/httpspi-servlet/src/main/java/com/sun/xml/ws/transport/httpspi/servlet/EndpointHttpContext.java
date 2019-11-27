/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.httpspi.servlet;

import javax.xml.ws.spi.http.HttpContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.io.IOException;

/**
 * @author Jitendra Kotamraju
*/
public final class EndpointHttpContext extends HttpContext {
    private final String urlPattern;

    public EndpointHttpContext(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    void handle(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {
        EndpointHttpExchange exchange = new EndpointHttpExchange(request,response,context, this);
        handler.handle(exchange);
    }

    @Override
    public String getPath() {
        return urlPattern;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public Set<String> getAttributeNames() {
        return null;
    }

}
