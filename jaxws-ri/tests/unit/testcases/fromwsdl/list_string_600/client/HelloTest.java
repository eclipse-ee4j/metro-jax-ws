/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.list_string_600.client;

import junit.framework.TestCase;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.List;

/**
 * Test for issue 600
 *
 * @author Jitendra Kotamraju
 */
public class HelloTest extends TestCase {

    private HelloServicePortType proxy;

    public HelloTest(String name) throws Exception{
        super(name);
    }

    protected void setUp() throws Exception {
        proxy = new HelloService().getHelloServicePort();
    }

    public void testList() throws Exception {
    	List<String> list = proxy.getAllGuestNames();
        assertEquals(1, list.size());
        assertEquals("sun", list.get(0));
    }

}
