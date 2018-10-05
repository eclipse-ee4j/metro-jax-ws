/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.http_session_scope.client;

import java.util.Map;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.Service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * HttpSessionScope test
 *
 * @author Jitendra Kotamraju
 */
public class HttpSessionScopeTest extends TestCase {

    public HttpSessionScopeTest(String name) {
        super(name);
    }

    /*
     * Without setting the maintain property, session
     * should not be maintained.
     */
    public void test1() throws Exception {
        Hello proxy = new HelloService().getHelloPort();
        assertEquals(0, proxy.getCounter());
        assertEquals(0, proxy.getCounter());
    }
    
    /*
     * With maintain property set to false, session
     * should not be maintained.
     */
    public void test2() throws Exception {
        Hello proxy = new HelloService().getHelloPort();
        Map<String, Object> requestContext =
            ((BindingProvider) proxy).getRequestContext();
        requestContext.put(
            BindingProvider.SESSION_MAINTAIN_PROPERTY, Boolean.FALSE);
        assertEquals(0, proxy.getCounter());
        assertEquals(0, proxy.getCounter());
    }
    
    /*
     * With maintain property set to true, session
     * should be maintained.
     */
    public void test3() throws Exception {
        Hello proxy = new HelloService().getHelloPort();
        Map<String, Object> requestContext =
            ((BindingProvider) proxy).getRequestContext();
        requestContext.put(
            BindingProvider.SESSION_MAINTAIN_PROPERTY, Boolean.TRUE);
        assertEquals(0, proxy.getCounter());
        assertEquals(1, proxy.getCounter());
        assertEquals(2, proxy.getCounter());
    }

    
}
