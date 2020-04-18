/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.http_only.server;

import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.Resource;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceContext;

/**
 * hmmm
 */
@WebService(name="Hello", serviceName="HelloService", targetNamespace="urn:test")
public class HelloServiceImpl {
    
    @Resource private WebServiceContext wsc;
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

    @WebMethod
    public void testServletProperties() {
		MessageContext ctxt = wsc.getMessageContext();
        if (ctxt.get(MessageContext.SERVLET_REQUEST) == null
            || ctxt.get(MessageContext.SERVLET_RESPONSE) == null
            || ctxt.get(MessageContext.SERVLET_CONTEXT) == null) {
            throw new WebServiceException("MessageContext is not populated.");
        }
    }

    @WebMethod
    public void testHttpProperties() {
		MessageContext ctxt = wsc.getMessageContext();
        if (ctxt.get(MessageContext.HTTP_REQUEST_HEADERS) == null
            || ctxt.get(MessageContext.HTTP_REQUEST_METHOD) == null
            || !ctxt.get(MessageContext.HTTP_REQUEST_METHOD).equals("POST")) {
            throw new WebServiceException("MessageContext is not populated.");
        }
    }
    
    private String getClientId() {
        HttpServletRequest req = (HttpServletRequest)
            wsc.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        HttpSession session = req.getSession();
        return session.getId();
    }
    
}
