/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.multiple_body.client;

import junit.framework.TestCase;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPBody;

import com.sun.xml.ws.api.SOAPVersion;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import testutil.ClientServerTestUtil;

public class Client extends TestCase {

    public Client(String name) {
        super(name);
    }

    public void testMultipleBodies() throws SOAPException {
        if (ClientServerTestUtil.useLocal()) {
            System.out.println("Runs only in HTTP!");
            return;
        }
        Service service = Service.create(new QName("urn:test", "Endpoint"));
        service.addPort(new QName("urn:test", "EndpointPort"), SOAPBinding.SOAP11HTTP_BINDING, "http://localhost:8080/jaxrpc-provider_tests_multiple_body/endpoint");
        Dispatch<SOAPMessage> disp = service.createDispatch(new QName("urn:test", "EndpointPort"), SOAPMessage.class, Service.Mode.MESSAGE);
        SOAPMessage sm = SOAPVersion.SOAP_11.saajMessageFactory.createMessage();
        SOAPBody sb = sm.getSOAPBody();
        sb.addBodyElement(new QName("http://first.body", "first"));
        sb.addBodyElement(new QName("http://second.body", "second"));
        SOAPMessage resp = disp.invoke(sm);

        SOAPBody body = resp.getSOAPBody();
        NodeList nl = body.getChildNodes();
        assertTrue(nl.getLength() == 2);
        Node n = nl.item(0);
        assertTrue(n.getLocalName().equals("first") && n.getNamespaceURI().equals("http://first.body"));
        n = nl.item(1);
        assertTrue(n.getLocalName().equals("second") && n.getNamespaceURI().equals("http://second.body"));
    }
}
