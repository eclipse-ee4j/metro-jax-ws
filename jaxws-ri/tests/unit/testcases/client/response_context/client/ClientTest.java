/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.response_context.client;

import junit.framework.TestCase;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import java.util.Map;

/**
 * @author Jitendra Kotamraju
 */
public class ClientTest extends TestCase {

    private Hello helloPort = new Hello_Service().getHelloPort();
    private String helloPortAddress = (String) ((BindingProvider) helloPort).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);

    Dispatch createDispatchJAXB() {
        Service service = Service.create(new QName("urn:test", "Hello"));
        JAXBContext context = createJAXBContext(ObjectFactory.class);
        QName portQName = new QName("urn:test", "HelloPort");
        service.addPort(portQName,
                "http://schemas.xmlsoap.org/wsdl/soap/http",
                helloPortAddress);
        Dispatch dispatch = service.createDispatch(portQName, context, Service.Mode.PAYLOAD);
        return dispatch;
    }

    JAXBContext createJAXBContext(Class<?> objectFactory) {
        try {
            return JAXBContext.newInstance(new Class[]{objectFactory});
        } catch (JAXBException e) {
            e.printStackTrace();
            fail("Error creating JAXBContext");
            return null;
        }
    }

    public ClientTest(String name) throws Exception {
        super(name);
    }

    public void testResponseContext1() throws Exception {
        try {
            Hello_Type req = new Hello_Type();
            req.setArgument("foo");
            req.setExtra("bar");

            ((BindingProvider) helloPort).getRequestContext().put(
                    BindingProvider.ENDPOINT_ADDRESS_PROPERTY, helloPortAddress);

            HelloResponse response = helloPort.hello(req);
        } catch (Exception e) {
            Map rc = ((BindingProvider) helloPort).getResponseContext();
            assertNotNull(rc);
            assertNotNull(rc.get(javax.xml.ws.handler.MessageContext.HTTP_RESPONSE_CODE));
            assertEquals(200,rc.get(javax.xml.ws.handler.MessageContext.HTTP_RESPONSE_CODE));
        }
    }

    public void testResponseContext2() throws Exception {
        Object ctx = ((BindingProvider)helloPort).getResponseContext();
        System.out.println("ctx = " + ctx);
        assertNull(ctx);
    }

    public void testResponseContext3() throws Exception {
        Dispatch dispatch = createDispatchJAXB();
        try {
            Hello_Type req = new Hello_Type();
            req.setArgument("foo");
            req.setExtra("bar");

            dispatch.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, helloPortAddress.toString());
            dispatch.invoke(req);
        } catch (Exception e) {
            Map rc = dispatch.getResponseContext();
            assertEquals(200,rc.get(javax.xml.ws.handler.MessageContext.HTTP_RESPONSE_CODE));
        }
    }

    public void testResponseContext4() throws Exception {
        Dispatch dispatch = createDispatchJAXB();
        assertNull(dispatch.getResponseContext());
    }

}
