/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom.issue_606.client;

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
public class BoundaryTest extends TestCase {

    private HelloImpl proxy;

    public BoundaryTest(String name) throws Exception{
        super(name);
    }

    protected void setUp() throws Exception {
        proxy = new HelloImplService().getHelloImplPort(new MTOMFeature());
    }

    public void testEcho() throws Exception {
        for(int i=0; i < 30000; i++) {
            if (i%500 == 0) {
                System.out.println("testEcho():"+i);
            }
            byte[] expected = getBuf(i);
            byte[] got = proxy.echo("file", expected);
            verify(expected, got);
        }
    }

    public void testEcho1() throws Exception {
        byte[] expected = new byte[0];
        String str = "";
        for(int i=0; i < 30000; i++) {
            if (i%500 == 0) {
                System.out.println("testEcho1():"+i);
            }
            str += "a";
            byte[] got = proxy.echo(str, expected);
            verify(expected, got);
        }
    }

    private static byte[] getBuf(int size) {
        byte[] buf = new byte[size];
        for(int i=0; i < size; i++) {
            buf[i] = (byte)i;
        }
        return buf;
    }

    private static void verify(byte[] expected, byte[] got) {
        assertEquals(expected.length, got.length);
        for(int i=0; i < expected.length; i++) {
            if (expected[i] != got[i]) {   //assertEquals seems expensive
                assertEquals(expected[i], got[i]);
            }
        }
    }

}
