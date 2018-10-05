/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package benchmark.doclit.client;

import java.util.Arrays;

/**
 * @author JAX-RPC RI Development Team
 */
public class EchoBenchmark extends DoclitTest {
    public EchoBenchmark(String name) {
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

        getStub().echoStringArray(Arrays.asList(stringArray));
        getStub().echoIntegerArray(Arrays.asList(intArray));
        getStub().echoFloatArray(Arrays.asList(floatArray));
        getStub().echoComplexTypeArray(Arrays.asList(complexTypeArray));
    }
}
