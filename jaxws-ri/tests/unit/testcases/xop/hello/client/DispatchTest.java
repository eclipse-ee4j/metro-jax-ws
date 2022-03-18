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

import testutil.AttachmentHelper;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.SOAPBinding;
import java.awt.*;
import java.io.InputStream;

import com.sun.xml.ws.developer.JAXWSProperties;
import junit.framework.TestCase;

public class DispatchTest extends TestCase {

    private final QName portQName = new QName("http://example.org/mtom", "HelloPort");
    private Service service;

    public DispatchTest(String name) throws Exception {
        super(name);
    }

    private static jakarta.xml.bind.JAXBContext createJAXBContext() {
        try {
            return jakarta.xml.bind.JAXBContext.newInstance(ObjectFactory.class);
        } catch (Exception e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    public void testMtom() throws Exception {
        service = new HelloService();
        JAXBContext context = createJAXBContext();
        Dispatch dispatch = service.createDispatch(portQName, context, Service.Mode.PAYLOAD);
        SOAPBinding binding = (SOAPBinding) ((BindingProvider) dispatch).getBinding();
        binding.setMTOMEnabled(true);
        ((BindingProvider) dispatch).getRequestContext().put(JAXWSProperties.MTOM_THRESHOLOD_VALUE, 0);
        String name = "Duke";
        DetailType detailType = new DetailType();
        detailType.setPhoto(name.getBytes());
        detailType.setImage(getImage("java.jpg"));
        ObjectFactory of = new ObjectFactory();
        JAXBElement<DetailType> jaxbe = of.createDetail(detailType);
        JAXBElement<DetailType> response = (JAXBElement<DetailType>) dispatch.invoke(jaxbe);
        DetailType result = response.getValue();
        assertTrue(result != null);
        byte[] photo = result.getPhoto();
        Image image = result.getImage();

        assertTrue(new String(photo).equals(name));
        assertTrue(AttachmentHelper.compareImages(getImage("java.jpg"), image));
    }

    private Image getImage(String imageName) throws Exception {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(imageName);
        return javax.imageio.ImageIO.read(is);
    }
}
