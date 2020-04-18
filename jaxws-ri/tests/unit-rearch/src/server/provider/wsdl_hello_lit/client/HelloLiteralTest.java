/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.wsdl_hello_lit.client;

import junit.framework.*;
import testutil.ClientServerTestUtil;
import jakarta.xml.ws.Service;
import javax.xml.namespace.QName;
import java.io.PrintStream;
import jakarta.xml.ws.soap.SOAPFaultException;
import java.util.Random;

/**
 *
 * @author Jitendra Kotamraju
 */
public class HelloLiteralTest extends TestCase {
    private Hello stub;
    private Hello asyncStub;

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

    private Hello getAsyncStub(){
        if (asyncStub != null) {
            return asyncStub;
        }
        try {
            Hello_Service service = new Hello_Service();
            asyncStub = service.getHelloAsyncPort();
            ClientServerTestUtil.setTransport(asyncStub);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return asyncStub;
    }

    public void testHello() throws Exception {
        Hello stub = getStub();
        Hello_Type req = new Hello_Type();
        req.setArgument("arg");req.setExtra("extra");

        for(int i=0; i < 5; i++) {
            HelloResponse response = stub.hello(req, req);
            assertEquals("arg", response.getArgument());
            assertEquals("extra", response.getExtra());
        }
    }

    public void testHelloAsync() throws Exception {
        Hello asyncStub = getAsyncStub();
        Hello_Type req = new Hello_Type();
        req.setArgument("arg");req.setExtra("extra");

        for(int i=0; i < 5; i++) {
            HelloResponse response = asyncStub.hello(req, req);
            assertEquals("arg", response.getArgument());
            assertEquals("extra", response.getExtra());
        }
    }

    public void testFault() throws Exception {
        Hello stub = getStub();
        Hello_Type req = new Hello_Type();
        req.setArgument("fault");req.setExtra("extra");

        try {
            HelloResponse response = stub.hello(req, req);
        } catch (SOAPFaultException e) {
            SOAPFaultException se = (SOAPFaultException)e;
            assertEquals("Server was unable to process request. ---> Not a valid accountnumber.", se.getFault().getFaultString());
        }
    }

}
