/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.handler_simple_rpclit.server;

import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

/**
 */
@jakarta.jws.WebService(portName="HelloPort12", endpointInterface="fromwsdl.handler_simple_rpclit.server.Hello12")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)

public class HelloService12_Impl implements Hello12 {

    public int hello12(int x) {
        System.out.println("Hello12Service_Impl received: " + x);
        return x;
    }

}
