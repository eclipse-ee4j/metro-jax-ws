/*
 * Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.http_multi_cookie_servlet.server;

import javax.annotation.Resource;
import jakarta.jws.WebService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.handler.MessageContext;
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

    public void introduce() {
        MessageContext mc = wsc.getMessageContext();
        HttpServletResponse sr = (HttpServletResponse)mc.get(MessageContext.SERVLET_RESPONSE);
        Cookie cookie1 = new Cookie("JREPLICA", "instance02");
        sr.addCookie(cookie1);

        Cookie cookie2 = new Cookie("METRO_KEY", "HASHABLE_KEY_264");
        sr.addCookie(cookie2);

        Cookie cookie3 = new Cookie("JROUTE", "kmH+");
        sr.addCookie(cookie3);
    }
    
    public boolean rememberMe() {
        MessageContext mc = wsc.getMessageContext();
        Map<String, List<String>> hdrs = (Map<String, List<String>>)mc.get(MessageContext.HTTP_REQUEST_HEADERS);
        List<String> cookieList = hdrs.get("Cookie");
        int noOfCookies = 0;
        System.out.println("******* server cookieList ********"+cookieList);
        for(String cookie : cookieList) {
            if (cookie.equals("JREPLICA=instance02")) {
                noOfCookies++;
            } else if (cookie.equals("METRO_KEY=HASHABLE_KEY_264")) {
                noOfCookies++;
            } else if (cookie.equals("JROUTE=kmH+")) {
                noOfCookies++;
            }
        }
        if (noOfCookies != 3) {
            throw new WebServiceException("Didn't receive all the cookies. Received:"+cookieList);
        }
        return true;
    }
}
