/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.default_pkg.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import testutil.ClientServerTestUtil;

import javax.xml.ws.soap.SOAPFaultException;

/**
 * @author JAX-RPC Development Team
 */
public class EchoClient extends TestCase {
    private static Echo stub;
    ClientServerTestUtil util = new ClientServerTestUtil();

    public EchoClient(String name) throws Exception{
        super(name);
        EchoService service = new EchoService();
        stub = service.getEchoPort();
        ClientServerTestUtil.setTransport(stub);
    }

    public void testSimple() throws Exception {
        assertTrue(stub.addNumbers(1,2)== 3);
    }

    public void testSimple1() throws Exception {
        try {
            stub.addNumbers(-1,2);
            assertTrue(false);
        } catch(AddNumbersException_Exception e) {
            //As expected
        }
    }

    /*
     * for debugging
     */
    public static void main(String [] args) {
        try {
            System.setProperty("uselocal", "true");
            EchoClient testor = new EchoClient("TestClient");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

