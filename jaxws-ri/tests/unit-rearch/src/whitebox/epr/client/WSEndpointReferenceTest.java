/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.epr.client;

import org.glassfish.jaxb.core.marshaller.XMLWriter;
import com.sun.xml.stream.buffer.XMLStreamBuffer;
import com.sun.xml.stream.buffer.XMLStreamBufferException;
import com.sun.xml.stream.buffer.stax.StreamReaderBufferProcessor;
import static com.sun.xml.ws.api.addressing.AddressingVersion.W3C;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
import com.sun.xml.ws.util.xml.XmlUtil;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLTestCase;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.wsaddressing.W3CEndpointReference;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;

/**
 * Tests {@link WSEndpointReferenceTest}.
 *
 * @author Kohsuke Kawaguchi
 */
    public class WSEndpointReferenceTest extends XMLTestCase {
    public void testCreateViaSpec() throws Exception {
        W3CEndpointReference spec = new W3CEndpointReference(new StreamSource(
            getClass().getResource("test-epr.xml").toExternalForm()
        ));
        new WSEndpointReference(spec, W3C);
    }

    public void testCreateViaBuffer() throws Exception {
        WSEndpointReference epr = createEPR();
        System.out.println(epr.toString());
    }

    private WSEndpointReference createEPR() throws XMLStreamBufferException, XMLStreamException {
        return new WSEndpointReference(getClass().getResourceAsStream("test-epr.xml"),W3C);
    }

    /**
     * This creates {@link WSEndpointReference} that uses a fragment infoset.
     * We should really fix this in XMLStreamBuffer.
     */
    private WSEndpointReference creaateSubtreeEPR() throws XMLStreamException {
        XMLStreamBuffer xsb = XMLStreamBuffer.createNewBufferFromXMLStreamReader(
            XMLInputFactory.newInstance().createXMLStreamReader(
                getClass().getResourceAsStream("test-epr.xml")));
        StreamReaderBufferProcessor p = xsb.readAsXMLStreamReader();
        XMLStreamBuffer mark = p.nextTagAndMark();

        return new WSEndpointReference(mark,W3C);
    }

    private Reader getReferenceInfoset() {
        return new InputStreamReader(getClass().getResourceAsStream("test-epr.ref.xml"));
    }

    public void testEchoStAX() throws Exception {
        StringWriter w = new StringWriter();
        XMLStreamWriter xmlwriter = XMLOutputFactory.newInstance().createXMLStreamWriter(w);
        createEPR().writeTo("root", xmlwriter);
        xmlwriter.close();
        //System.out.println(w.toString());
        XMLAssert.assertXMLIdentical(compareXML(w.toString(),getReferenceInfoset()),true);
    }

    public void testEchoSAX() throws Exception {
        StringWriter w = new StringWriter();
        createEPR().writeTo("root", new XMLWriter(new PrintWriter(w),"UTF-8"),
            new DefaultHandler(), true
        );
        System.out.println(w.toString());
        XMLAssert.assertXMLIdentical(compareXML(w.toString(),getReferenceInfoset()),true);
    }

    public void testAsSource() throws Exception {
        StringWriter w = new StringWriter();
        XmlUtil.newTransformer().transform(createEPR().asSource("root"),new StreamResult(w));

        XMLAssert.assertXMLIdentical(compareXML(w.toString(),getReferenceInfoset()),true);
    }

    public void testAddress() throws Exception {
        WSEndpointReference epr = createEPR();
        assertEquals("http://example.com/fabrikam/acct",epr.getAddress());
    }

    public void testReplace() throws Exception {
        WSEndpointReference n = createEPR().createWithAddress("newAddress");
        System.out.println(n);
        assertEquals("newAddress",n.getAddress());
        XMLAssert.assertXMLIdentical(compareXML(n.toString(),
            new InputStreamReader(getClass().getResourceAsStream("newAddress.epr.xml"))),true);
    }

    public void testSubtreeToSpec() throws Exception {
        creaateSubtreeEPR().toSpec();
        creaateSubtreeEPR().toSpec(MemberSubmissionEndpointReference.class);
    }
}
