/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint.client;
                                                                                
import java.security.Principal;
import jakarta.jws.*;
import jakarta.jws.soap.SOAPBinding;
                                                                                
import java.rmi.RemoteException;
                                                                                
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceContext;
import jakarta.annotation.Resource;
import jakarta.xml.ws.WebServiceException;

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
        // In JDK17 comes as localhost.realm.com:auth-user
        String[] realmUser = principal.getName().split(":");
        String userName = null;
        if (realmUser.length == 1) {
        	userName = realmUser[0];
        } else {
        	userName = realmUser[1];
        }
        System.out.println("Pricipal.getName()="+principal.getName());
        if (!"auth-user".equals(userName)) {
            throw new WebServiceException("Principal " + principal.getName() + " is incorrect.");
        }
        return arg0;
    }
}
