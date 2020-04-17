/*
 * Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.http_only.client;

import java.util.Map;
import java.util.List;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.Service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * HTTP session test
 *
 * @author Jitendra Kotamraju
 */
public class SessionTest extends TestCase {

    public SessionTest(String name) {
        super(name);
    }

    /*
     * Without setting the maintain property, session
     * should not be maintained.
     */
    public void test1() throws Exception {
        Hello proxy = new HelloService().getHelloPort();
        proxy.introduce();
        assertFalse("client session should not be maintained",
            proxy.rememberMe());
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
        proxy.introduce();
        assertFalse("client session should not be maintained",
            proxy.rememberMe());
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
        proxy.introduce();
        assertTrue("client session should be maintained", proxy.rememberMe());
    }

    /*
     * Tests Standard Servlet MessageContext properties
     */
    public void testServletMsgCtxt() throws Exception {
        Hello proxy = new HelloService().getHelloPort();
        proxy.testServletProperties();
    }

    /*
     * Tests Standard HTTP MessageContext properties on server side
     */
    public void testHttpMsgCtxt() throws Exception {
        Hello proxy = new HelloService().getHelloPort();
        proxy.testHttpProperties();
    }

    /*
     * Tests Standard HTTP MessageContext properties on client side
     */
    public void testClientHttpMsgCtxt() throws Exception {
        Hello proxy = new HelloService().getHelloPort();
        proxy.introduce();
        Map<String, Object> responseContext =
            ((BindingProvider) proxy).getResponseContext();
        Integer code = (Integer)responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
        assertTrue(code != null);
        assertEquals((int)code, 200);
        Map<String, List<String>> headers =
            (Map<String, List<String>>)responseContext.get(MessageContext.HTTP_RESPONSE_HEADERS);
        assertTrue(headers != null);
    }
    
}
