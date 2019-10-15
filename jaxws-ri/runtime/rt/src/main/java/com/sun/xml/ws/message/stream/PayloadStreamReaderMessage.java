/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message.stream;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.MessageHeaders;
import com.sun.xml.ws.message.AbstractMessageImpl;
import com.sun.xml.ws.message.AttachmentSetImpl;
import com.sun.istack.Nullable;
import com.sun.istack.NotNull;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;

/**
 * {@link Message} backed by {@link XMLStreamReader} as payload
 *
 * @author Jitendra Kotamraju
 */
public class PayloadStreamReaderMessage extends AbstractMessageImpl {
    private final StreamMessage message;

    public PayloadStreamReaderMessage(XMLStreamReader reader, SOAPVersion soapVer) {
        this(null, reader,new AttachmentSetImpl(), soapVer);
    }

    public PayloadStreamReaderMessage(@Nullable MessageHeaders headers, @NotNull XMLStreamReader reader,
                                      @NotNull AttachmentSet attSet, @NotNull SOAPVersion soapVersion) {
        super(soapVersion);
        message = new StreamMessage(headers, attSet, reader, soapVersion);
    }
    
    public boolean hasHeaders() {
        return message.hasHeaders();
    }

    public AttachmentSet getAttachments() {
        return message.getAttachments();
    }

    public String getPayloadLocalPart() {
        return message.getPayloadLocalPart();
    }

    public String getPayloadNamespaceURI() {
        return message.getPayloadNamespaceURI();
    }

    public boolean hasPayload() {
        return true;
    }

    public Source readPayloadAsSource() {
        return message.readPayloadAsSource();
    }

    public XMLStreamReader readPayload() throws XMLStreamException {
        return message.readPayload();
    }

    public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
        message.writePayloadTo(sw);
    }

    public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
        return (T) message.readPayloadAsJAXB(unmarshaller);
    }

    public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
        message.writeTo(contentHandler, errorHandler);
    }

    protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
        message.writePayloadTo(contentHandler, errorHandler, fragment);
    }

    public Message copy() {
        return message.copy().copyFrom(message);
    }
    
    @Override
    public @NotNull MessageHeaders getHeaders() {
        return message.getHeaders();
    }
}
