/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.multibinding_soap.server;

@javax.jws.WebService (endpointInterface="fromwsdl.multibinding_soap.server.PingPort")
public class PingImpl {
    
   public String ping(String ticket, String message) {
        System.out.println("Ping:The message is here : " + message);
        return message;

    }

     public String ping2(String ticket, String message) {
        System.out.println("Ping2:The message is here 2: " + message);
        return message;

    }
                                                                                
    public String ping0(String ticket, String message) {
        return ping(ticket, message);
       
    }
}
