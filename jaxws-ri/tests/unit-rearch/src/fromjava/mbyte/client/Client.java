/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.mbyte.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

public class Client extends TestCase {

    public Client(String name) {
        super(name);
    }


    public void testMultibyteService(){
        if (ClientServerTestUtil.useLocal()) {
            System.out.println("Runs only in HTTP!");
            return;
        }
        HelloService service = new HelloService();
        Hello proxy = service.getHelloPort();
        String resp = proxy.echo("Hello\u00EEService");
        assertEquals(resp, "Hello\u00EEService");
    }




}
