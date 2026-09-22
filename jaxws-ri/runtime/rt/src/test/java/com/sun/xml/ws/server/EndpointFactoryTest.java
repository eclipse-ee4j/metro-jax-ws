/*
 * Copyright (c) 2012, 2026 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.binding.BindingImpl;
import junit.framework.TestCase;

import jakarta.jws.WebService;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.LogicalHandler;
import jakarta.xml.ws.handler.LogicalMessageContext;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class EndpointFactoryTest extends TestCase {

    public void testVerifyImplementorClass1() {
        try {
            boolean condition = EndpointFactory.verifyImplementorClass(MyImpl1.class);
            // returns true if Provider or AsyncProvider
            assertFalse(condition);
        } catch (Throwable t) {
            fail("Unexpected exception cought");
            t.printStackTrace();
        }
    }
    public void testVerifyImplementorClass2() {
        try {
            EndpointFactory.verifyImplementorClass(MyImpl2.class);
            fail("The given class isn't correct Web Service; verification should fail");
        } catch (IllegalArgumentException ignored) {
            assertTrue(true);
        } catch (Throwable t) {
            fail("Unexpected exception cought");
            t.printStackTrace();
        }
    }

    /**
     * A handler chain configured on the binding before the endpoint is created must
     * survive endpoint creation, rather than being replaced by the one declared with
     * {@code @HandlerChain} on the implementor.
     */
    public void testConfiguredHandlerChainSurvivesHandlerChainAnnotation() {
        WSBinding binding = BindingImpl.create(BindingID.SOAP11_HTTP);
        Handler<?> configured = new ConfiguredHandler();
        binding.setHandlerChain(Collections.<Handler>singletonList(configured));

        createEndpoint(binding);

        List<Handler> chain = binding.getHandlerChain();
        assertEquals(1, chain.size());
        assertSame(configured, chain.get(0));
    }

    /**
     * With no chain configured beforehand, the {@code @HandlerChain} declared one is
     * still applied, as it always has been.
     */
    public void testHandlerChainAnnotationAppliedWhenNoneConfigured() {
        WSBinding binding = BindingImpl.create(BindingID.SOAP11_HTTP);
        assertTrue(binding.getHandlerChain().isEmpty());

        createEndpoint(binding);

        List<Handler> chain = binding.getHandlerChain();
        assertEquals(1, chain.size());
        assertTrue(chain.get(0) instanceof AnnotationDeclaredHandler);
    }

    private static void createEndpoint(WSBinding binding) {
        WSEndpoint.create(AnnotatedProvider.class, true, null, null, null, null, binding,
                null, null, (URL) null);
    }
}

@WebService
class MyImpl1 { }

class MyImpl2 { }

class ConfiguredHandler implements LogicalHandler<LogicalMessageContext> {

    @Override
    public boolean handleMessage(LogicalMessageContext context) {
        return true;
    }

    @Override
    public boolean handleFault(LogicalMessageContext context) {
        return true;
    }

    @Override
    public void close(jakarta.xml.ws.handler.MessageContext context) {
    }
}
