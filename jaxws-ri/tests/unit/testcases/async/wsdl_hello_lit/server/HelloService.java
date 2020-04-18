/*
 * Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.wsdl_hello_lit.server;

import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;

@WebService(endpointInterface="async.wsdl_hello_lit.server.Hello")
public class HelloService implements Hello {
    public HelloOutput hello(Hello_Type req){
        System.out.println("Hello_PortType_Impl received: " + req.getArgument() +
            ", " + req.getExtra());
        HelloOutput resp = new HelloOutput();
        resp.setArgument(req.getArgument());
        resp.setExtra(req.getExtra());
        return resp;
    }
    
    public void hello1(HelloType req, HelloType inHeader, 
            Holder<HelloOutput> resp, Holder<HelloType> outHeader){
        HelloOutput out = new HelloOutput();
        out.setArgument(req.getArgument());
        out.setExtra(req.getExtra());
        resp.value = out; 
        outHeader.value = inHeader;        
    }

    public int hello0(int param_in){
        return param_in;
    }

}
