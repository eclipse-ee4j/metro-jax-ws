/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.servlet;

import com.sun.istack.localization.Localizable;
import com.sun.istack.localization.Localizer;
import com.sun.xml.ws.resources.WsservletMessages;
import com.sun.xml.ws.transport.http.HttpAdapter;
import com.sun.xml.ws.util.exception.JAXWSExceptionBase;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.ws.Binding;
import jakarta.xml.ws.http.HTTPBinding;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Called by {@link WSServlet} to choose {@link HttpAdapter}
 * and sends a request to it.
 *
 * <p>
 * One instance of this object is created, and then shared across
 * {@link WSServlet} instances (the container might deploy many of them,
 * depending on how the user writes {@code web.xml}.)
 *
 * @author WS Development Team
 */
public class WSServletDelegate {

    /**
     * All {@link ServletAdapter}s that are deployed in the current web application.
     */
    public final List<ServletAdapter> adapters;

    private final Map<String, ServletAdapter> fixedUrlPatternEndpoints = new HashMap<String, ServletAdapter>();
    private final List<ServletAdapter> pathUrlPatternEndpoints = new ArrayList<ServletAdapter>();
    private final Map<Locale,Localizer> localizerMap = new HashMap<Locale,Localizer>();
    private final JAXWSRIServletProbeProvider probe = new JAXWSRIServletProbeProvider();

    public WSServletDelegate(List<ServletAdapter> adapters, ServletContext context) {
        this.adapters = adapters;

        for(ServletAdapter info : adapters) {
            registerEndpointUrlPattern(info);
        }

        localizerMap.put(defaultLocalizer.getLocale(), defaultLocalizer);

        if (logger.isLoggable(Level.INFO)) {
            logger.info(WsservletMessages.SERVLET_INFO_INITIALIZE());
        }

        // compatibility.
        String publishStatusPageParam =
            context.getInitParameter(WSServlet.JAXWS_RI_PROPERTY_PUBLISH_STATUS_PAGE);
        if (publishStatusPageParam != null) {
            HttpAdapter.setPublishStatus(Boolean.parseBoolean(publishStatusPageParam));
        }
    }

    public void destroy() {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(WsservletMessages.SERVLET_INFO_DESTROY());
        }
    }

    public void doHead(HttpServletRequest request, HttpServletResponse response, ServletContext context)
        throws ServletException {

        try {
            ServletAdapter target = getTarget(request);
            if (target != null) {
                if (logger.isLoggable(Level.FINEST)) {
                    logger.finest(
                        WsservletMessages.SERVLET_TRACE_GOT_REQUEST_FOR_ENDPOINT(target.name));
                }
                target.handle(context, request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (JAXWSExceptionBase e) {
            logger.log(Level.SEVERE, defaultLocalizer.localize(e), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Throwable e) {
            if (e instanceof Localizable) {
                logger.log(
                    Level.SEVERE,
                    defaultLocalizer.localize((Localizable) e),
                    e);
            } else {
                logger.log(Level.SEVERE, "caught throwable", e);
            }

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext context)
        throws ServletException {

        try {
            ServletAdapter target = getTarget(request);
            if (target != null) {
                if (logger.isLoggable(Level.FINEST)) {
                    logger.finest(
                        WsservletMessages.SERVLET_TRACE_GOT_REQUEST_FOR_ENDPOINT(target.name));
                }
                final String path = request.getContextPath()+target.getValidPath();
                probe.startedEvent(path);

                target.invokeAsync(context, request, response, new HttpAdapter.CompletionCallback() {
                    @Override
                    public void onCompletion() {
                        probe.endedEvent(path);
                    }
                });
            } else {
                Localizer localizer = getLocalizerFor(request);
                writeNotFoundErrorPage(localizer, response, "Invalid Request");
            }
        } catch (JAXWSExceptionBase e) {
            logger.log(Level.SEVERE, defaultLocalizer.localize(e), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Throwable e) {
            if (e instanceof Localizable) {
                logger.log(
                    Level.SEVERE,
                    defaultLocalizer.localize((Localizable) e),
                    e);
            } else {
                logger.log(Level.SEVERE, "caught throwable", e);
            }

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * processes web service requests by finding the {@link ServletAdapter}
     * created by the {@link WSServletContextListener} and creating a
     * {@link ServletConnectionImpl}.
     *
     * @param request the HTTP request object
     * @param response the HTTP response object
     * @param context the Servlet context object
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException {
        doGet(request, response,context);
    }

    /**
     * Handles HTTP PUT for XML/HTTP binding based endpoints
     * @param request the HTTP request object
     * @param response the HTTP response object
     * @param context the Servlet context object
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response, ServletContext context)
        throws ServletException {
        // TODO: unify this into doGet.
        try {
            ServletAdapter target = getTarget(request);
            if (target != null) {
                if (logger.isLoggable(Level.FINEST)) {
                    logger.finest(
                        WsservletMessages.SERVLET_TRACE_GOT_REQUEST_FOR_ENDPOINT(target.name));
                }
            } else {
                Localizer localizer = getLocalizerFor(request);
                writeNotFoundErrorPage(localizer, response, "Invalid request");
                return;
            }
            Binding binding = target.getEndpoint().getBinding();
            if (binding instanceof HTTPBinding) {
                target.handle(context, request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        } catch (JAXWSExceptionBase e) {
            logger.log(Level.SEVERE, defaultLocalizer.localize(e), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Throwable e) {
            if (e instanceof Localizable) {
                logger.log(
                    Level.SEVERE,
                    defaultLocalizer.localize((Localizable) e),
                    e);
            } else {
                logger.log(Level.SEVERE, "caught throwable", e);
            }
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Handles HTTP DELETE for XML/HTTP binding based endpoints
     * @param request the HTTP request object
     * @param response the HTTP response object
     * @param context the Servlet context object
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response, ServletContext context)
        throws ServletException {

        // At preseent, there is no difference for between PUT and DELETE processing
        doPut(request, response, context);
    }


    private void writeNotFoundErrorPage(
        Localizer localizer,
        HttpServletResponse response,
        String message)
        throws IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>");
        out.println(WsservletMessages.SERVLET_HTML_TITLE());
        out.println("</title></head>");
        out.println("<body>");
        out.println(WsservletMessages.SERVLET_HTML_NOT_FOUND(message));
        out.println("</body>");
        out.println("</html>");
    }

    private void registerEndpointUrlPattern(ServletAdapter a) {
        String urlPattern = a.urlPattern;
        if (urlPattern.indexOf("*.") != -1) {
            // cannot deal with implicit mapping right now
            logger.warning(
                WsservletMessages.SERVLET_WARNING_IGNORING_IMPLICIT_URL_PATTERN(a.name));
        } else if (urlPattern.endsWith("/*")) {
            pathUrlPatternEndpoints.add(a);
        } else {
            if (fixedUrlPatternEndpoints.containsKey(urlPattern)) {
                logger.warning(
                    WsservletMessages.SERVLET_WARNING_DUPLICATE_ENDPOINT_URL_PATTERN(a.name));
            } else {
                fixedUrlPatternEndpoints.put(urlPattern, a);
            }
        }
    }

    /**
     * Determines which {@link ServletAdapter} serves the given request.
     * @param request request
     * @return the adapter
     */
    protected ServletAdapter getTarget(HttpServletRequest request) {

        String path =
            request.getRequestURI().substring(
                request.getContextPath().length());
        ServletAdapter result = fixedUrlPatternEndpoints.get(path);
        if (result == null) {
            for (ServletAdapter candidate : pathUrlPatternEndpoints) {
                String noSlashStar = candidate.getValidPath();
                if (path.equals(noSlashStar) || path.startsWith(noSlashStar+"/") || path.startsWith(noSlashStar+"?")) {
                    result = candidate;
                    break;
                }
            }
        }

        return result;
    }

    protected Localizer getLocalizerFor(ServletRequest request) {
        Locale locale = request.getLocale();
        if (locale.equals(defaultLocalizer.getLocale())) {
            return defaultLocalizer;
        }

        synchronized (localizerMap) {
            Localizer localizer = localizerMap.get(locale);
            if (localizer == null) {
                localizer = new Localizer(locale);
                localizerMap.put(locale, localizer);
            }
            return localizer;
        }
    }

    private static final Localizer defaultLocalizer = new Localizer();
    private static final Logger logger =
        Logger.getLogger(
            com.sun.xml.ws.util.Constants.LoggingDomain + ".servlet.http");

}
