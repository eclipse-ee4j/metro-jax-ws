/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.session_ignorecase_portable1.client;

import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import junit.framework.TestCase;

/**
 * Address is set dynamically between the requests and see if session
 * is continued
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
        proxy.introduce();

        String addr = (String)requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        URL url = new URL(addr);
        String host = url.getHost();
        addr = addr.replace(host, host.toUpperCase());
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, addr);

        assertTrue("client session should be maintained", proxy.rememberMe());
    }
    
}
