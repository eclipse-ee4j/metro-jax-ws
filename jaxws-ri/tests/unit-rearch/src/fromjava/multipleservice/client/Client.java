/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.multipleservice.client;

import junit.framework.TestCase;
import fromjava.multipleservice.server.one.ServiceOne;
import fromjava.multipleservice.server.one.ServiceOneService;
import fromjava.multipleservice.server.two.ServiceTwoService;
import fromjava.multipleservice.server.two.ServiceTwo;

public class Client extends TestCase {

    public Client(String name) {
        super(name);
    }

    public void testServiceOne(){
        ServiceOneService service = new ServiceOneService();
        ServiceOne port = service.getServiceOnePort();
        String resp = port.echoOne("Hello1");
        assertEquals(resp, "Hello1");
    }

    public void testServiceTwo(){
        ServiceTwoService service = new ServiceTwoService();
        ServiceTwo port = service.getServiceTwoPort();
        String resp = port.echoTwo("Hello2");
        assertEquals(resp, "Hello2");
    }

}
