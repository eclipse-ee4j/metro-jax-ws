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

/**
 */
@jakarta.jws.WebService(portName="HelloPort", endpointInterface="fromwsdl.handler_simple_rpclit.server.Hello")
public class HelloService_Impl implements Hello {

    public int hello(int x) {
        System.out.println("HelloService_Impl received: " + x);
        return x;
    }

}
