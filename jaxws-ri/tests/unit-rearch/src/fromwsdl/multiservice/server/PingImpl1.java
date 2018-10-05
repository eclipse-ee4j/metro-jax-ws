/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.multiservice.server;

@javax.jws.WebService (endpointInterface="fromwsdl.multiservice.server.PingPort1")
public class PingImpl1 implements PingPort1 {
    
   public String ping1(TicketType ticket, String message) {
        System.out.println("Ping1:The message is here : " + message);
        return message;
    }
                                                                                
    public String ping01(TicketType ticket, String message) {
        return ping1(ticket, message);
    }
}
