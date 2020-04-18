/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.inherited_soapbindings.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Holder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JAX-RPC Development Team
 */
public class EchoClient extends TestCase {
    private EchoImpl stub;

    public EchoClient(String name) {
        super(name);
    }

    private EchoImpl getStub() throws Exception {
        if (stub != null) {
            return stub;
        }
        stub = new EchoImplService().getEchoPort();
        ClientServerTestUtil.setTransport(stub);
        return stub;
    }

    public void testSimple() throws Exception {
        EchoImpl stub = getStub();

        assertTrue(stub.echoA("A").equals("EchoBase2:A"));
        assertTrue(stub.echoB("B").equals("EchoBase:B"));
        assertTrue(stub.echoC("C").equals("EchoBase3:C"));
        assertTrue(stub.echoD("D").equals("EchoBase3:D"));
        assertTrue(stub.echoE("E").equals("EchoBase2:E"));
        assertTrue(stub.echoF("F").equals("EchoImpl:F"));
        assertTrue(stub.echoG("G").equals("EchoBase3:G"));
        assertTrue(stub.echoH("H").equals("EchoImpl:H"));
    }

//    private void runArray(EchoIF stub) throws Exception {
//        Bar bar = new Bar();
//        bar.setAge(33);
//        Bar bar2 = new Bar();
//        bar2.setAge(44);
//
//        Bar[] barArray = stub.echoBarArray(new Bar[] { bar, bar2 });
//        assertTrue(barArray.length == 2);
//        assertTrue(barArray[0].getAge() == bar.getAge());
//        assertTrue(barArray[1].getAge() == bar2.getAge());
//
//        barArray = stub.echoTwoBar(bar, bar2);
//        assertTrue(barArray.length == 2);
//        assertTrue(barArray[0].getAge() == bar.getAge());
//        System.out.println(barArray[1].getAge());
//        System.out.println(bar2.getAge());
//        assertTrue(barArray[1].getAge() == bar2.getAge());
//    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(EchoClient.class);
        return suite;
    }

}

