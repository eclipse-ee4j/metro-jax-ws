/*
 * Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.http_multi_cookie_servlet.client;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.Service;
import java.util.Collections;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * HTTP HA test
 *
 * @author Jitendra Kotamraju
 */
public class MultiCookieTest extends TestCase {


    public MultiCookieTest(String name) {
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
        assertTrue("client session should be maintained", proxy.rememberMe());
    }
    
}
