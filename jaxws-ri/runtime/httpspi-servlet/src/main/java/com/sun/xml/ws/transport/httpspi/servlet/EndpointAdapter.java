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

import javax.xml.ws.Endpoint;
import javax.xml.ws.spi.http.HttpContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jitendra Kotamraju
*/
public final class EndpointAdapter {
    private final Endpoint endpoint;
    private final String urlPattern;
    private final EndpointHttpContext httpContext;

    public EndpointAdapter(Endpoint endpoint, String urlPattern) {
        this.endpoint = endpoint;
        this.urlPattern = urlPattern;
        httpContext = new EndpointHttpContext(urlPattern);
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public HttpContext getContext() {
        return httpContext;
    }

    public void publish() {
        endpoint.publish(httpContext);
    }

    public void dispose() {
        endpoint.stop();
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void handle(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {
        httpContext.handle(context, request, response);
    }

    /**
     * Returns the "/abc/def/ghi" portion if
     * the URL pattern is "/abc/def/ghi/*".
     */
    public String getValidPath() {
        if (urlPattern.endsWith("/*")) {
            return urlPattern.substring(0, urlPattern.length() - 2);
        } else {
            return urlPattern;
        }
    }
    
}
