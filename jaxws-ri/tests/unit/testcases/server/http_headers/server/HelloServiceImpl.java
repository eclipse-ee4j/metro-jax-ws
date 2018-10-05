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

import java.util.*;

import javax.annotation.Resource;

import javax.jws.WebMethod;
import javax.jws.WebService;

import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceContext;

/**
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
        if (ctxt.get(MessageContext.HTTP_REQUEST_HEADERS) == null
            || ctxt.get(MessageContext.HTTP_REQUEST_METHOD) == null
            || !ctxt.get(MessageContext.HTTP_REQUEST_METHOD).equals("POST")) {
            throw new WebServiceException("MessageContext is not populated.");
        }
        Map<String, List<String>> hdrs = (Map<String, List<String>>)ctxt.get(MessageContext.HTTP_REQUEST_HEADERS);
        List<String> customHdrList = hdrs.get("custom-header");
        if (customHdrList == null || customHdrList.size() != 1) { 
            throw new WebServiceException("MessageContext is not populated.");
        }
        String got = customHdrList.get(0);
        if (!got.equals("custom-value")) {
            throw new WebServiceException("Expected="+"custom-value"+" got="+got);
        }
    }
    
}
