/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.catalog.server;

import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;
import java.util.List;

/**
 * @authoer Rama Pulavarthi
 */
@jakarta.jws.WebService(endpointInterface="fromwsdl.catalog.server.Hello")
public class HelloImpl implements Hello {
    public HelloResponse hello(Hello_Type req)  {
        System.out.println("Hello_PortType_Impl received: " + req.getArgument() +
            ", " + req.getExtra());
        HelloResponse resp = new HelloResponse();
        resp.setName("Duke");
        resp.setArgument(req.getArgument());
        resp.setExtra(req.getExtra());
        return resp;
    }

}
