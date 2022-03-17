/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.HeaderList;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.MessageHeaders;

import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;

/**
 * {@link Message} that has no body.
 * 
 * @author Kohsuke Kawaguchi
 */
public class EmptyMessageImpl extends AbstractMessageImpl {

    /**
     * If a message has no payload, it's more likely to have
     * some header, so we create it eagerly here.
     */
    private final MessageHeaders headers;
    private final AttachmentSet attachmentSet;

    public EmptyMessageImpl(SOAPVersion version) {
        super(version);
        this.headers = new HeaderList(version);
        this.attachmentSet = new AttachmentSetImpl();
    }

    public EmptyMessageImpl(MessageHeaders headers, @NotNull AttachmentSet attachmentSet, SOAPVersion version){
        super(version);
        if(headers==null)
            headers = new HeaderList(version);
        this.attachmentSet = attachmentSet;
        this.headers = headers;
    }

    /**
     * Copy constructor.
     */
    private EmptyMessageImpl(EmptyMessageImpl that) {
        super(that);
        this.headers = new HeaderList(that.headers);
        this.attachmentSet = that.attachmentSet;
        this.copyFrom(that);
    }

    @Override
    public boolean hasHeaders() {
        return headers.hasHeaders();
    }
    
    @Override
    public MessageHeaders getHeaders() {
        return headers;
    }

    @Override
    public String getPayloadLocalPart() {
        return null;
    }

    @Override
    public String getPayloadNamespaceURI() {
        return null;
    }

    @Override
    public boolean hasPayload() {
        return false;
    }

    @Override
    public Source readPayloadAsSource() {
        return null;
    }

    @Override
    public XMLStreamReader readPayload() throws XMLStreamException {
        return null;
    }

    @Override
    public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
        // noop
    }

    @Override
    public void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
        // noop
    }

    @Override
    public Message copy() {
        return new EmptyMessageImpl(this).copyFrom(this);
    }

}
