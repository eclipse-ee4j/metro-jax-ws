/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.session_ignorecase_portable.client;

import java.net.URL;
import java.util.Map;

import jakarta.xml.ws.BindingProvider;

import junit.framework.TestCase;

/**
 * Host in web service address is uppercase. That causes problems for
 * sticking cookies
 *
 * @author Jitendra Kotamraju
 */
public class HostIgnoreCaseTest extends TestCase {

    public HostIgnoreCaseTest(String name) {
        super(name);
    }
    
    /*
     * With maintain property set to true, session
     * should be maintained.
     */
    public void test3() throws Exception {
        Hello proxy = new HelloService().getHelloPort();

        // Set the adress with upper case hostname
        Map<String, Object> requestContext =
            ((BindingProvider) proxy).getRequestContext();
        requestContext.put(
            BindingProvider.SESSION_MAINTAIN_PROPERTY, Boolean.TRUE);
        // proxy.introduce(); Doing this below as setting endpoint address
        // resets the cookie store. Will be handled in future revisions

        String addr = (String)requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        URL url = new URL(addr);
        String host = url.getHost();
        addr = addr.replace(host, host.toUpperCase());
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, addr);

        proxy.introduce();
        assertTrue("client session should be maintained", proxy.rememberMe());
    }
    
}
