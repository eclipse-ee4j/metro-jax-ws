/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.jaxws102.client;

import junit.framework.TestCase;

import javax.xml.ws.Holder;

/**
 * @author Vivek Pandey
 */
public class Client extends TestCase {

    private BenchMarkPortType port1;
    private BenchMarkSubBPPortType port2;
    public Client(String name) {
        super(name);
        port1 = new BenchMarkService().getBenchMarkPort();
        port2 = new BenchMarkSubBPService().getBenchMarkSubPort();
    }

    public void testBenchMarkPortTypeOp1(){
        BenchMarkType bm = new BenchMarkType();
        bm.setString("Type1");
        Holder<BenchMarkType> type = new Holder<BenchMarkType>(bm);
        port1.benchMarkOperation1(type);
        assertTrue(type.value.getString().equals("Type2"));
    }

    public void testBenchMarkPortTypeOp2(){
        BenchMarkType type = new BenchMarkType();
        type.setString("Type2");
        port1.benchMarkOperation2(type);
    }

    public void testBenchMarkPortSubBPTypeOp(){
        BenchMarkType bm = new BenchMarkType();
        bm.setString("BPType1");
        Holder<BenchMarkType> type = new Holder<BenchMarkType>(bm);
        port2.benchMarkSubBPOperation1(type);
        System.out.println("REceived: "+type.value.getString());
        assertTrue(type.value.getString().equals("BPType2"));
    }
}
