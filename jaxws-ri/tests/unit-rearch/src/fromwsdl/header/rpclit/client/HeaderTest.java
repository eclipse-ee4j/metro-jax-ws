/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.header.rpclit.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class HeaderTest extends TestCase {

    private static HelloPortType stub;

    public HeaderTest(String name) throws Exception{
        super(name);
        HelloService service = new HelloService();
        stub = service.getHelloPort();
        ClientServerTestUtil.setTransport(stub);
    }

    public void testEcho3() throws Exception{
        assertEquals(1, stub.echo3("1"));
    }

    public void testEcho() throws Exception {
        ObjectFactory of = new ObjectFactory();
        EchoType reqBody = new EchoType();
        reqBody.setReqInfo("foobar");
        EchoType reqHeader = new EchoType();
        reqHeader.setReqInfo("foobar");
        EchoResponseType response = stub.echo(reqBody, reqHeader);
        assertEquals("foobarfoobar", response.getRespInfo());
    }

    public void testNullParameter() {
        ObjectFactory of = new ObjectFactory();
        EchoType reqHeader = new EchoType();
        reqHeader.setReqInfo("foobar");
        try{
            EchoResponseType response = stub.echo(null, reqHeader);
            assertTrue(false);
        }catch(WebServiceException e){
            assertTrue(true);
        }

    }

    public void testNullParameterOnServer() {
        ObjectFactory of = new ObjectFactory();
        EchoType reqBody = new EchoType();
        reqBody.setReqInfo("sendNull");
        EchoType reqHeader = new EchoType();
        reqHeader.setReqInfo("foobar");
        try{
            EchoResponseType response = stub.echo(reqBody, reqHeader);
            assertTrue(false);
        }catch(WebServiceException e){
            assertTrue(true);
        }

    }

    public void testEcho1() throws Exception {
        String str = "Hello";
        Holder<String> req = new Holder<String>(str);
        stub.echo1(req);
        assertEquals(str+" World!", req.value);
    }


    public void testEcho2() throws Exception {
        ObjectFactory of = new ObjectFactory();
        EchoType reqBody = of.createEchoType();
        reqBody.setReqInfo("foobar");
        EchoType req1Header = of.createEchoType();
        req1Header.setReqInfo("foobar");
        Echo2Type req2Header = of.createEcho2Type();
        req2Header.setReqInfo("foobar");
        Echo2ResponseType response = stub.echo2(reqBody, req1Header, req2Header);
        assertEquals("foobarfoobarfoobar", response.getRespInfo());
    }
}
