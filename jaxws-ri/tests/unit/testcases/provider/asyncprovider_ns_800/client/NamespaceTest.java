/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.asyncprovider_ns_800.client;

import junit.framework.TestCase;

import jakarta.xml.soap.*;
import jakarta.xml.ws.BindingProvider;
import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;

/**
 * @author Jitendra Kotamraju
 */
public class NamespaceTest extends TestCase {

    public NamespaceTest(String name) throws Exception{
        super(name);
    }
    
    public void testNamespace() throws Exception {
        // Create request message
        String str = "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'><S:Body><ns2:Hello xmlns:ns2='urn:test:types'><argument>arg</argument><extra>extra</extra></ns2:Hello></S:Body></S:Envelope>";
        MessageFactory fact = MessageFactory.newInstance();
        MimeHeaders headers = new MimeHeaders();
        SOAPMessage req = fact.createMessage(headers, new ByteArrayInputStream(str.getBytes("UTF-8")));

        // Get address
        Hello hello = new Hello_Service().getHelloAsyncPort();
        BindingProvider bp = (BindingProvider)hello;
        String address = (String) bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);

        // Get response message
        SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
        SOAPConnection con = factory.createConnection();
        SOAPMessage res = con.call(req, address);
        con.close();

        // Verify namespaces
        SOAPElement elem = (SOAPElement)res.getSOAPBody().getChildElements().next();
        elem = (SOAPElement)elem.getChildElements().next();
        QName expected = new QName("http://xml.netbeans.org/schema/dataTypes.xsd", "Element20");
        assertEquals(expected, elem.getElementQName());
        assertEquals("http://j2ee.netbeans.org/wsdl/QNameAssignment_WithStructuredPart", elem.getNamespaceURI("msgns"));
    }

}
