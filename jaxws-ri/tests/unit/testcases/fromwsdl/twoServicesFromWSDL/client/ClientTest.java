/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.twoServicesFromWSDL.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;

public class ClientTest extends TestCase {

    private static final List<Integer> values = new ArrayList<Integer>(Arrays.asList(new Integer[]{1142, 2242}));

    public ClientTest(String name) {
        super(name);
    }

    public void testWs1Client() throws Exception {
        AddNumbersPortType port = new AddNumbersService2().getAddNumbersPort();
        assertTrue("WS1 invocation failed." , values.remove(Integer.valueOf(port.addNumbers(1, 2))));
    }

    public void testWs2Client() throws Exception {
        fromwsdl.twoServicesFromWSDL.client2.AddNumbersPortType port
                = new fromwsdl.twoServicesFromWSDL.client2.AddNumbersService().getAddNumbersPort();
        assertTrue("WS2 invocation failed.", values.remove(Integer.valueOf(port.addNumbers(1, 2))));
    }
}
