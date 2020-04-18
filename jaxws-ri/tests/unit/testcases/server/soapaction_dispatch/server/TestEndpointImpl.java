/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.soapaction_dispatch.server;

import jakarta.jws.WebService;


/**
 * @author Rama Pulavarthi
 */
@WebService(portName = "TestEndpointPort1", targetNamespace = "http://server.soapaction_dispatch.server/", serviceName="TestEndpointService",
        endpointInterface = "server.soapaction_dispatch.server.TestEndpoint")
public class TestEndpointImpl {
    public EchoResponse echo(Echo e){
        EchoResponse r = new EchoResponse();
        r.setReturn("Hello "+ e.getArg0());
        return r;
    }

    public EchoResponse echo1(Echo e){
        EchoResponse r = new EchoResponse();
        r.setReturn("Hello1 "+ e.getArg0());
        return r;
    }
}
