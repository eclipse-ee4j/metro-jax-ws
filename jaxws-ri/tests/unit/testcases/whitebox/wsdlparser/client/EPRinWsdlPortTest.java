/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.wsdlparser.client;

import com.sun.xml.ws.wsdl.parser.RuntimeWSDLParser;
import com.sun.xml.ws.api.model.wsdl.*;
import com.sun.xml.ws.util.xml.XmlCatalogUtil;
import junit.framework.TestCase;
import org.xml.sax.EntityResolver;
import com.sun.xml.ws.api.addressing.WSEndpointReference;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.transform.stream.StreamSource;
import java.net.URL;


/**
 * Tests to verify that the EndpointReference inside wsdl:port is captured correctly so that it can be used as the destination EPR.
 * Tests verify that the ns declarations on the golbal/parent elements is captured in to the EPR infoset.
 *
 * @author Rama Pulavarthi
 */
public class EPRinWsdlPortTest extends TestCase {
    public void test1() throws Exception {
        URL fileurl = getResource("hello_literal_identity.wsdl");
        WSDLModel doc = RuntimeWSDLParser.parse(fileurl, new StreamSource(fileurl.toExternalForm()), getResolver(), true, null);
        WSDLService service = doc.getService(new QName("urn:test", "Hello"));
        WSDLPort port = service.getFirstPort();
        WSEndpointReference wsepr = port.getExtension(WSEndpointReference.class);
        QName q = new QName("http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", "Identity");
        WSEndpointReference.EPRExtension eprExtn = wsepr.getEPRExtension(q);
        XMLStreamReader xsr = eprExtn.readAsXMLStreamReader();
        if (xsr.getEventType() == XMLStreamConstants.START_DOCUMENT)
            xsr.next();
        assertEquals(q.getNamespaceURI(), xsr.getNamespaceURI());
        assertEquals(q.getLocalPart(), xsr.getLocalName());

    }

    private static EntityResolver getResolver() {
        EntityResolver resolver = null;
        if (resolver == null) {
            resolver = XmlCatalogUtil.createDefaultCatalogResolver();
        }

        return resolver;
    }

    public URL getResource(String s) {
        return getClass().getClassLoader().getResource(s);
    }

}
