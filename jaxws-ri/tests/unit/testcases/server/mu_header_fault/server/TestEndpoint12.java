/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.mu_header_fault.server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.BindingType;


@WebService(portName ="TestEndpoint12Port",serviceName="TestEndpoint12Service" )
@BindingType(value="http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")

public class TestEndpoint12
{
    @WebMethod()
    public String echo(String s) {
        return s;
    }
}
