/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint.client;
                                                                                
import java.security.Principal;
import javax.jws.*;
import javax.jws.soap.SOAPBinding;
                                                                                
import java.rmi.RemoteException;
                                                                                
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceContext;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceException;

/**
 * @author Jitendra Kotamraju
 */
@WebService(name="RpcLit", serviceName="RpcLitEndpoint", portName="RpcLitPort", targetNamespace="http://echo.org/", endpointInterface="server.endpoint.client.RpcLitEndpointIF")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class RpcLitEndpoint {
    @Resource
    private WebServiceContext ctxt;
    
    public int echoInteger(int arg0) {       
        Principal principal = ctxt.getUserPrincipal();
        System.out.println("Pricipal.getName()="+principal.getName());
        if (!principal.getName().equals("auth-user")) {
            throw new WebServiceException("Principal is incorrect.");
        }
        return arg0;
    }
}
