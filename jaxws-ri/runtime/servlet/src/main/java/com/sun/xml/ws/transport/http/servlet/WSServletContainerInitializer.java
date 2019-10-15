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

import javax.jws.WebService;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import javax.xml.ws.WebServiceProvider;
import java.net.URL;
import java.util.Set;

/**
 * @author Rama Pulavarthi
 */
@HandlesTypes({WebService.class, WebServiceProvider.class})
public class WSServletContainerInitializer implements ServletContainerInitializer {
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        //Called with null, when there are no matching classes as per Servlet 3.0 spec
        try {
            if (c != null && !c.isEmpty()) {
                URL sunJaxWsXml = ctx.getResource(WSServletContextListener.JAXWS_RI_RUNTIME);
                //Don't register a listener, when there is no sun-jaxws.xml, let 109 impl  handle it.
                if (sunJaxWsXml != null) {
                    WSServletContextListener listener = new WSServletContextListener();
                    listener.parseAdaptersAndCreateDelegate(ctx);
                    ctx.addListener(listener);
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
