/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei_default.client;

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
    private static EchoImpl stub;
    ClientServerTestUtil util = new ClientServerTestUtil();

    public EchoClient(String name) throws Exception{
        super(name);
        EchoImplService service = new EchoImplService();
        stub = service.getEchoImplPort();      
        ClientServerTestUtil.setTransport(stub);   
    }

    public void testSimple() throws Exception {
        Bar bar = new Bar();
        bar.setAge(33);

        assertTrue(stub.echoString("test").equals("test"));
        assertTrue(stub.echoString("Mary & Paul").equals("Mary & Paul"));
        assertTrue(stub.echoBar(bar).getAge() == bar.getAge());
        
        Class2 cls = new Class2();
        cls.setAge(33);
        assertTrue(stub.echoClass2(cls).getAge() == cls.getAge());
    }

    public void testArray1() throws Exception {
        List<String> strArray = new ArrayList<String>();
        strArray.add("Mary");
        strArray.add("Paul");

        List<String> returnArray = stub.echoStringArray(strArray);
        assertTrue(returnArray.size() == strArray.size());
        assertTrue(returnArray.get(0).equals(strArray.get(0)));
        assertTrue(returnArray.get(1).equals(strArray.get(1)));
    }

    public void testArray2() throws Exception {
        Bar bar = new Bar();
        bar.setAge(33);
        Bar bar2 = new Bar();
        bar2.setAge(44);

        List<Bar> barArray = new ArrayList<Bar>();
        barArray.add(bar);
        barArray.add(bar2);
        List<Bar> resultArray = stub.echoBarArray(barArray);
        assertTrue(resultArray.size() == 2);
        assertTrue(resultArray.get(0).getAge() == bar.getAge());
        assertTrue(resultArray.get(1).getAge() == bar2.getAge());
    }

    public void testArray3() throws Exception {
        Bar bar = new Bar();
        bar.setAge(33);
        Bar bar2 = new Bar();
        bar2.setAge(44);

        List<Bar> barArray = stub.echoTwoBar(bar, bar2);
        assertTrue(barArray.size() == 2);
//System.out.println("1");
        assertTrue(barArray.get(0).getAge() == bar.getAge());
//System.out.println("2");
        assertTrue(barArray.get(1).getAge() == bar2.getAge());
//System.out.println("3");
    }

    public void testOneway() throws Exception {
        stub.oneway();
        assertTrue(true);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(EchoClient.class);
        return suite;
    }

}

