/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package xop.hello_soap12.client;

import junit.framework.TestCase;
import testutil.AttachmentHelper;
import testutil.ClientServerTestUtil;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.soap.SOAPBinding;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.w3c.dom.Node;



public class TestApp extends TestCase{
    private static Hello port;
    public TestApp(String name) throws Exception{
        super(name);
//        Object obj = ClientServerTestUtil.getPort(HelloService.class, Hello.class, new QName("http://example.org/mtom", "HelloPort"));
//        assertTrue(obj != null);
        HelloService helloService = new HelloService();
        Object obj = helloService.getHelloPort();
        assertTrue(obj != null);
        ClientServerTestUtil.setTransport(obj);

        //set Mtom optimization.
        //set Mtom optimization.
        SOAPBinding binding = (SOAPBinding)((BindingProvider)obj).getBinding();
        binding.setMTOMEnabled(true);
        port = (Hello)obj;
    }

    public void testMtom() throws Exception{
        String name="Duke";
        Holder<byte[]> photo = new Holder<byte[]>(name.getBytes());
        Holder<Image> image = new Holder<Image>(getImage("java.jpg"));
        port.detail(photo, image);
        assertTrue(new String(photo.value).equals(name));
        assertTrue(AttachmentHelper.compareImages(getImage("java.jpg"), image.value));
    }

    public void testSwaref() throws Exception{
        DataHandler claimForm = new DataHandler(getFileAsStreamSource("gpsXml.xml"), "text/xml");
        DataHandler out = port.claimForm(claimForm);
        assertTrue(AttachmentHelper.compareSource(getFileAsStreamSource("gpsXml.xml"), (Source)out.getContent()));
    }

    private Image getImage(String imageName) throws Exception {
        String location = getDataDir() + imageName;
        return javax.imageio.ImageIO.read(new File(location));
    }

    private String getDataDir() {
        String userDir = System.getProperty("user.dir");
        String sepChar = System.getProperty("file.separator");
        return userDir+sepChar+ "src/xop/hello/common_resources/WEB-INF/";
    }

    private StreamSource getFileAsStreamSource(String fileName)
        throws Exception {
        InputStream is = null;
        String location = getDataDir() + fileName;
        File f = new File(location);
        FileInputStream fis = new FileInputStream(f);
        return new StreamSource(fis);
    }





}
