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

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpContext;
import javax.xml.ws.handler.MessageContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.net.InetSocketAddress;
import java.security.Principal;

/**
 * @author Jitendra Kotamraju
*/
final class EndpointHttpExchange extends HttpExchange {
    private final HttpServletRequest req;
    private final HttpServletResponse res;
    private final ExchangeRequestHeaders reqHeaders;
    private final ExchangeResponseHeaders resHeaders;
    private final ServletContext servletContext;
    private final HttpContext httpContext;
    private static final Set<String> attributes = new HashSet<String>();
    static {
        attributes.add(MessageContext.SERVLET_CONTEXT);
        attributes.add(MessageContext.SERVLET_REQUEST);
        attributes.add(MessageContext.SERVLET_RESPONSE);
    }

    EndpointHttpExchange(HttpServletRequest req, HttpServletResponse res, ServletContext servletContext,
                         HttpContext httpContext) {
        this.req = req;
        this.res = res;
        this.servletContext = servletContext;
        this.httpContext = httpContext;
        this.reqHeaders = new ExchangeRequestHeaders(req);
        this.resHeaders = new ExchangeResponseHeaders(res);
    }

    @Override
    public Map<String, List<String>> getRequestHeaders() {
        return reqHeaders;
    }

    @Override
    public Map<String, List<String>> getResponseHeaders() {
        return resHeaders;
    }

    @Override
    public String getRequestURI() {
        return req.getRequestURI();
    }

    @Override
    public String getContextPath() {
        return req.getContextPath();
    }

    @Override
    public String getRequestMethod() {
        return req.getMethod();
    }

    @Override
    public HttpContext getHttpContext() {
        return httpContext;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public String getRequestHeader(String name) {
        return reqHeaders.getFirst(name);
    }

    @Override
    public void addResponseHeader(String name, String value) {
        resHeaders.add(name, value);
    }

    @Override
    public InputStream getRequestBody() throws IOException {
        return req.getInputStream();
    }

    @Override
    public OutputStream getResponseBody() throws IOException {
        return res.getOutputStream();
    }

    @Override
    public void setStatus(int rCode) {
        res.setStatus(rCode);
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
        // Only from 2.4
        // return InetSocketAddress.createUnresolved(req.getRemoteAddr(), req.getRemotePort());
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return InetSocketAddress.createUnresolved(req.getServerName(), req.getServerPort());
    }

    @Override
    public String getProtocol() {
        return req.getProtocol();
    }

    @Override
    public Object getAttribute(String name) {
        if (name.equals(MessageContext.SERVLET_CONTEXT)) {
            return servletContext;
        } else if (name.equals(MessageContext.SERVLET_REQUEST)) {
            return req;
        } else if (name.equals(MessageContext.SERVLET_RESPONSE)) {
            return res;
        }
        return null;
    }

    @Override
    public Set<String> getAttributeNames() {
        return attributes;
    }

    @Override
    public Principal getUserPrincipal() {
        return req.getUserPrincipal();
    }

    @Override
    public boolean isUserInRole(String role) {
        return req.isUserInRole(role);
    }

    @Override
    public String getScheme() {
        return req.getScheme();
    }

    @Override
    public String getPathInfo() {
        return req.getPathInfo();
    }

    @Override
    public String getQueryString() {
        return req.getQueryString();
    }
}
