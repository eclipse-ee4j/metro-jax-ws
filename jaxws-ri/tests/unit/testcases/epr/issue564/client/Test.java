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

import com.sun.xml.ws.api.message.*;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.Codecs;
import com.sun.xml.ws.util.ByteArrayBuffer;
import junit.framework.TestCase;
import org.w3c.dom.Element;

import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * This testcase tests if the namespace declaration on soap:env are retained when the EPRs (replyTo and FaultTo)
 * are read and used in the response
 *
 * @author Rama Pulavarthi
 */
public class Test extends TestCase {
    public void testMember() throws Exception {
        {
            String requestStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<S:Envelope xmlns:S=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:user=\"http://foo.bar\" " +
                    "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\">" +
                    "<S:Header>" +
                    "<wsa:Action>http://example.org/action/echoIn</wsa:Action>" +
                    "<wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>" +
                    "<wsa:MessageID>urn:uuid:1234567890</wsa:MessageID>" +
                    "<wsa:ReplyTo>" +
                    "<wsa:Address>http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous</wsa:Address>" +
                    "<wsa:ReferenceParameters>" +
                    "<user:foo>bar</user:foo>" +
                    "<user:bar xmlns:user=\"http://foo.bar1\">" +
                    "<user:foobar>barfoo</user:foobar>" +
                    "</user:bar>" +
                    "</wsa:ReferenceParameters>" +
                    "</wsa:ReplyTo>" +
                    "<wsa:FaultTo>" +
                    "<wsa:Address>http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous</wsa:Address>" +
                    "<wsa:ReferenceParameters>" +
                    "<user:foo>bar</user:foo>" +
                    "<user:bar xmlns:user=\"http://foo.bar1\">" +
                    "<user:foobar>barfoo</user:foobar>" +
                    "</user:bar>" +
                    "</wsa:ReferenceParameters>" +
                    "</wsa:FaultTo>" +
                    "</S:Header>" +
                    "<S:Body>" +
                    "<addNumbers xmlns=\"http://example.com/\">" +
                    "<number1>10</number1>" +
                    "<number2>10</number2>" +
                    "</addNumbers>" +
                    "</S:Body></S:Envelope>";
            Message message = useStream12Codec(requestStr);
            WSEndpointReference wsepr = AddressingUtils.getFaultTo(message.getHeaders(), AddressingVersion.MEMBER, SOAPVersion.SOAP_12);
            Message m2 = Messages.create("Test Unsupported", AddressingVersion.MEMBER, SOAPVersion.SOAP_12);
            wsepr.addReferenceParametersToList(m2.getHeaders());
            ByteArrayBuffer baos = new ByteArrayBuffer();
            XMLStreamWriter writer = XMLStreamWriterFactory.create(baos);
            m2.writeTo(writer);
            writer.flush();

            XMLInputFactory readerFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = readerFactory.createXMLStreamReader(baos.newInputStream());
            Message sm = Messages.create(reader);
            Packet sm_packet = new Packet(sm);
            MessageHeaders headers = sm_packet.getMessage().getHeaders();
            Header h1 = headers.get("http://foo.bar", "foo", true);
            assertEquals("bar", h1.getStringContent());
            Header h2 = headers.get("http://foo.bar1", "bar", true);
            assertTrue(h2 != null);


        }
    }

    public void testW3C() throws Exception {
        {
            String requestStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<S:Envelope xmlns:S=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:user=\"http://foo.bar\" " +
                    "xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">" +
                    "<S:Header>" +
                    "<wsa:Action>http://example.org/action/echoIn</wsa:Action>" +
                    "<wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>" +
                    "<wsa:MessageID>urn:uuid:1234567890</wsa:MessageID>" +
                    "<wsa:ReplyTo>" +
                    "<wsa:Address>address1</wsa:Address>" +
                    "<wsa:ReferenceParameters>" +
                    "<user:foo>bar</user:foo>" +
                    "<user:bar xmlns:user=\"http://foo.bar\">" +
                    "<user:foobar>barfoo</user:foobar>" +
                    "</user:bar>" +
                    "</wsa:ReferenceParameters>" +
                    "</wsa:ReplyTo>" +
                    "<wsa:FaultTo>" +
                    "<wsa:Address>address2</wsa:Address>" +
                    "<wsa:ReferenceParameters>" +
                    "<user:foo>bar</user:foo>" +
                    "<user:bar xmlns:user=\"http://foo.bar\">" +
                    "<user:foobar>barfoo</user:foobar>" +
                    "</user:bar>" +
                    "</wsa:ReferenceParameters>" +
                    "</wsa:FaultTo>" +
                    "</S:Header>" +
                    "<S:Body>" +
                    "<addNumbers xmlns=\"http://example.com/\">" +
                    "<number1>10</number1>" +
                    "<number2>10</number2>" +
                    "</addNumbers>" +
                    "</S:Body></S:Envelope>";
            Message message = useStream12Codec(requestStr);
            WSEndpointReference wsepr = AddressingUtils.getFaultTo(message.getHeaders(), AddressingVersion.W3C, SOAPVersion.SOAP_12);
            Message m2 = Messages.create("Test Unsupported", AddressingVersion.W3C, SOAPVersion.SOAP_12);
            wsepr.addReferenceParametersToList(m2.getHeaders());
            ByteArrayBuffer baos = new ByteArrayBuffer();
            XMLStreamWriter writer = XMLStreamWriterFactory.create(baos);
            m2.writeTo(writer);
            writer.flush();

            XMLInputFactory readerFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = readerFactory.createXMLStreamReader(baos.newInputStream());
            Message sm = Messages.create(reader);
            Packet sm_packet = new Packet(sm);
            List refParams = sm_packet.getReferenceParameters();
            assertEquals("Did n't get expected ReferenceParameters", 2, refParams.size());
            for (Object e : refParams) {
                assertEquals("NS Decl did not match", "http://foo.bar", ((Element) e).getNamespaceURI());
            }

        }
    }

    public static Message useStream12Codec(String msg) throws IOException {
        Codec codec = Codecs.createSOAPEnvelopeXmlCodec(SOAPVersion.SOAP_12);
        Packet packet = new Packet();
        ByteArrayInputStream in = new ByteArrayInputStream(msg.getBytes());
        codec.decode(in, "application/soap+xml", packet);
        return packet.getMessage();
    }


}
