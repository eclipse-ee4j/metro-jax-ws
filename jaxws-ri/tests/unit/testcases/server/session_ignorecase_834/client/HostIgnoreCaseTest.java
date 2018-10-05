/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.session_ignorecase_834.client;

import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.Service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * HTTP session test
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
        Map<String, Object> requestContext =
            ((BindingProvider) proxy).getRequestContext();

        String addr = (String)requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        URL url = new URL(addr);
        String host = url.getHost();
        addr = addr.replace(host, host.toUpperCase());
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, addr);

        requestContext.put(
            BindingProvider.SESSION_MAINTAIN_PROPERTY, Boolean.TRUE);
        proxy.introduce();

        assertTrue("client session should be maintained", proxy.rememberMe());
    }
    
}
