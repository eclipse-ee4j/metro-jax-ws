/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.issue_563.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import testutil.ClientServerTestUtil;
import javax.xml.ws.Service;
import java.math.BigInteger;


/**
 * @author JAX-RPC Development Team
 */
public class EchoClient extends TestCase {
    private Service service;
    private static Calculator stub;

    public EchoClient(String name) throws Exception{
        super(name);
        CalculatorService service = new CalculatorService();
        stub = service.getCalculatorPort();
        ClientServerTestUtil.setTransport(stub);   
    }

    public void testSimple() throws Exception {
        Query query = new Query();
        query.setArg0(new BigInteger("10"));
        query.setArg1(new BigInteger("20"));
        Result result = calculatorProxy.add(query);
        System.out.println("Sum of 10+20 = "+result.getSum());
        
    }
}

