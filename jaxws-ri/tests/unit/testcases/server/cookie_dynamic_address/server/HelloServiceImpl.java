/*
 * Copyright (c) 2011, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.cookie_dynamic_address.server;

import javax.annotation.Resource;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.handler.MessageContext;
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

    public void introduce() {
        Map<String, List<String>> hdrs = new HashMap<String, List<String>>();
        List<String> cookies = new ArrayList<String>();
        cookies.add("JREPLICA=instance02");
        cookies.add("METRO_KEY=HASHABLE_KEY_264");
        cookies.add("JROUTE=kmH+");
        hdrs.put("Set-Cookie", cookies);
        MessageContext mc = wsc.getMessageContext();
        mc.put(MessageContext.HTTP_RESPONSE_HEADERS, hdrs);
    }

    public boolean rememberMe() {
        MessageContext mc = wsc.getMessageContext();
        Map<String, List<String>> hdrs = (Map<String, List<String>>)mc.get(MessageContext.HTTP_REQUEST_HEADERS);
        List<String> cookieList = hdrs.get("Cookie");
        int noOfCookies = 0;
        System.out.println("******* server cookieList ********"+cookieList);
        if (cookieList != null) {
            for(String cookie : cookieList) {
                if (cookie.equals("JREPLICA=instance02")) {
                    noOfCookies++;
                } else if (cookie.equals("METRO_KEY=HASHABLE_KEY_264")) {
                    noOfCookies++;
                } else if (cookie.equals("JROUTE=kmH+")) {
                    noOfCookies++;
                }
            }
        }
        if (noOfCookies != 3) {
            throw new WebServiceException("Didn't receive all the cookies. Received:"+cookieList);
        }
        return true;
    }
}
