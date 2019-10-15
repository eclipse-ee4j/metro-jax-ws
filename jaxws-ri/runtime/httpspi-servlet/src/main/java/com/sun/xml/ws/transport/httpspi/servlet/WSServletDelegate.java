/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.httpspi.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Binding;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.http.HTTPBinding;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Called by {@link WSSPIServlet} to choose {@link EndpointAdapter}
 * and sends a request to it.
 *
 * <p>
 * One instance of this object is created, and then shared across
 * {@link WSSPIServlet} instances (the container might deploy many of them,
 * depending on how the user writes {@code web.xml}.)
 *
 * @author Jitendra Kotamraju
 */
public class WSServletDelegate {

    /**
     * All {@link EndpointAdapter}s that are deployed in the current web application.
     */
    public final List<EndpointAdapter> adapters;

    private final Map<String, EndpointAdapter> fixedUrlPatternEndpoints = new HashMap<String, EndpointAdapter>();
    private final List<EndpointAdapter> pathUrlPatternEndpoints = new ArrayList<EndpointAdapter>();

    public WSServletDelegate(List<EndpointAdapter> adapters, ServletContext context) {
        this.adapters = adapters;

        for(EndpointAdapter info : adapters) {
            registerEndpointUrlPattern(info);
        }

        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Initializing Servlet for {0}", fixedUrlPatternEndpoints);
        }

    }

    public void destroy() {
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Destroying Servlet for {0}", fixedUrlPatternEndpoints);
        }

        for(EndpointAdapter a : adapters) {
            try {
                a.dispose();
            } catch(Throwable e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext context) {

        try {
            EndpointAdapter target = getTarget(request);
            if (target != null) {
                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Got request for endpoint {0}", target.getUrlPattern());
                }
                target.handle(context, request, response);
            } else {
                writeNotFoundErrorPage(response, "Invalid Request");
            }
        } catch (WebServiceException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "caught throwable", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * processes web service requests by finding the {@link EndpointAdapter}
     * created by the {@link WSSPIContextListener}
     *
     * @param request the HTTP request object
     * @param response the HTTP response object
     * @param context servlet context
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
        doGet(request, response,context);
    }

    /**
     * Handles HTTP PUT for XML/HTTP binding based endpoints
     *
     * @param request the HTTP request object
     * @param response the HTTP response object
     * @param context servlet context
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
        // TODO: unify this into doGet.
        try {
            EndpointAdapter target = getTarget(request);
            if (target != null) {
                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Got request for endpoint {0}", target.getUrlPattern());
                }
            } else {
                writeNotFoundErrorPage(response, "Invalid request");
                return;
            }
            Binding binding = target.getEndpoint().getBinding();
            if (binding instanceof HTTPBinding) {
                target.handle(context, request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        } catch (WebServiceException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "caught throwable", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Handles HTTP DELETE for XML/HTTP binding based endpoints
     *
     * @param request the HTTP request object
     * @param response the HTTP response object
     * @param context servlet context
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response, ServletContext context) {

        // At preseent, there is no difference for between PUT and DELETE processing
        doPut(request, response, context);
    }


    private void writeNotFoundErrorPage(
        HttpServletResponse response,
        String message)
        throws IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>");
        out.println("Web Services");
        out.println("</title></head>");
        out.println("<body>");
        out.println("Not found "+message);
        out.println("</body>");
        out.println("</html>");
    }

    private void registerEndpointUrlPattern(EndpointAdapter a) {
        String urlPattern = a.getUrlPattern();
        if (urlPattern.indexOf("*.") != -1) {
            // cannot deal with implicit mapping right now
            logger.log(Level.WARNING, "Ignoring implicit url-pattern {0}", urlPattern);
        } else if (urlPattern.endsWith("/*")) {
            pathUrlPatternEndpoints.add(a);
        } else {
            if (fixedUrlPatternEndpoints.containsKey(urlPattern)) {
                logger.log(Level.WARNING, "Ignoring duplicate url-pattern {0}", urlPattern);
            } else {
                fixedUrlPatternEndpoints.put(urlPattern, a);
            }
        }
    }

    /**
     * Determines which {@link EndpointAdapter} serves the given request.
     *
     * @param request the HTTP request object
     */
    protected EndpointAdapter getTarget(HttpServletRequest request) {

        String path =
            request.getRequestURI().substring(
                request.getContextPath().length());
        EndpointAdapter result = fixedUrlPatternEndpoints.get(path);
        if (result == null) {
            for (EndpointAdapter candidate : pathUrlPatternEndpoints) {
                String noSlashStar = candidate.getValidPath();
                if (path.equals(noSlashStar) || path.startsWith(noSlashStar+"/") || path.startsWith(noSlashStar+"?")) {
                    result = candidate;
                    break;
                }
            }
        }

        return result;
    }

    private static final Logger logger =
        Logger.getLogger(WSServletDelegate.class.getName());

}
