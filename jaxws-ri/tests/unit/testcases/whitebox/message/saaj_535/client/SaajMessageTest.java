/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.message.saaj_535.client;

import junit.framework.TestCase;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.Codecs;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.util.ByteArrayBuffer;
import com.sun.xml.ws.message.stream.StreamMessage;
import com.sun.xml.ws.message.saaj.SAAJMessage;

import javax.xml.stream.XMLStreamWriter;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPBody;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.ByteArrayInputStream;

/**
 * Tests the SAAJMessage implementation for issue: 535
 *
 * @author Jitendra Kotamraju
 */
public class SaajMessageTest extends TestCase {
    String MESSAGE_535  =
        "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'>"+
        "<S:Header>" +
        "<wsa:Action xmlns:wsa='http://www.w3.org/2005/08/addressing'>http://example.com/addNumbers</wsa:Action>" +
        "</S:Header>" +
        "<S:Body attr='value'>" +
        "<addNumbers xmlns='http://example.com/'/>" +
        "</S:Body></S:Envelope>";

    public void testBodyAttr() throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        Source src = new StreamSource(new ByteArrayInputStream(MESSAGE_535.getBytes()));
        message.getSOAPPart().setContent(src);

        SAAJMessage saajMsg = new SAAJMessage(message);
        saajMsg.hasHeaders();         // breaks the underlying SOAPMessage
        Source source = saajMsg.readEnvelopeAsSource();
        SOAPMessage newMsg = factory.createMessage();
        newMsg.getSOAPPart().setContent(source);
        SOAPBody body = newMsg.getSOAPBody();
        assertEquals("value", body.getAttribute("attr"));
    }
}
