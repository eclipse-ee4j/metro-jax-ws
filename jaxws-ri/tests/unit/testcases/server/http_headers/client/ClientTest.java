/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.http_headers.client;

import junit.framework.TestCase;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.util.Collections;

/**
 * @author Jitendra Kotamraju
 */
public class ClientTest extends TestCase {

    private Hello hello = new HelloService().getHelloPort();

    public ClientTest(String name) throws Exception {
        super(name);
    }

    public void testCustomHttpHeader() throws Exception {
        // keep singletonMap, singletonList as they are unmodifiable
        ((BindingProvider)hello).getRequestContext().put(
                MessageContext.HTTP_REQUEST_HEADERS,
                Collections.singletonMap("custom-header", Collections.singletonList("custom-value")));

        hello.testHttpProperties();
        System.out.println("MIRAN: TEST finished.");
    }

}
