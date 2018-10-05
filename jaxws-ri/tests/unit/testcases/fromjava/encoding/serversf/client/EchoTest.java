/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.encoding.serversf.client;

import com.sun.xml.ws.developer.SerializationFeature;
import junit.framework.TestCase;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;

/**
 * @author Jitendra Kotamraju
 */
public class EchoTest extends TestCase {

    public void testSerializationFeature() throws Exception {
        Echo echoPort = new EchoService().getEchoPort(new SerializationFeature());
        echoPort.echoString("utf-8");
        // Server sends responses encoded with UTF-16
        testCharset(echoPort, "utf-16");
    }

    public void testSerializationFeatureUtf8() throws Exception {
        String encoding = "UTF-8";
        Echo echoPort = new EchoService().getEchoPort(new SerializationFeature(encoding));
        echoPort.echoString(encoding);
        // Server sends responses encoded with UTF-16
        testCharset(echoPort, "utf-16");
    }

    public void testSerializationFeatureUtf16() throws Exception {
        String encoding = "UTF-16";
        Echo echoPort = new EchoService().getEchoPort(new SerializationFeature(encoding));
        echoPort.echoString(encoding);
        // Server sends responses encoded with UTF-16
        testCharset(echoPort, "utf-16");
    }

    public void testExceptionUtf8() throws Exception {
        String encoding = "UTF-8";
        Echo echoPort = new EchoService().getEchoPort(new SerializationFeature(encoding));
        try {
            echoPort.echoString("Exception1");
            fail("Exception1");
        } catch (Exception1_Exception e) {
            Exception1 ex = e.getFaultInfo();
            assertEquals("my exception1", ex.getFaultString());
            assertTrue(ex.isValid());
        }
        // Server sends responses encoded with UTF-16
        testCharset(echoPort, "utf-16");
    }

    public void testExceptionUtf16() throws Exception {
        String encoding = "UTF-16";
        Echo echoPort = new EchoService().getEchoPort(new SerializationFeature(encoding));
        try {
            echoPort.echoString("Fault1");
            fail("Fault1");
        } catch (Fault1 e) {
            FooException ex = e.getFaultInfo();
            assertEquals("fault1", e.getMessage());
            assertEquals(44F, ex.getVarFloat());
            assertEquals((int)33, (int)ex.getVarInt());
            assertEquals("foo", ex.getVarString());
        }
        // Server sends responses encoded with UTF-16
        testCharset(echoPort, "utf-16");
    }

    public void testExceptionJIS() throws Exception {
        String encoding = "Shift_JIS";
        Echo echoPort = new EchoService().getEchoPort(new SerializationFeature(encoding));
        try {
            echoPort.echoString("WSDLBarException");
            fail("WSDLBarException");
        } catch (WSDLBarException e) {
            Bar ex = e.getFaultInfo();
            assertEquals("my barException", e.getMessage());
            assertEquals(33, ex.getAge());
        }
        // Server sends responses encoded with UTF-16
        testCharset(echoPort, "utf-16");
    }

    private void testCharset(Echo proxy, String encoding) {
        Map<String, List<String>> headers =
            (Map<String, List<String>>)((BindingProvider)proxy).getResponseContext().get(MessageContext.HTTP_RESPONSE_HEADERS);
        String s = headers.get("Content-Type").get(0).toLowerCase();
        assertTrue(s.contains(encoding.toLowerCase()));
    }

}
