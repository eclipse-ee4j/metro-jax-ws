/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.epr_extensions.server;

import jakarta.jws.WebService;
import jakarta.annotation.Resource;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.wsaddressing.W3CEndpointReference;

/**
 * @author Rama Pulavarthi
 */
@WebService(endpointInterface = "epr.epr_extensions.server.Hello")
public class HelloImpl implements Hello {
    @Resource
    WebServiceContext wsContext;

    public HelloResponse hello(HelloRequest parameters) {
        //dummy impl
        return null;
    }

    public W3CEndpointReference getW3CEPR() {
        return (W3CEndpointReference) wsContext.getEndpointReference();
    }
}
