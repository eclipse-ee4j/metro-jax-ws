/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.dynamic_775.client;

import java.util.*;
import junit.framework.TestCase;

/**
 * @author Jitendra Kotamraju
 */
public class EchoTest extends TestCase {
    Echo proxy;

    public void setUp() {
        proxy = new EchoService().getEchoPort();
    }

    public void testEcho() throws Exception {
        proxy.echo("test".getBytes());
    }

    public void testEcho1() throws Exception {
        List<String> list = new ArrayList<String>(); 
        list.add("list");
        proxy.echo1("test".getBytes(), list);
    }

}
