/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint_http_context.client;
                                                                                
import java.security.Principal;
import jakarta.jws.*;
import jakarta.jws.soap.SOAPBinding;
                                                                                
import java.rmi.RemoteException;
                                                                                
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceContext;
import javax.annotation.Resource;
import jakarta.xml.ws.WebServiceException;

/**
 * @author Jitendra Kotamraju
 */
@WebService(name="RpcLit", serviceName="RpcLitEndpoint", portName="RpcLitPort", targetNamespace="http://echo.org/", endpointInterface="server.endpoint_http_context.client.RpcLitEndpointIF")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class RpcLitEndpoint {
    @Resource
    private WebServiceContext ctxt;
    
    public int echoInteger(int arg0) {       
        return arg0;
    }
}
