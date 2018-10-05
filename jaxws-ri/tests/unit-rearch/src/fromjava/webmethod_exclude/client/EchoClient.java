/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.webmethod_exclude.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import testutil.ClientServerTestUtil;
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
        assertTrue(stub.echoString("test").equals("test"));
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

