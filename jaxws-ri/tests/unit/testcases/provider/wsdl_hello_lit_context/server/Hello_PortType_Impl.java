/*
 * Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.wsdl_hello_lit_context.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.WebServiceException;
import jakarta.jws.HandlerChain;

/**
 * @author Jitendra Kotamraju
 */
@WebServiceProvider(serviceName="Hello", portName="HelloPort",
    targetNamespace="urn:test")
@HandlerChain(file="handlers.xml")
public class Hello_PortType_Impl extends ProviderImpl {
    @Resource(type=Object.class)
    private  WebServiceContext wsContextViaField;

    private  WebServiceContext wsContextViaMethod;

    private boolean injectionDone;

    @PostConstruct
    public void over() {
        System.out.println("PostConstruct Complete");
        if (wsContextViaField == null) {
            throw new WebServiceException("wsContextViaField injection is not done");
        }
        if (wsContextViaMethod == null) {
            throw new WebServiceException("wsContextViaMethod injection is not done");
        }
        if (wsContextViaBaseField == null) {
            throw new WebServiceException("wsContextViaBaseField injection is not done");
        }
        if (wsContextViaBaseMethod == null) {
            throw new WebServiceException("wsContextViaBaseMethod injection is not done");
        }
        injectionDone = true;
        boolean illegal = false;
        try {
        	wsContextViaField.getMessageContext();
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
        	wsContextViaMethod.getMessageContext();
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

    @Resource
    public void setMyContext(WebServiceContext ctxt) {
        this.wsContextViaMethod = ctxt;
    }
}
