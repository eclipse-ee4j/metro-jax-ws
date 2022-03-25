/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.message;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.spi.db.XMLBridge;

import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;

/**
 * A <code>FilterMessageImpl</code> contains some other Message, which it uses
 * as its  basic source of message data, possibly transforming the data along
 * the way or providing  additional functionality.
 *
 * <p>
 * The class <code>FilterMessageImpl</code> itself simply overrides
 * all the methods of <code>Message</code> and invokes them on
 * contained Message delegate. Subclasses of <code>FilterMessageImpl</code>
 * may further override some of  these methods and may also provide
 * additional methods and fields.
 *
 * @author Jitendra Kotamraju
 */
public class FilterMessageImpl extends Message {
    private final Message delegate;

    protected FilterMessageImpl(Message delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean hasHeaders() {
        return delegate.hasHeaders();
    }

    @Override
    public @NotNull MessageHeaders getHeaders() {
        return delegate.getHeaders();
    }

    @Override
    public @NotNull AttachmentSet getAttachments() {
        return delegate.getAttachments();
    }

    @Override
    protected boolean hasAttachments() {
        return delegate.hasAttachments();    
    }

    @Override
    public boolean isOneWay(@NotNull WSDLPort port) {
        return delegate.isOneWay(port);
    }

    @Override
    public @Nullable String getPayloadLocalPart() {
        return delegate.getPayloadLocalPart();
    }

    @Override
    public String getPayloadNamespaceURI() {
        return delegate.getPayloadNamespaceURI();
    }

    @Override
    public boolean hasPayload() {
        return delegate.hasPayload();
    }

    @Override
    public boolean isFault() {
        return delegate.isFault();
    }

    @Override
    public @Nullable QName getFirstDetailEntryName() {
        return delegate.getFirstDetailEntryName();
    }

    @Override
    public Source readEnvelopeAsSource() {
        return delegate.readEnvelopeAsSource();
    }

    @Override
    public Source readPayloadAsSource() {
        return delegate.readPayloadAsSource();
    }

    @Override
    public SOAPMessage readAsSOAPMessage() throws SOAPException {
        return delegate.readAsSOAPMessage();
    }

    @Override
    public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
        return delegate.readAsSOAPMessage(packet, inbound);
    }

    @Override
    public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
        return delegate.readPayloadAsJAXB(unmarshaller);
    }

    @Override
    public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
        return delegate.readPayloadAsJAXB(bridge);
    }

    @Override
    public XMLStreamReader readPayload() throws XMLStreamException {
        return delegate.readPayload();
    }

    @Override
    public void consume() {
        delegate.consume();
    }

    @Override
    public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
        delegate.writePayloadTo(sw);
    }

    @Override
    public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
        delegate.writeTo(sw);
    }

    @Override
    public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
        delegate.writeTo(contentHandler, errorHandler);
    }

    @Override
    public Message copy() {
        return delegate.copy().copyFrom(delegate);
    }

    @Override
    public @NotNull String getID(@NotNull WSBinding binding) {
        return delegate.getID(binding);
    }

    @Override
    public @NotNull String getID(AddressingVersion av, SOAPVersion sv) {
        return delegate.getID(av, sv);
    }

    @Override
    public SOAPVersion getSOAPVersion() {
        return delegate.getSOAPVersion();
    }
}
