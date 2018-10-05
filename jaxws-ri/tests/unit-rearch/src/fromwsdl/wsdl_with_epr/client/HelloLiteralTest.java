/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.wsdl_with_epr.client;

import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.soap.AddressingFeature;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;
import javax.xml.bind.JAXBContext;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.sun.xml.ws.util.DOMUtil;


/**
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    // main method added for debugging
    public static void main(String [] args) {
        try {
            System.setProperty("uselocal", "true");
            HelloLiteralTest test = new HelloLiteralTest("HelloLiteralTest");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Hello stub;

    public HelloLiteralTest(String name) throws Exception {
        super(name);
        Hello_Service service = new Hello_Service();

        stub = service.getHelloPort();
        ClientServerTestUtil.setTransport(stub);
    }

    //tests EPR in wsdl
    // wsdl epr has two reference parameters
    public void testHello() throws Exception {
        try {
            String arg = "foo";
            String extra = "bar";
            Hello_Type req = new Hello_Type();
            req.setArgument(arg);
            req.setExtra(extra);
            HelloResponse response = stub.hello(req);
            assertEquals(arg, response.getArgument());
            assertEquals(extra, response.getExtra());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    //tests EPR in wsdl
    // wsdl epr has two reference parameters
    public void testHelloDispatch() throws Exception {
        try {
            String arg = "foo";
            String extra = "bar";
            Hello_Type req = new Hello_Type();
            req.setArgument(arg);
            req.setExtra(extra);
            Hello_Service service = new Hello_Service();
            JAXBContext jc = javax.xml.bind.JAXBContext.newInstance(fromwsdl.wsdl_with_epr.client.ObjectFactory.class);
            QName port = new QName("urn:test","HelloPort");
            Dispatch<Object> dispatch = service.createDispatch(port, jc, Service.Mode.PAYLOAD, new AddressingFeature());
            HelloResponse response = (HelloResponse) dispatch.invoke(req);
            assertEquals(arg, response.getArgument());
            assertEquals(extra, response.getExtra());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    //Tests epr overriding
    //epr(1 reference parameter) passed as argument overrides epr in wsdl(2 reference parameters)
    public void testEchoArray() throws Exception {
        try {
            String xmlParam1 = "<myns:MyParam1 xmlns:myns=\"http://cptestservice.org/wsdl\">Hello</myns:MyParam1>";
            Node n1 = DOMUtil.createDOMNode(new ByteArrayInputStream(xmlParam1.getBytes()));
            String endpointAddress = (String) ((BindingProvider) stub).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
            W3CEndpointReferenceBuilder eprBuilder = new W3CEndpointReferenceBuilder();
            eprBuilder.address(endpointAddress);
            eprBuilder.referenceParameter((Element) n1.getFirstChild());
            W3CEndpointReference epr = eprBuilder.build();
            Hello_Service service = new Hello_Service();
            Hello newStub = service.getPort(epr, Hello.class);

            String[] in = {"JAXRPC 1.0", "JAXRPC 1.1", "JAXRPC 1.1.2", "JAXRPC 2.0"};
            NameType nt = new NameType();
            nt.getName().add(in[0]);
            nt.getName().add(in[1]);
            nt.getName().add(in[2]);
            nt.getName().add(in[3]);
            javax.xml.ws.Holder<NameType> req = new javax.xml.ws.Holder<NameType>(nt);
            newStub.echoArray(req);
            assertTrue(req.value == null);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }


}
