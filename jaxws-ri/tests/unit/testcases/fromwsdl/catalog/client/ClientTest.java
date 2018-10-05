/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.catalog.client;

import junit.framework.TestCase;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.net.URL;

/**
 * @author Jitendra Kotamraju
 */
public class ClientTest extends TestCase {

    private Hello helloPort = new Hello_Service().getHelloPort();

    public ClientTest(String name) throws Exception {
        super(name);
    }

    public void test1() throws Exception {
        //import javax.xml.ws.handler.MessageContext;
        // http://localhost:8080/fromwsdl.catalog.server/HelloImpl
        String originalAddress = (String) ((BindingProvider) helloPort).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);

        //now make catalog effective and get dummy address from the resolved wsdl
        Hello_Service newService = new Hello_Service(new
                URL("http://example.com/fromwsdl.catalog.server/HelloImpl?wsdl"), new QName("urn:test", "Hello"));
        Hello newPort = newService.getHelloPort();
        String newAddress = (String) ((BindingProvider) newPort).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        //Make sure the catalog was effective
        assertEquals("http://example.com/service", newAddress);

        //reset the address to original working address
        ((BindingProvider) newPort).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, originalAddress);
        String arg = "foo";
        String extra = "bar";
        Hello_Type req = new Hello_Type();
        req.setArgument(arg);
        req.setExtra(extra);
        HelloResponse response = newPort.hello(req);
        assertEquals(arg, response.getArgument());
        assertEquals(extra, response.getExtra());
        System.out.println("MIRAN: TEST finished.");
    }

}
