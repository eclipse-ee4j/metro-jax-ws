/*
 * Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.basic_auth.server;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.List;

import javax.annotation.Resource;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceContext;

/**
 * HTTP basic auth test
 *
 * @author Jitendra Kotamraju
 */
@WebService(name="Hello", serviceName="HelloService", targetNamespace="urn:test")
public class HelloServiceImpl {
    
    @Resource
    private WebServiceContext wsc;

    @WebMethod
    public void testHttpProperties() {
        MessageContext ctxt = wsc.getMessageContext();
        Map<String, List<String>> headers = (Map<String, List<String>>)ctxt.get(MessageContext.HTTP_REQUEST_HEADERS);
        if (headers == null) {
            throw new WebServiceException("HTTP_HEADERS is not populated");
        }
        List<String> authHeader = headers.get("Authorization");
        if (authHeader == null) { 
            throw new WebServiceException("No Authorization Header="+authHeader);
        }
        if (authHeader.size() != 1) { 
            throw new WebServiceException("Incorrect Authorization Header="+authHeader);
        }
        String expected = "Basic YXV0aC11c2VyOmF1dGgtcGFzcw==";
        String got = authHeader.get(0);
        if (got == null || !got.equals(expected)) {
            throw new WebServiceException("Authorization value expected="+expected+" got="+got);
        }
    }
    
}
