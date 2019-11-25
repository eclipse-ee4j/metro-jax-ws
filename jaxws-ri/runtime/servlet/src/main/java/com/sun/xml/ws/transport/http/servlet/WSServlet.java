/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.servlet;

import com.sun.istack.Nullable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The JAX-WS dispatcher servlet.
 *
 * <p>
 * It really just forwards processing to {@link WSServletDelegate}.
 *
 * @author WS Development Team
 */
public class WSServlet extends HttpServlet {
    private transient WSServletDelegate delegate = null;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        delegate = getDelegate(servletConfig);
    }

    /**
     * Gets the {@link WSServletDelegate} that we will be forwarding the requests to.
     *
     * @return
     *      null if the deployment have failed and we don't have the delegate.
     */
    protected @Nullable WSServletDelegate getDelegate(ServletConfig servletConfig) {
        return (WSServletDelegate) servletConfig.getServletContext().getAttribute(JAXWS_RI_RUNTIME_INFO);
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (delegate != null) {
            delegate.doPost(request,response,getServletContext());
        }
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
        if (delegate != null) {
            delegate.doGet(request,response,getServletContext());
        }
    }

    @Override
    protected void doPut( HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
        if (delegate != null) {
            delegate.doPut(request,response,getServletContext());
        }
    }

    @Override
    protected void doDelete( HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
        if (delegate != null) {
            delegate.doDelete(request,response,getServletContext());
        }
    }

    @Override
    protected void doHead( HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (delegate != null) {
            delegate.doHead(request,response,getServletContext());
        }
    }

    /**
     * {@link WSServletDelegate}.
     */
    public static final String JAXWS_RI_RUNTIME_INFO =
        "com.sun.xml.ws.server.http.servletDelegate";
    public static final String JAXWS_RI_PROPERTY_PUBLISH_WSDL =
        "com.sun.xml.ws.server.http.publishWSDL";
    public static final String JAXWS_RI_PROPERTY_PUBLISH_STATUS_PAGE =
        "com.sun.xml.ws.server.http.publishStatusPage";

}
