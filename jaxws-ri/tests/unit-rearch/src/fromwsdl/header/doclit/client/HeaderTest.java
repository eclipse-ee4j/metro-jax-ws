/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.header.doclit.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Holder;

/**
 * @author JAX-RPC RI Development Team
 */
public class HeaderTest extends TestCase {
    private static HelloPortType stub;

    public HeaderTest(String name) throws Exception {
        super(name);
        HelloService service = new HelloService();
        stub = (HelloPortType) service.getHelloPort();
        ClientServerTestUtil.setTransport(stub);
    }

    public void testEcho() throws Exception {
        EchoType request = new EchoType();
        request.setReqInfo("foo");
        Echo2Type header2 = new Echo2Type();
        header2.setReqInfo("foo");
        EchoResponseType response = stub.echo(request, request, header2);
        assertEquals("foofoofoo", (response.getRespInfo()));
    }

    public void testEcho2() throws Exception {
        String response = stub.echo2("foo");
        assertEquals("foobar", response);
    }

    public void testEcho3() throws Exception {
        Holder<String> req = new Holder<String>("foo");
        stub.echo3(req);
        assertEquals("foobar", req.value);
    }


    public void testEcho4() throws Exception {
        Echo4Type reqBody = new Echo4Type();
        reqBody.setExtra("foo1");
        reqBody.setArgument("bar1");

        Echo4Type reqHeader = new Echo4Type();
        reqHeader.setExtra("foo2");
        reqHeader.setArgument("bar2");

        String req2HeaderType = "foobar3";
        Holder<String> req2Header = new Holder<String>(req2HeaderType);
        Holder<String> respBody = new Holder<String>();
        Holder<String> respHeader = new Holder<String>();

        stub.echo4(reqBody, reqHeader, req2HeaderType, respBody, respHeader);
        assertEquals("foo1bar1foo2bar2foobar3", respBody.value);
    }

    public void testEcho5() throws Exception {
        EchoType body = new EchoType();
        body.setReqInfo("Hello World!");
        String resp = stub.echo5(body);
        assertEquals(resp, body.getReqInfo());
    }
    /**
     * TODO: this test has header as return type, it wont work till we have annotation
     * on @WebResult or similar solution. Commenting out till we have it.
     */
//    public void testEcho6() throws Exception {
//        EchoType body = new EchoType();
//        String reqName = "Vivek";
//        String address = "4140 Network Cirlce";
//        body.setReqInfo(reqName);
//        Holder<String> name = new Holder<String>();
//        EchoType header = new EchoType();
//        header.setReqInfo(address);
//        Holder<EchoType> req = new Holder<EchoType>(body);
//        String resp = stub.echo6(name, header, req);
//        assertEquals(req.value.getReqInfo(), reqName + "'s Response");
//        assertEquals(resp, name.value +"'s Address: "+address);
//    }

    /**
     * TODO: this test has header as return type, it wont work till we have annotation
     * on @WebResult or similar solution. Commenting out till we have it.
     */
//    public void testEcho7() throws Exception {
//        String firstName = "Vivek";
//        String lastName = "Pandey";
//        Holder<String> address = new Holder<String>();
//        Holder<String> personDetails = new Holder<String>();
//        NameType nameType = stub.echo7(address, personDetails, lastName, firstName);
//        assertEquals(nameType.getName(), "Employee");
//        assertEquals(address.value, "Sun Micro Address");
//        assertEquals(personDetails.value, "Vivek Pandey");
//    }
}
