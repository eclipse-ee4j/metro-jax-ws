/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.wsdl_hello_lit_soap12.client;

import junit.framework.*;
import testutil.ClientServerTestUtil;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPFaultException;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stub;
    }

    public void testSendBean() throws Exception {
		Hello stub = getStub();
		String arg = "sendBean";
		String extra = "bar";
		Hello_Type req = new Hello_Type();
		req.setArgument(arg);req.setExtra(extra);
		HelloResponse response = stub.hello(req, req);
		assertEquals("foo", response.getArgument());
		assertEquals("bar", response.getExtra());
    }

    public void testSendSource() throws Exception {
		Hello stub = getStub();
		String arg = "sendSource";
		String extra = "bar";
		Hello_Type req = new Hello_Type();
		req.setArgument(arg);req.setExtra(extra);
		HelloResponse response = stub.hello(req, req);
		assertEquals("foo", response.getArgument());
		assertEquals("bar", response.getExtra());
    }

    public void testException() throws Exception {
		try {
			Hello stub = getStub();
			String arg = "exp";
			String extra = "bar";
			Hello_Type req = new Hello_Type();
			req.setArgument(arg);req.setExtra(extra);
			stub.hello(req, req);
			assertTrue(false);
		} catch(SOAPFaultException e) {
			assertTrue(true);	
		}
    }

}
