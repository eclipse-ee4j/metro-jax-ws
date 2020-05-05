/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.jaxws563.client;

import java.io.*;
import java.math.BigInteger;

import junit.framework.TestCase;

/**
 * @author Jitendra Kotamraju
 */
public class SchemaTest extends TestCase {

    public void testSchema() throws IOException {
        Query query = new Query();
        query.setFoo(new BigInteger("10"));
        query.setBar(new BigInteger("20"));
        Calculator calculator = new CalculatorService().getCalculatorPort();
        Result result = calculator.add(query);
        assertEquals(new BigInteger("30"),result.getSum());
    }

}
