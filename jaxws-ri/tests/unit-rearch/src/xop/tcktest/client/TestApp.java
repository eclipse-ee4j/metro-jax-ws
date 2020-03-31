/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package xop.tcktest.client;

import junit.framework.TestCase;
import testutil.AttachmentHelper;
import testutil.ClientServerTestUtil;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.soap.MTOMFeature;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.soap.SOAPBinding;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;

import com.sun.xml.ws.developer.JAXWSProperties;

/**
 * @author Jitendra Kotamraju
 */
public class TestApp extends TestCase{

    private Hello proxy;

    public TestApp(String name) throws Exception{
        super(name);
        proxy = new HelloService().getHelloPort(new MTOMFeature());
        ClientServerTestUtil.setTransport(proxy);
    }

    public void testMtomIn() throws Exception {
        DataType dt = new DataType();
        dt.setDoc1(getSource("gpsXml.xml"));
        dt.setDoc2(getSource("gpsXml.xml"));
        // This not working since DCH is not registerd by JAX-WS
        //dt.setDoc3(new DataHandler(getSource("gpsXml.xml"), "text/xml"));
        dt.setDoc3(getDataHandler("gpsXml.xml"));
        dt.setDoc4(getImage("java.jpg"));

        String works = proxy.mtomIn(dt);
        assertEquals("works", works);
    }

    private Image getImage(String image) throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(image);
        return javax.imageio.ImageIO.read(is);
    }

    private StreamSource getSource(String file) throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        return new StreamSource(is);
    }

    private DataHandler getDataHandler(final String file) throws Exception {
        return new DataHandler(new DataSource() {
            public String getContentType() {
                return "text/html";
            }

            public InputStream getInputStream() {
                return getClass().getClassLoader().getResourceAsStream(file);
            }

            public String getName() {
                return null;
            }

            public OutputStream getOutputStream() {
                throw new UnsupportedOperationException();
            }
        });
    }

}
