/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom.charset.client;

import junit.framework.TestCase;
import testutil.AttachmentHelper;
import testutil.ClientServerTestUtil;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.soap.MTOMFeature;
import jakarta.xml.ws.Holder;
import java.util.*;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Jitendra Kotamraju
 */
public class EncodingTest extends TestCase {

    private HelloImpl proxy;
    private Random random = new Random();

    public EncodingTest(String name) throws Exception{
        super(name);
    }

    protected void setUp() throws Exception {
        proxy = new HelloImplService().getHelloImplPort(new MTOMFeature());
    }

    public void testEchoBinaryAsString() throws Exception {
        String exp = new String(new char[] { 0xfffb, 'a', 'b', '<' });
        String str = proxy.echoBinaryAsString(exp.getBytes("UTF-8"));
        assertEquals(exp, str);
    }

    public void testEchoBinaryAsString16() throws Exception {
        String exp = new String(new char[] { 0xfffb, 'a', 'b', '<' });
        String str = proxy.echoBinaryAsString16(exp.getBytes("UTF-16"));
        assertEquals(exp, str);
    }

    public void testEchoString() throws Exception {
        String exp = new String(new char[] { 0xfffb, 'a', 'b', '<' });
        String str = proxy.echoString(exp);
        assertEquals(exp, str);
    }

}
