/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.session_ignorecase_834.server;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import javax.jws.WebMethod;
import javax.jws.WebService;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceContext;

/**
 * HTTP session test
 *
 * @author Jitendra Kotamraju
 */
@WebService(name="Hello", serviceName="HelloService", targetNamespace="urn:test")
public class HelloServiceImpl {
    
    @Resource
    private WebServiceContext wsc;
    private Set<String> clients;

    public HelloServiceImpl() {
        clients = new HashSet<String>();
    }
    
    @WebMethod
    public void introduce() {
        String id = getClientId();
        System.out.println("** storing session id: " + id);
        clients.add(id);
    }
    
    @WebMethod
    public boolean rememberMe() {
        String id = getClientId();
        System.out.println("** looking up id: " + id);
        return clients.contains(id);
    }
    
    private String getClientId() {
        HttpServletRequest req = (HttpServletRequest)
            wsc.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        HttpSession session = req.getSession();
        return session.getId();
    }
    
}
