/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package benchmark.rpclit.client;

/**
 * @author JAX-RPC RI Development Team
 */
public class EchoBenchmark extends RpclitTest {
    public EchoBenchmark(String name) throws Exception {
        super(name);
    }

    public void testOnce() throws Exception {
        getStub().echoVoid();
        getStub().echoBoolean(true);
        getStub().echoString(string);
        getStub().echoInteger(intNumber);
        getStub().echoFloat(floatNumber);
        getStub().echoComplexType(complexType);
        getStub().echoBase64(base64Binary);
        getStub().echoDate(gregorianDate);
        getStub().echoDecimal(decimal);
        getStub().echoEnum(enumBitFive);
        getStub().echoNestedComplexType(nestedComplexType);

        getStub().echoStringArray(rpclitStringArray);
        getStub().echoIntegerArray(rpclitIntArray);
        getStub().echoFloatArray(rpclitFloatArray);
        getStub().echoComplexTypeArray(complexTypeArray);
    }
}
