/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.mime.text_plain_754.client;

import junit.framework.TestCase;

import javax.xml.ws.Holder;


/**
 * Test case for issue: 754 - tests text/plain in mime binding
 *
 * @author Jitendra Kotamraju
 */
public class HelloLiteralTest extends TestCase {

    private static CatalogPortType port;

    public HelloLiteralTest(String name) throws Exception {
        super(name);
    }

    @Override
    public void setUp() {
        CatalogService service = new CatalogService();
        port = service.getCatalogPort();
    }

    public void testEchoString() throws Exception {
        Holder<String> outStr = new Holder<String>("output");
        Holder<String> att = new Holder<String>();
        port.echoString("input", "attInput", outStr, att);
        assertEquals("output", outStr.value);
        assertEquals("att", att.value);
    }

}
