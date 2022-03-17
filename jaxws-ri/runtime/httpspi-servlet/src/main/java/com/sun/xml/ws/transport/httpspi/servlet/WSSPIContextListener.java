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

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.xml.ws.WebServiceException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Parses {@code sun-jaxws.xml} and sets up
 * {@link com.sun.xml.ws.transport.httpspi.servlet.EndpointAdapter}s for all deployed endpoints.
 *
 * <p>
 * This code is the entry point at the server side in the servlet deployment.
 * The user application writes this in their <code>web.xml</code> so that we can
 * start when the container starts the webapp.
 *
 * @author Jitendra Kotamraju
 */
public final class WSSPIContextListener
    implements ServletContextAttributeListener, ServletContextListener {

    private WSServletDelegate delegate;

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        if (delegate != null) { // the deployment might have failed.
            delegate.destroy();
        }

        if (logger.isLoggable(Level.INFO)) {
            logger.info("JAX-WS context listener destroyed");
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info("JAX-WS context listener initializing");
        }
        ServletContext context = event.getServletContext();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }
        try {
            // Parse the descriptor file and build endpoint infos
            DeploymentDescriptorParser<EndpointAdapter> parser = new DeploymentDescriptorParser<>(
                    classLoader, new ServletResourceLoader(context), new EndpointAdapterFactory());
            URL sunJaxWsXml = context.getResource(JAXWS_RI_RUNTIME);
            if(sunJaxWsXml==null)
                throw new WebServiceException("Runtime descriptor "+JAXWS_RI_RUNTIME+" is mising");
            List<EndpointAdapter> adapters = parser.parse(sunJaxWsXml.toExternalForm(), sunJaxWsXml.openStream());
            for(EndpointAdapter adapter : adapters) {
                adapter.publish();
            }

            delegate = createDelegate(adapters, context);

            context.setAttribute(WSSPIServlet.JAXWS_RI_RUNTIME_INFO,delegate);

        } catch (Throwable e) {
            logger.log(Level.SEVERE, "failed to parse runtime descriptor", e);
            context.removeAttribute(WSSPIServlet.JAXWS_RI_RUNTIME_INFO);
            throw new WebServiceException("failed to parse runtime descriptor", e);
        }
    }

    /**
     * Creates {@link com.sun.xml.ws.transport.httpspi.servlet.WSServletDelegate} that does the real work.
     */
    protected WSServletDelegate createDelegate(List<EndpointAdapter> adapters, ServletContext context) {
        return new WSServletDelegate(adapters,context);
    }

    private static final String JAXWS_RI_RUNTIME = "/WEB-INF/sun-jaxws.xml";

    private static final Logger logger =
        Logger.getLogger(WSSPIContextListener.class.getName());

}
