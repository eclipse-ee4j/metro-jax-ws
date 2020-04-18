/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.executor_821.server;

import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;

/**
 * @author Jitendra Kotamraju
 */
@WebService(endpointInterface="async.executor_821.server.Hello")
public class HelloService implements Hello {

    public HelloOutput hello(Hello_Type req){
        // Intentionally adding the sleep so that we can test client
        try {
            Thread.sleep(10000);
        } catch(Exception e) {
            throw new WebServiceException(e);
        }
        HelloOutput resp = new HelloOutput();
        resp.setArgument(req.getArgument());
        resp.setExtra(req.getExtra());
        return resp;
    }
    
    public void hello1(HelloType req, HelloType inHeader, 
            Holder<HelloOutput> resp, Holder<HelloType> outHeader){
        // Intentionally adding the sleep so that we can test client
        try {
            Thread.sleep(10000);
        } catch(Exception e) {
            throw new WebServiceException(e);
        }
        HelloOutput out = new HelloOutput();
        out.setArgument(req.getArgument());
        out.setExtra(req.getExtra());
        resp.value = out; 
        outHeader.value = inHeader;        
    }

    public int hello0(int param_in) {
        // Intentionally adding the sleep so that we can test client
        try {
            Thread.sleep(10000);
        } catch(Exception e) {
            throw new WebServiceException(e);
        }
        return param_in;
    }

}
