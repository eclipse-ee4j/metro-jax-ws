/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.cookie_subdomain.server;

import jakarta.annotation.Resource;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.handler.MessageContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void introduce(String fqdn) {
        assert fqdn.indexOf('.') != -1;
        String sub = fqdn.substring(fqdn.indexOf('.')+1);
        Map<String, List<String>> hdrs = new HashMap<String, List<String>>();
        List<String> cookieList = new ArrayList<String>();
        cookieList.add("a=b; domain="+fqdn);
        cookieList.add("c=d; domain="+sub);
        System.out.println("Setting Cookies = "+cookieList);
        hdrs.put("Set-Cookie", cookieList);
        MessageContext mc = wsc.getMessageContext();
        mc.put(MessageContext.HTTP_RESPONSE_HEADERS, hdrs);
    }
    
    @WebMethod
    public boolean rememberMe() {
        MessageContext mc = wsc.getMessageContext();
        Map<String, List<String>> hdrs = (Map<String, List<String>>)mc.get(MessageContext.HTTP_REQUEST_HEADERS);
        List<String> cookieList = hdrs.get("Cookie");
        if (cookieList == null || cookieList.size() != 2) {
            throw new WebServiceException("Expecting two cookies, Got="+cookieList);
        }
        String cookie1 = cookieList.get(0);
        String cookie2 = cookieList.get(1);
        return (cookie1.equals("a=b") && cookie2.equals("c=d")) ||
                (cookie1.equals("c=d") && cookie2.equals("a=b"));
    }
}
