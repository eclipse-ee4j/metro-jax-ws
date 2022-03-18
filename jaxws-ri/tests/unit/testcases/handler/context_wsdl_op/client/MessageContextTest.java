/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.context_wsdl_op.client;

import junit.framework.TestCase;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Binding;
import jakarta.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.List;

import handler.context_wsdl_op.common.TestHandler;

/**
 * Tests WSDL_OPERATION property in MessageContext
 * @author Rama Pulavarthi
 */
public class MessageContextTest extends TestCase {
    public MessageContextTest(String name) throws Exception {
        super(name);
    }

    public void testRequestResponse() throws Exception {
      HelloService  helloService = new HelloService();
      Hello helloPort = helloService.getHelloPort();
      Binding binding = ((BindingProvider)helloPort).getBinding();
      List<Handler> handlers =  new ArrayList<Handler>();
      handlers.add(new TestHandler("CLIENT-SIDE"));
      binding.setHandlerChain(handlers);  
      int x = 1;
      int y = helloPort.sayHello(x);
      assertTrue(y == x);
    }
}
