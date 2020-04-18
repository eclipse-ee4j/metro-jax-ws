/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.multibinding.server;

@jakarta.jws.WebService (endpointInterface="fromwsdl.multibinding.server.PingPort")
public class PingImpl implements PingPort {
    
   public String ping(TicketType ticket, String message) {
        System.out.println("Ping:The message is here : " + message);
        return message;

    }
                                                                                
    public String ping0(TicketType ticket, String message) {
        return ping(ticket, message);
       
    }
}
