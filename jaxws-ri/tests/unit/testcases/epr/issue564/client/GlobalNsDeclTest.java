/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.issue564.client;

import junit.framework.TestCase;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.*;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.Codecs;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
import com.sun.xml.ws.util.ByteArrayBuffer;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Test to see if ns declarations on soap envelope are captured on to the headers after parsing.
 *
 * @author Rama Pulavarthi
 */
public class GlobalNsDeclTest extends TestCase {

    public void testNsDecl() throws Exception{
        String requestStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" xmlns:user=\"http://foo.bar\">" +
                "<S:Header>" +
                "<user:foo>bar</user:foo>" +
                "</S:Header>" +
                "<S:Body>" +
                "<addNumbers xmlns=\"http://example.com/\">" +
                "<number1>10</number1>" +
                "<number2>10</number2>" +
                "</addNumbers>" +
                "</S:Body></S:Envelope>";
        Message message = useStreamCodec(requestStr);
        MessageHeaders hl = message.getHeaders();
        ByteArrayBuffer baos = new ByteArrayBuffer();
        XMLStreamWriter writer = XMLStreamWriterFactory.create(baos);
        writer.writeStartDocument();
        for (Header h : hl.asList()) {
            h.writeTo(writer);
        }
        writer.writeEndDocument();
        writer.flush();
        //baos.writeTo(System.out);

        XMLInputFactory readerFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = readerFactory.createXMLStreamReader(baos.newInputStream());
        reader.next();// go to start element
        Header h = Headers.create(SOAPVersion.SOAP_11, reader);
        assertEquals(h.getNamespaceURI(), "http://foo.bar");
    }

    Message useStreamCodec(String msg) throws IOException {
        Codec codec = Codecs.createSOAPEnvelopeXmlCodec(SOAPVersion.SOAP_11);
        Packet packet = new Packet();
        ByteArrayInputStream in = new ByteArrayInputStream(msg.getBytes());
        codec.decode(in, "text/xml", packet);
        return packet.getMessage();
    }

}
