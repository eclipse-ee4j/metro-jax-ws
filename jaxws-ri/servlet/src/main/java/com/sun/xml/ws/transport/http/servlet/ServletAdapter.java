/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.servlet;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
import com.sun.xml.ws.api.ha.StickyFeature;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.server.BoundEndpoint;
import com.sun.xml.ws.api.server.Module;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.api.server.WebModule;
import com.sun.xml.ws.transport.http.HttpAdapter;
import com.sun.xml.ws.transport.http.WSHTTPConnection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link HttpAdapter} for servlets.
 *
 * <p>
 * This is a thin wrapper around {@link HttpAdapter} with some description
 * specified in the deployment (in particular those information are related
 * to how a request is routed to a {@link ServletAdapter}.
 *
 * <p>
 * This class implements {@link BoundEndpoint} and represent the
 * servlet-{@link WSEndpoint} association for {@link }
 *
 */
public class ServletAdapter extends HttpAdapter implements BoundEndpoint {
    final String name;

    @SuppressWarnings("unchecked")
	protected ServletAdapter(String name, String urlPattern, WSEndpoint endpoint, ServletAdapterList owner) {
        super(endpoint, owner, urlPattern);
        this.name = name;
        // registers itself with the container
        Module module = endpoint.getContainer().getSPI(Module.class);
        if (module == null) {
            LOGGER.log(Level.WARNING, "Container {0} doesn''t support {1}", new Object[]{endpoint.getContainer(), Module.class});
        } else {
            module.getBoundEndpoints().add(this);
        }

        boolean sticky = false;
        if (HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured()) {
            WebServiceFeature[] features = endpoint.getBinding().getFeatures().toArray();
            for(WebServiceFeature f : features) {
                if (f instanceof StickyFeature) {
                    sticky = true;
                    break;
                }
            }
            disableJreplicaCookie = HighAvailabilityProvider.INSTANCE.isDisabledJreplica();
        }
        stickyCookie = sticky;
    }

    public ServletContext getServletContext() {
        return ((ServletAdapterList)owner).getServletContext();
    }

    /**
     * Gets the name of the endpoint as given in the <code>sun-jaxws.xml</code>
     * deployment descriptor.
     */
    public String getName() {
        return name;
    }


    @NotNull
    @Override
    public URI getAddress() {
        WebModule webModule = endpoint.getContainer().getSPI(WebModule.class);
        if(webModule==null) {
            throw new WebServiceException("Container "+endpoint.getContainer()+" doesn't support "+WebModule.class);
        }

        return getAddress(webModule.getContextPath());
    }

    @Override
    @NotNull
    public URI getAddress(String baseAddress) {
        String adrs = baseAddress+getValidPath();
        try {
            return new URI(adrs);
        } catch (URISyntaxException e) {
            // this is really a bug in the container implementation
            throw new WebServiceException("Unable to compute address for "+endpoint,e);
        }
    }

    /**
     * Convenient method to return a port name from {@link WSEndpoint}.
     *
     * @return
     *      null if {@link WSEndpoint} isn't tied to any paritcular port.
     */
    public QName getPortName() {
        WSDLPort port = getEndpoint().getPort();
        if (port == null) {
            return null;
        } else {
            return port.getName();
        }
    }

    /**
     * Version of {@link #handle(WSHTTPConnection)}
     * that takes convenient parameters for servlet.
     *
     * @param context Servlet Context
     * @param request Servlet Request
     * @param response Servlet Response
     * @throws IOException when there is i/o error in handling request
     */
    public void handle(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.handle(createConnection(context, request, response));
    }
    
    protected WSHTTPConnection createConnection(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
    	return new ServletConnectionImpl(this,context,request,response);
    }

    /**
     * Version of {@link #handle(WSHTTPConnection)}  that takes convenient parameters for servlet.
     *
     * Based on the async capabilities of the request and the application processing it, the method may run in asynchronous mode.
     * When run in async mode, this method returns immediately. The response is delayed until the application is ready with the response or
     *  the corresponding asynchronous operation times out. The CompletionCallback is guaranteed to run after response is committed..
     *
     * @param context Servlet Context
     * @param request Servlet Request
     * @param response Servlet Response
     * @param callback CompletionCallback
     * @throws IOException when there is i/o error in handling request
     */
    public void invokeAsync(ServletContext context, HttpServletRequest request, HttpServletResponse response, final CompletionCallback callback) throws IOException {
        boolean asyncStarted = false;
        try {
            WSHTTPConnection connection = new ServletConnectionImpl(this, context, request, response);
            if (handleGet(connection)) {
                return;
            }

            boolean asyncRequest = false;
            try {
                asyncRequest = isServlet30Based && request.isAsyncSupported() && !request.isAsyncStarted();
            } catch (Throwable t) {
                //this happens when the loaded Servlet API is 3.0, but the impl is not, ending up as AbstractMethodError
                LOGGER.log(Level.INFO, request.getClass().getName() + " does not support Async API, Continuing with synchronous processing", t);
                //Continue with synchronous processing and don't repeat the check for processing further requests
                isServlet30Based = false;
            }

            if (asyncRequest) {
                final javax.servlet.AsyncContext asyncContext = request.startAsync(request, response);
                final AsyncCompletionCheck completionCheck = new AsyncCompletionCheck();
                new WSAsyncListener(connection, callback).addListenerTo(asyncContext,completionCheck);
                //asyncContext.setTimeout(10000L);// TODO get it from @ or config file
                super.invokeAsync(connection, new CompletionCallback() {
                    @Override
                    public void onCompletion() {
                        synchronized (completionCheck) {
                            if(!completionCheck.isCompleted()) {
                                asyncContext.complete();
                                completionCheck.markComplete();
                            }
                        }
                    }
                });
                asyncStarted = true;
            } else {
                super.handle(connection);
            }
        } finally {
            if (!asyncStarted) {
                callback.onCompletion();
            }
        }
    }

    /**
     * Synchronizes the CompletionHandler action and Container's timeout action.
     */
    static class AsyncCompletionCheck {
        boolean completed = false;
        synchronized void markComplete() {
            completed = true;
        }

        synchronized boolean isCompleted() {
            return completed;
        }
    }
    
    /**
     * @param context Servlet Context
     * @param request Servlet Request
     * @param response Servlet Response
     * @throws IOException when there is i/o error in handling request
     *
     * @deprecated
     *      Use {@link #handle(ServletContext, HttpServletRequest, HttpServletResponse)}
     */
    public void publishWSDL(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {
        WSHTTPConnection connection = new ServletConnectionImpl(this,context,request,response);
        super.handle(connection);
    }

    @Override
    public String toString() {
        return super.toString()+"[name="+name+']';
    }

    private static final Logger LOGGER = Logger.getLogger(ServletAdapter.class.getName());

    private boolean isServlet30Based = ServletUtil.isServlet30Based();

}
