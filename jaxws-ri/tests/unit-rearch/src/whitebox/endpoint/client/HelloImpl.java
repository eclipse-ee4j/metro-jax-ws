/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.endpoint.client;

import jakarta.jws.*;
import jakarta.jws.soap.*;
import jakarta.xml.ws.Holder;
import jakarta.jws.WebParam.Mode;

/**
 * @author Jitendra Kotamraju
 */

@WebService(serviceName="HelloService" , targetNamespace="http://service_api.endpoint.server/")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class HelloImpl {
   
    @WebMethod
    public String echoFoo(String foo) {
        return foo;
    }
   
}
