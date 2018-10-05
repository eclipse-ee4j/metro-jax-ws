/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.xmime_501.client;

import junit.framework.TestCase;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

/**
 * Test for issue 501
 *
 * @author Jitendra Kotamraju
 */
public class XmimeTest extends TestCase {

    private Hello proxy;

    public XmimeTest(String name) throws Exception{
        super(name);
    }

    protected void setUp() throws Exception {
        proxy = new HelloService().getHelloPort();
    }

    public void testMtomInOut() throws Exception {
    	proxy.mtomInOut(getSource("gpsXml.xml"));
    }

    private StreamSource getSource(String file) throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        return new StreamSource(is);
    }

}
