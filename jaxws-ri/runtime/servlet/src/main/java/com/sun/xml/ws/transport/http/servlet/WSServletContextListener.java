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

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.resources.WsservletMessages;
import com.sun.xml.ws.transport.http.DeploymentDescriptorParser;
import com.sun.xml.ws.transport.http.HttpAdapter;

import jakarta.servlet.*;
import jakarta.xml.ws.WebServiceException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Parses {@code sun-jaxws.xml} and sets up
 * {@link HttpAdapter}s for all deployed endpoints.
 *
 * <p>
 * This code is the entry point at the server side in the servlet deployment.
 * The user application writes this in their <code>web.xml</code> so that we can
 * start when the container starts the webapp.
 *
 * @author WS Development Team
 */
public final class WSServletContextListener
    implements ServletContextAttributeListener, ServletContextListener {

    private WSServletDelegate delegate;
    private List<ServletAdapter> adapters;
    private final JAXWSRIDeploymentProbeProvider probe = new JAXWSRIDeploymentProbeProvider();

    private static final String WSSERVLET_CONTEXT_LISTENER_INVOKED="com.sun.xml.ws.transport.http.servlet.WSServletContextListener.Invoked";

    public void attributeAdded(ServletContextAttributeEvent event) {
    }

    public void attributeRemoved(ServletContextAttributeEvent event) {
    }

    public void attributeReplaced(ServletContextAttributeEvent event) {
    }

    public void contextDestroyed(ServletContextEvent event) {
        if (delegate != null) { // the deployment might have failed.
            delegate.destroy();
        }

        if (adapters != null) {

            for(ServletAdapter a : adapters) {
                try {
                    a.getEndpoint().dispose();
                } catch(Throwable e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }

                // Emit undeployment probe event for each endpoint
                probe.undeploy(a);
            }
        }

        if (logger.isLoggable(Level.INFO)) {
            logger.info(WsservletMessages.LISTENER_INFO_DESTROY());
        }
    }

    void parseAdaptersAndCreateDelegate(ServletContext context){
        //The same class can be invoked via @WebListener discovery or explicit configuration in deployment descriptor
        // avoid redoing the processing of web services.
        String alreadyInvoked = (String) context.getAttribute(WSSERVLET_CONTEXT_LISTENER_INVOKED);
        if(Boolean.valueOf(alreadyInvoked)) {
            return;
        }
        context.setAttribute(WSSERVLET_CONTEXT_LISTENER_INVOKED, "true");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }
        try {
            URL sunJaxWsXml = context.getResource(JAXWS_RI_RUNTIME);
            if(sunJaxWsXml==null) {
                    throw new WebServiceException(WsservletMessages.NO_SUNJAXWS_XML(JAXWS_RI_RUNTIME));                 
            }

            // Parse the descriptor file and build endpoint infos
            DeploymentDescriptorParser<ServletAdapter> parser = new DeploymentDescriptorParser<ServletAdapter>(
                classLoader,new ServletResourceLoader(context), createContainer(context), new ServletAdapterList(context));
            adapters = parser.parse(sunJaxWsXml.toExternalForm(), sunJaxWsXml.openStream());
            registerWSServlet(adapters, context);
            delegate = createDelegate(adapters, context);

            context.setAttribute(WSServlet.JAXWS_RI_RUNTIME_INFO,delegate);

        } catch (Throwable e) {
            logger.log(Level.SEVERE,
                WsservletMessages.LISTENER_PARSING_FAILED(e),e);
            context.removeAttribute(WSServlet.JAXWS_RI_RUNTIME_INFO);
            throw new WSServletException("listener.parsingFailed", e);
        }

    }

    public void contextInitialized(ServletContextEvent event) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(WsservletMessages.LISTENER_INFO_INITIALIZE());
        }
        ServletContext context = event.getServletContext();

        parseAdaptersAndCreateDelegate(context);
        if(adapters != null)  {
            // Emit deployment probe event for each endpoint
            for (ServletAdapter adapter : adapters) {
                probe.deploy(adapter);
            }
        }
    }

    private void registerWSServlet(List<ServletAdapter> adapters, ServletContext context) {
        if ( !ServletUtil.isServlet30Based())
            return;
        Set<String> unregisteredUrlPatterns = new HashSet<String>();
        try {
            Collection<? extends ServletRegistration> registrations = context.getServletRegistrations().values();
            for (ServletAdapter adapter : adapters) {
                if (!existsServletForUrlPattern(adapter.urlPattern, registrations)) {
                    unregisteredUrlPatterns.add(adapter.urlPattern);
                }
            }
            if (!unregisteredUrlPatterns.isEmpty()) {
                //register WSServlet Dynamically
                ServletRegistration.Dynamic registration = context.addServlet("Dynamic JAXWS Servlet", WSServlet.class);
                registration.addMapping(unregisteredUrlPatterns.toArray(new String[unregisteredUrlPatterns.size()]));
                registration.setAsyncSupported(true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean existsServletForUrlPattern(String urlpattern, Collection<? extends ServletRegistration> registrations) {
        for (ServletRegistration r : registrations) {
            if (r.getMappings().contains(urlpattern)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates {@link Container} implementation that hosts the JAX-WS endpoint.
     * @param context the Servlet context object
     * @return {@link Container} implementation that hosts the JAX-WS endpoint
     */
    protected @NotNull Container createContainer(ServletContext context) {
        return new ServletContainer(context);
    }

    /**
     * Creates {@link WSServletDelegate} that does the real work.
     * @param adapters adapters
     * @param context the Servlet context object
     * @return {@link WSServletDelegate} that does the real work
     */
    protected @NotNull WSServletDelegate createDelegate(List<ServletAdapter> adapters, ServletContext context) {
        return new WSServletDelegate(adapters,context);
    }

    static final String JAXWS_RI_RUNTIME = "/WEB-INF/sun-jaxws.xml";

    private static final Logger logger =
        Logger.getLogger(
            com.sun.xml.ws.util.Constants.LoggingDomain + ".server.http");
}
