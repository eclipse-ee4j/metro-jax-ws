/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.tube_predestroy.server;

import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;

/**
 * @author Martin Grebac
 */
@WebService(endpointInterface="async.tube_predestroy.server.Hello")
public class HelloService implements Hello {

    public HelloOutput hello(Hello_Type req){
        return new HelloOutput();
    }
    
    public void hello1(HelloType req, HelloType inHeader, 
            Holder<HelloOutput> resp, Holder<HelloType> outHeader){
    }

    public int hello0(int param_in) {
        return param_in;
    }

}
