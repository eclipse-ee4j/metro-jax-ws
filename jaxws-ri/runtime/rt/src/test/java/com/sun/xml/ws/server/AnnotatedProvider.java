/*
 * Copyright (c) 2026 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import jakarta.jws.HandlerChain;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.ServiceMode;
import jakarta.xml.ws.WebServiceProvider;
import javax.xml.transform.Source;

/**
 * Endpoint implementor declaring a handler chain through {@code @HandlerChain}, used to
 * verify how that annotation interacts with a chain configured on the binding beforehand.
 * Must be public with a public no-arg constructor so that the default
 * {@code InstanceResolver} can instantiate it.
 */
@WebServiceProvider(targetNamespace = "http://server.ws.xml.sun.com/", serviceName = "AnnotatedProviderService", portName = "AnnotatedProviderPort")
@ServiceMode(value = Service.Mode.PAYLOAD)
@HandlerChain(file = "test-handler-chain.xml")
public class AnnotatedProvider implements Provider<Source> {

    @Override
    public Source invoke(Source request) {
        return request;
    }
}
