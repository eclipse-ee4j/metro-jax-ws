/*
 * Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.basic_auth.client;

import jakarta.xml.ws.BindingProvider;
import junit.framework.TestCase;

/**
 * HTTP Basic Auth test
 *
 * @author Jitendra Kotamraju
 */
public class BasicAuthTest extends TestCase {

    public BasicAuthTest(String name) {
        super(name);
    }

    /*
     * Tests Standard HTTP Authorization header on server side
     */
    public void testHttpMsgCtxt() throws Exception {
        Hello proxy = new HelloService().getHelloPort();
        BindingProvider bp = (BindingProvider)proxy;
        bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "auth-user");
        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "auth-pass");
        proxy.testHttpProperties();
    }
    
}
