/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.wsdl_hello_lit_oneway.client;

import junit.framework.*;
import testutil.ClientServerTestUtil;
import jakarta.xml.ws.Service;
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
//            stub = (Hello)ClientServerTestUtil.getPort(
//                Hello_Service.class, Hello.class,
//                new QName("urn:test", "HelloPort"));
            Hello_Service service = new Hello_Service();
            stub = service.getHelloPort();
            ClientServerTestUtil.setTransport(stub);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stub;
    }

    public void testHello() throws Exception {
        try{
            Hello stub = getStub();
            String arg = "foo";
            String extra = "bar";
            Hello_Type req = new Hello_Type();
            req.setArgument(arg);req.setExtra(extra);

            for(int i=0; i < 10; i++) {
                stub.hello(req);
            }
        }catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

}
