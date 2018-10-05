/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws692.client;

import junit.framework.*;

/**
 * Test type described message part.
 * 
 * @author Martin Grebac
 */
public class SEITest extends TestCase {

    public SEITest(String name) throws Exception{
        super(name);
    }

    public void testHello1() throws Exception {;
        MyService service = new MyService();
        ServicePort proxy = service.getPort();

        OperationRequest req = new OperationRequest();
        req.setRequest("request111");
        OperationResponse resp = proxy.operation(req);
        assertEquals("response111", resp.getResponse());
    }

}
