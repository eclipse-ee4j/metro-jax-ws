/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.handler_simple.server;

import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

/**
 */
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@WebService(portName="HelloPort12", endpointInterface="fromwsdl.handler_simple.server.Hello12")
public class HelloService12_Impl implements Hello12 {

    public int hello12(int x) {
        System.out.println("Hello12Service_Impl received: " + x);
        return x;
    }

}
