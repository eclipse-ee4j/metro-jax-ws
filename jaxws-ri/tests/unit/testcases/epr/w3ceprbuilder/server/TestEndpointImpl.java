/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.w3ceprbuilder.server;

/**
 * @author Rama Pulavarthi
 */
@javax.jws.WebService(endpointInterface = "epr.w3ceprbuilder.server.Hello")

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
