/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.httpspi.servlet;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The JAX-WS dispatcher servlet.
 *
 * <p>
 * It really just forwards processing to {@link com.sun.xml.ws.transport.httpspi.servlet.WSServletDelegate}.
 *
 * @author Jitendra Kotamraju
 */
public class WSSPIServlet extends HttpServlet {

    private static final long serialVersionUID = -7349233037950585585L;

    private transient WSServletDelegate delegate = null;

    private static final Logger LOGGER = Logger.getLogger(WSSPIServlet.class.getName());
    
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        delegate = getDelegate(servletConfig);
    }

    /**
     * Gets the {@link com.sun.xml.ws.transport.httpspi.servlet.WSServletDelegate} that we will be forwarding the requests to.
     *
     * @return
     *      null if the deployment have failed and we don't have the delegate.
     */
    protected WSServletDelegate getDelegate(ServletConfig servletConfig) {
        return (WSServletDelegate) servletConfig.getServletContext().getAttribute(JAXWS_RI_RUNTIME_INFO);
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (delegate != null) {
            delegate.doPost(request,response,getServletContext());
        } else {
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.log(Level.INFO, "No delegate for {0} to invoke post method.", this);
            }
        }
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
        if (delegate != null) {
            delegate.doGet(request,response,getServletContext());
        } else {
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.log(Level.INFO, "No delegate for {0} to invoke get method.", this);
            }
        }
    }

    @Override
    protected void doPut( HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
        if (delegate != null) {
            delegate.doPut(request,response,getServletContext());
        } else {
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.log(Level.INFO, "No delegate for {0} to invoke put method.", this);
            }
        }
    }

    @Override
    protected void doDelete( HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
        if (delegate != null) {
            delegate.doDelete(request,response,getServletContext());
        } else {
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.log(Level.INFO, "No delegate for {0} to invoke delete method.", this);
            }
        }
    }

    /**
     * {@link com.sun.xml.ws.transport.httpspi.servlet.WSServletDelegate}.
     */
    public static final String JAXWS_RI_RUNTIME_INFO =
        "com.sun.xml.ws.server.http.servletDelegate";
    public static final String JAXWS_RI_PROPERTY_PUBLISH_WSDL =
        "com.sun.xml.ws.server.http.publishWSDL";
    public static final String JAXWS_RI_PROPERTY_PUBLISH_STATUS_PAGE =
        "com.sun.xml.ws.server.http.publishStatusPage";

}
