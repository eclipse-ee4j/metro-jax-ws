/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.xmlbinding.client;

import junit.framework.*;
import testutil.ClientServerTestUtil;
import javax.xml.ws.Service;
import javax.xml.namespace.QName;
import java.io.PrintStream;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {
    private Hello stub;

    public HelloLiteralTest(String name) {
        super(name);
    }

    private Hello getStub(){
        if (stub != null) {
            return stub;
        }
        try {
            Hello_Service service = new Hello_Service();
            stub = service.getHelloPort();
            ClientServerTestUtil.setTransport(stub);
//            stub = (Hello)ClientServerTestUtil.getPort(
//                Hello_Service.class, Hello.class,
//                new QName("urn:test", "HelloPort"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stub;
    }

    public void testHello() throws Exception {
        try {
            Hello stub = getStub();
            String arg = "foo";
            String extra = "bar";
            Hello_Type req = new Hello_Type();
            req.setArgument(arg);req.setExtra(extra);

            for(int i=0; i < 10; i++) {
                HelloResponse response = stub.hello(req, req);
                assertEquals(arg, response.getArgument());
                assertEquals(extra, response.getExtra());
            }
        } catch(Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testException() throws Exception {
        try {
            Hello stub = getStub();
            String arg = "foo1";
            String extra = "bar";
            Hello_Type req = new Hello_Type();
            req.setArgument(arg);req.setExtra(extra);
            HelloResponse response = stub.hello(req, req);
            assertTrue(false);
        } catch(Exception e) {
            return;
        }
    }

}
