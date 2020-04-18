/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.mixed_bindings.server;


import jakarta.jws.*;
import jakarta.jws.soap.*;

import jakarta.xml.ws.Holder;
import jakarta.xml.ws.*;


@WebService(name="Echo", serviceName="EchoService", targetNamespace="http://echo.org/")
public class EchoImpl {

    /*
     * ParameterStyle=BARE Methods 
     */

    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebMethod
    public String echoBar(String bar) {
        return bar;
    }

    /*
     * ParameterStyle=WRAPPED Methods 
     */
    @WebMethod
    public String echoFoo(String foo, String random) {
        return foo + random;
    }
    
    @WebMethod(operationName="echoFoo1", action="echoFoo1")   
    @RequestWrapper(className="misc.java_overload.Foo1Req")
    @ResponseWrapper(targetNamespace="urn:foo", className="misc.java_overload.Foo1Resp")
    public int echoFoo(int foo) {
        return foo;
    }
    
}
