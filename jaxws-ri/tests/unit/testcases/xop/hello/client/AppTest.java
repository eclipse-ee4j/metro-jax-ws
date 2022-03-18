/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package xop.hello.client;

import com.sun.xml.ws.Closeable;
import junit.framework.TestCase;
import testutil.AttachmentHelper;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.soap.SOAPBinding;
import java.awt.*;
import java.io.InputStream;
import java.util.Arrays;

import com.sun.xml.ws.developer.JAXWSProperties;

public class AppTest extends TestCase {
    
    private static Hello port;
    
    public AppTest(String name) throws Exception{
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        HelloService helloService = new HelloService();
        Object obj = helloService.getHelloPort();
        assertTrue(obj != null);

        //set Mtom optimization.
        SOAPBinding binding = (SOAPBinding)((BindingProvider)obj).getBinding();
        binding.setMTOMEnabled(true);
        port = (Hello)obj;
    }

    public void testMtom() throws Exception {
        String name="Duke";
        Holder<byte[]> photo = new Holder<byte[]>(name.getBytes());
        Holder<Image> image = new Holder<Image>(getImage("java.jpg"));
        port.detail(photo, image);

        assertTrue(new String(photo.value).equals(name));
        assertTrue(AttachmentHelper.compareImages(getImage("java.jpg"), image.value));
    }

    public void testEcho() throws Exception {
        ((BindingProvider)port).getRequestContext().put(JAXWSProperties.MTOM_THRESHOLOD_VALUE, 10);
        byte[] bytes = AttachmentHelper.getImageBytes(getImage("java.jpg"), "image/jpeg");
        Holder<byte[]> image = new Holder<byte[]>(bytes);
        port.echoData(image);
        assertTrue(Arrays.equals(bytes, image.value));
    }

    private Image getImage(String imageName) throws Exception {
        InputStream is = resource(imageName);
        return javax.imageio.ImageIO.read(is);
    }
    
    private InputStream resource(String file) {
        return getClass().getClassLoader().getResourceAsStream(file);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (port != null) {
            ((Closeable)port).close();
        }
    }
}
