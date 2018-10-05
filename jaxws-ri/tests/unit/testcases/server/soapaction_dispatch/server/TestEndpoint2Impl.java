/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.soapaction_dispatch.server;

import javax.jws.WebService;
import javax.xml.ws.BindingType;


/**
 * @author Rama Pulavarthi
 */
@WebService(portName = "TestEndpointPort2", targetNamespace = "http://server.soapaction_dispatch.server/", serviceName="TestEndpoint2Service",
        endpointInterface = "server.soapaction_dispatch.server.TestEndpoint")
@BindingType("http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
public class TestEndpoint2Impl {
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
