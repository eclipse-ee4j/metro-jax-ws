/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.binding;

import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.client.HandlerConfiguration;
import com.sun.xml.ws.resources.ClientMessages;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.http.HTTPBinding;
import java.util.Collections;
import java.util.List;

/**
 * @author WS Development Team
 */
public class HTTPBindingImpl extends BindingImpl implements HTTPBinding {

    /**
     * Use {@link BindingImpl#create(BindingID)} to create this.
     */
    HTTPBindingImpl() {
        this(EMPTY_FEATURES);
    }

    HTTPBindingImpl(WebServiceFeature ... features) {
        super(BindingID.XML_HTTP, features);
    }

    /**
     * This method separates the logical and protocol handlers and
     * sets the HandlerConfiguration.
     * Only logical handlers are allowed with HTTPBinding.
     * Setting SOAPHandlers throws WebServiceException
     */
    public void setHandlerChain(List<Handler> chain) {
        for (Handler handler : chain) {
            if (!(handler instanceof LogicalHandler)) {
                throw new WebServiceException(ClientMessages.NON_LOGICAL_HANDLER_SET(handler.getClass()));
            }
        }
        setHandlerConfig(new HandlerConfiguration(Collections.<String>emptySet(), chain));
    }
}
