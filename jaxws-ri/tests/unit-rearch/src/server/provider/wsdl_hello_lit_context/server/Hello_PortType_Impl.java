/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.wsdl_hello_lit_context.server;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.WebServiceException;

@WebServiceProvider(
    wsdlLocation="WEB-INF/wsdl/hello_literal_overridden.wsdl")
public class Hello_PortType_Impl extends ProviderImpl {
    @Resource(type=Object.class)
    protected WebServiceContext wsContext;

    private boolean injectionDone;

    public WebServiceContext getContext() {
        return wsContext;
    }

    @PostConstruct
    public void over() {
        System.out.println("PostConstruct Complete");
        injectionDone = true;
        boolean illegal = false;
        try {
        	wsContext.getMessageContext();
        } catch(IllegalStateException ie) {
			// No op. Expected to get this exception
            illegal = true;
        }
        if (!illegal) {
            throw new WebServiceException("IllegalStateException is not called");
        }
    }

    @PreDestroy
    public void destroy() {
        System.out.println("PreDestroy is called");
        boolean illegal = false;
        try {
        	wsContext.getMessageContext();
        } catch(IllegalStateException ie) {
			// No op. Expected to get this exception
            illegal = true;
        }
        if (!illegal) {
            throw new WebServiceException("IllegalStateException is not called");
        }
    }

    public boolean isInjectionDone() {
        return injectionDone;
    }
}
