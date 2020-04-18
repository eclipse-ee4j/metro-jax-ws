/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsimport.extensibilityEl.server;

import java.util.List;

/**
 * @author Rama Pulavarthi
 *  This is a simple test that has extensibility elements at various points in the wsdl to test if RI accepots such wsdls.
 */
@jakarta.jws.WebService(endpointInterface = "wsimport.extensibilityEl.server.Hello")
public class TestEndpointImpl implements Hello {
    public HelloResponse hello(Hello_Type req) {
        System.out.println("Hello_PortType_Impl received: " + req.getArgument() +
                ", " + req.getExtra());
        HelloResponse resp = new HelloResponse();
        resp.setName("vivek");
        resp.setArgument(req.getArgument());
        resp.setExtra(req.getExtra());
        return resp;
    }    
}
