/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.handler_dd.server;

@javax.jws.WebService(endpointInterface="fromwsdl.handler_dd.server.Hello")
public class HelloService_Impl implements Hello {
    
    public int hello(int x) {
        System.out.println("HelloService_Impl received: " + x);
        return x;
    }
    
}
