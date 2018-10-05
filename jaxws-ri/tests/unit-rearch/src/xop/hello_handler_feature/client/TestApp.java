/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package xop.hello_handler_feature.client;

import junit.framework.TestCase;
import testutil.AttachmentHelper;
import testutil.ClientServerTestUtil;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.soap.MTOMFeature;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import com.sun.xml.ws.developer.JAXWSProperties;


public class TestApp extends TestCase{
    private static Hello port;
    public TestApp(String name) throws Exception{
        super(name);

//        Object obj = ClientServerTestUtil.getPort(HelloService.class, Hello.class, new QName("http://example.org/mtom", "HelloPort"));
        HelloService helloService = new HelloService();
        Object obj = helloService.getHelloPort(new MTOMFeature());
        assertTrue(obj != null);
        ClientServerTestUtil.setTransport(obj);
        port = (Hello)obj;
    }

    public void testMtom() throws Exception{
        ((BindingProvider)port).getRequestContext().put(JAXWSProperties.MTOM_THRESHOLOD_VALUE, 0);
        String name="Duke";
        Holder<byte[]> photo = new Holder<byte[]>(name.getBytes());
        Holder<Image> image = new Holder<Image>(getImage("java.jpg"));
        port.detail(photo, image);
        //Mtom attachments are not visible to the applications, so commenting out this testing
//        Map<String, Object> responseContext = ((BindingProvider)port).getResponseContext();
//        String conneg = (String)((BindingProvider)port).getRequestContext().get(JAXWSProperties.CONTENT_NEGOTIATION_PROPERTY);
//        if (conneg == null || !conneg.equalsIgnoreCase("optimistic")) {
//            Map<String, DataHandler> attachments = (Map<String, DataHandler>)((BindingProvider)port).getResponseContext().get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);
//            assertTrue(attachments.size() == 2);
//        }
        assertTrue(new String(photo.value).equals(name));
        assertTrue(AttachmentHelper.compareImages(getImage("java.jpg"), image.value));
    }

    public void testEcho() throws Exception{
        ((BindingProvider)port).getRequestContext().put(JAXWSProperties.MTOM_THRESHOLOD_VALUE, 2000);
        byte[] bytes = AttachmentHelper.getImageBytes(getImage("java.jpg"), "image/jpeg");
        Holder<byte[]> image = new Holder<byte[]>(bytes);
        port.echoData(image);
        assertTrue(Arrays.equals(bytes, image.value));
    }

    public void testSwarefSource() throws Exception{
        DataHandler claimForm = new DataHandler(getFileAsStreamSource("gpsXml.xml"), "text/xml");
        DataHandler out = port.claimForm(claimForm);
        assertTrue(AttachmentHelper.compareSource(getFileAsStreamSource("gpsXml.xml"), (StreamSource)out.getContent()));
    }

    public void testSwarefImage() throws Exception{
        DataHandler claimForm = new DataHandler(getImage("java.jpg"), "image/jpeg");
        DataHandler out = port.claimForm(claimForm);
        //assertTrue(AttachmentHelper.compareSource(getFileAsStreamSource("gpsXml.xml"), (StreamSource)out.getContent()));
        assertTrue(AttachmentHelper.compareImages(getImage("java.jpg"), (Image)out.getContent()));
    }

    private Image getImage(String imageName) throws Exception {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(imageName);
        return javax.imageio.ImageIO.read(is);
    }

    private StreamSource getFileAsStreamSource(String fileName) throws Exception {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
        return new StreamSource(is);
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("uselocal", "true");
        TestApp testor = new TestApp("TestApp");
//        testor.testMtom();
    }

}
