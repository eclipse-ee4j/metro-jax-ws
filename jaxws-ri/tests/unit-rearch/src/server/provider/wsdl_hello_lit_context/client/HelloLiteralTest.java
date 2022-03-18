/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.wsdl_hello_lit_context.client;

import javax.xml.namespace.QName;
import junit.framework.*;
import testutil.ClientServerTestUtil;
import jakarta.xml.ws.*;
import jakarta.xml.ws.handler.MessageContext;
import java.util.Map;
import java.util.List;

/**
 *
 * @author Jitendra Kotamraju
 */
public class HelloLiteralTest extends TestCase {

    public HelloLiteralTest(String name) {
        super(name);
    }

    private Hello getStub(){
        Hello_Service service = new Hello_Service();
        return service.getHelloPort();
    }

    private Hello getMsgStub() {
        HelloMsg service = new HelloMsg();
        Hello hello =  service.getHelloMsgPort();
        return hello;
    }

    public void testVoid() {
        Hello stub = getStub();
        VoidType req = new VoidType();
        VoidType response = stub.voidTest(req);
        assertNotNull(response);
    }

    public void testMsgVoid() {
        Hello stub = getMsgStub();
        VoidType req = new VoidType();
        VoidType response = stub.voidTest(req);
        assertNotNull(response);
        if (!ClientServerTestUtil.useLocal()) {
            Map<String, List<String>> map = (Map<String, List<String>>)((BindingProvider)stub).getResponseContext().get(MessageContext.HTTP_RESPONSE_HEADERS);

            assertNotNull(map);
            assertEquals("bar", map.get("foo").get(0));
        }
    }

}
