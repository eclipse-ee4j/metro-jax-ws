/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message.source;

import org.glassfish.jaxb.runtime.api.Bridge;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.pipe.Codecs;
import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.MessageHeaders;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.spi.db.XMLBridge;
import com.sun.xml.ws.streaming.SourceReaderFactory;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;

/**
 * Implementation of {@link Message} backed by {@link Source} where the Source
 * represents the complete message such as a SOAP envelope. It uses
 * {@link StreamSOAPCodec} to create a {@link Message} and uses it as a
 * delegate for all the methods.
 *
 * @author Vivek Pandey
 * @author Jitendra Kotamraju
 */
public class ProtocolSourceMessage extends Message {
    private final Message sm;

    public ProtocolSourceMessage(Source source, SOAPVersion soapVersion) {
        XMLStreamReader reader = SourceReaderFactory.createSourceReader(source, true);
        com.sun.xml.ws.api.pipe.StreamSOAPCodec codec = Codecs.createSOAPEnvelopeXmlCodec(soapVersion);
        sm = codec.decode(reader);
    }

    @Override
    public boolean hasHeaders() {
        return sm.hasHeaders();
    }

    @Override
    public String getPayloadLocalPart() {
        return sm.getPayloadLocalPart();
    }

    @Override
    public String getPayloadNamespaceURI() {
        return sm.getPayloadNamespaceURI();
    }

    @Override
    public boolean hasPayload() {
        return sm.hasPayload();
    }

    @Override
    public Source readPayloadAsSource() {
        return sm.readPayloadAsSource();
    }

    @Override
    public XMLStreamReader readPayload() throws XMLStreamException {
        return sm.readPayload();
    }

    @Override
    public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
        sm.writePayloadTo(sw);
    }

    @Override
    public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
        sm.writeTo(sw);
    }

    @Override
    public Message copy() {
        return sm.copy().copyFrom(sm);
    }

    @Override
    public Source readEnvelopeAsSource() {
        return sm.readEnvelopeAsSource();
    }

    @Override
    public SOAPMessage readAsSOAPMessage() throws SOAPException {
        return sm.readAsSOAPMessage();
    }

    @Override
    public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
        return sm.readAsSOAPMessage(packet, inbound);
    }

    @Override
    public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
        return sm.readPayloadAsJAXB(unmarshaller);
    }
    /** @deprecated */
    @Deprecated
    @Override
    public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
        return sm.readPayloadAsJAXB(bridge);
    }
    @Override
    public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
        return sm.readPayloadAsJAXB(bridge);
    }

    @Override
    public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
        sm.writeTo(contentHandler, errorHandler);
    }

    @Override
    public SOAPVersion getSOAPVersion() {
        return sm.getSOAPVersion();
    }

    @Override
    public MessageHeaders getHeaders() {
        return sm.getHeaders();
    }
}
