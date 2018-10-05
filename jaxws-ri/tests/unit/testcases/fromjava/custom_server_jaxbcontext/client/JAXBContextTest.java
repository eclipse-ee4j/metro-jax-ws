/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.custom_server_jaxbcontext.client;

import junit.framework.TestCase;

/**
 * @author Jitendra Kotamraju
 */
public class JAXBContextTest extends TestCase {

    private EchoImpl proxy;

    public JAXBContextTest(String name) throws Exception {
        super(name);
    }

    protected void setUp() throws Exception {
        proxy = new EchoImplService().getEchoImplPort();
    }

    public void testEcho() throws Exception {
        Toyota toyota = new Toyota();
        toyota.setModel("camry");
        toyota.setYear("2000");
        toyota.setColor("blue");
        Car car = proxy.echo(toyota);
        assertTrue(car instanceof Toyota);
        assertEquals("Toyota", car.getMake());
        assertEquals("camry", car.getModel());
        assertEquals("2000", car.getYear());
        assertEquals("blue", ((Toyota)car).getColor());
    }

}
