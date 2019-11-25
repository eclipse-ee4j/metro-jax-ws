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

import com.sun.xml.ws.util.ByteArrayBuffer;
import com.sun.xml.ws.wsdl.parser.RuntimeWSDLParser;
import com.sun.xml.ws.api.model.wsdl.*;
import junit.framework.TestCase;
import org.xml.sax.EntityResolver;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.util.xml.XmlCatalogUtil;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import java.net.URL;


/**
 * Tests to verify that the EndpointReference inside wsdl:port is captured correctly so that it can be used as the destination EPR.
 * Tests verify that the ns declarations on the golbal/parent elements is captured in to the EPR infoset by writing the
 * infoset and reading it back again
 *
 * @author Rama Pulavarthi
 */
public class EPRWriteNReadTest extends TestCase {
    public void test1() throws Exception {
        URL fileurl = getResource("hello_literal_identity1.wsdl");
        WSDLModel doc = RuntimeWSDLParser.parse(fileurl, new StreamSource(fileurl.toExternalForm()), getResolver(), true, null);
        WSDLService service = doc.getService(new QName("urn:test", "Hello"));
        WSDLPort port = service.getFirstPort();
        WSEndpointReference wsepr = port.getExtension(WSEndpointReference.class);
        verifyEPR(wsepr);
    }

    public void test2() throws Exception {
        URL fileurl = getResource("hello_literal_identity2.wsdl");
        WSDLModel doc = RuntimeWSDLParser.parse(fileurl, new StreamSource(fileurl.toExternalForm()), getResolver(), true, null);
        WSDLService service = doc.getService(new QName("urn:test", "Hello2"));
        WSDLPort port = service.getFirstPort();
        WSEndpointReference wsepr = port.getExtension(WSEndpointReference.class);
        verifyEPR(wsepr);
    }

    public void test3() throws Exception {
        URL fileurl = getResource("hello_literal_identity3.wsdl");
        WSDLModel doc = RuntimeWSDLParser.parse(fileurl, new StreamSource(fileurl.toExternalForm()), getResolver(), true, null);
        WSDLService service = doc.getService(new QName("urn:test", "Hello2"));
        WSDLPort port = service.getFirstPort();
        WSEndpointReference wsepr = port.getExtension(WSEndpointReference.class);
        verifyEPR(wsepr);
    }

    private static void verifyEPR( WSEndpointReference wsepr) throws Exception {
        ByteArrayBuffer bytebuffer = new ByteArrayBuffer();
        XMLStreamWriter w = XMLOutputFactory.newInstance().createXMLStreamWriter(bytebuffer);
        System.out.println("----------------------------------------------------------------------");
        w.writeStartDocument();
        wsepr.writeTo("EndpointReference",w);
        w.writeEndDocument();
        System.out.println(bytebuffer.toString());

        //verify the validity of infoset by reading it as EPR back again
        EndpointReference epr = W3CEndpointReference.readFrom(new StreamSource(bytebuffer.newInputStream()));

        //Verify it good old way
        XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(new StreamSource(bytebuffer.newInputStream()));
        QName q = new QName("http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", "Identity");
        while(xsr.hasNext()) {
            xsr.next();
            if(xsr.getEventType() == XMLStreamConstants.START_ELEMENT && xsr.getLocalName().equals("Identity")) {
                assertEquals(q.getNamespaceURI(), xsr.getNamespaceURI());
            }
        }
    }

    private static EntityResolver getResolver() {
        EntityResolver resolver = null;
        if (resolver == null) {
            // set up a manager
            resolver = XmlCatalogUtil.createDefaultCatalogResolver();
        }

        return resolver;
    }

    public URL getResource(String s) {
        return getClass().getClassLoader().getResource(s);
    }

}
