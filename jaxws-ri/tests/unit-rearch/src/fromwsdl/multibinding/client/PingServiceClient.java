/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.multibinding.client;

import java.io.File;
import java.net.URL;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import junit.framework.TestCase;
import jakarta.xml.ws.Binding;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.soap.SOAPBinding;
import testutil.ClientServerTestUtil;
import jakarta.xml.ws.BindingProvider;

public class PingServiceClient extends TestCase{
    PingPort stub;
    PingPort stub1;
            
    public PingServiceClient(String s) throws Exception {
        super(s);
        PingService service = new PingService();
        stub = (PingPort) service.getPort(new QName("http://xmlsoap.org/Ping","Ping"), PingPort.class);

        stub1 = (PingPort) service.getPort(new QName("http://xmlsoap.org/Ping","Ping1"), PingPort.class);
         
    }
   

   
    public void testPing(){
        TicketType ticket = new TicketType();
        ticket.setValue("SUNW");
        stub.ping(ticket, "Hello  ping !");
        
    }

     public void testPing1(){
        TicketType ticket = new TicketType();
        ticket.setValue("SUNW");
        stub1.ping(ticket, "Hello  ping1 !");

    }
    
}
