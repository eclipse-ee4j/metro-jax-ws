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
import jakarta.jws.soap.SOAPBinding;
                                                                                
import java.rmi.RemoteException;
                                                                                
import jakarta.xml.ws.Holder;

/**
 * @author Jitendra Kotamraju
 */
@WebService(name="RpcLit", endpointInterface="whitebox.endpoint.client.RpcLitEndpoint1IF", serviceName="RpcLitEndpoint", targetNamespace="http://echo.org/")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class RpcLitEndpoint1 {
    @WebMethod
    public Integer echoInteger(Integer param) {
        return param;
    }
}
