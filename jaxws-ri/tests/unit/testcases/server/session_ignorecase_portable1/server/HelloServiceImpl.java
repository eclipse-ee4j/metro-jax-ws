/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.session_ignorecase_portable1.server;

import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.Resource;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceContext;
import java.util.*;

/**
 * Making sure that cookies are returned by client
 *
 * @author Jitendra Kotamraju
 */
@WebService(name="Hello", serviceName="HelloService", targetNamespace="urn:test")
public class HelloServiceImpl {
    
    @Resource
    private WebServiceContext wsc;
    
    @WebMethod
    public void introduce() {
        Map<String, List<String>> hdrs = new HashMap<String, List<String>>();
        hdrs.put("Set-Cookie", Collections.singletonList("a=b"));
        MessageContext mc = wsc.getMessageContext();
        mc.put(MessageContext.HTTP_RESPONSE_HEADERS, hdrs);
    }
    
    @WebMethod
    public boolean rememberMe() {
        MessageContext mc = wsc.getMessageContext();
        Map<String, List<String>> hdrs = (Map<String, List<String>>)mc.get(MessageContext.HTTP_REQUEST_HEADERS);
        List<String> cookieList = hdrs.get("Cookie");
        if (cookieList == null || cookieList.get(0) == null || !cookieList.get(0).equals("a=b")) {
            return false;
        }
        return true; 
    }
}
