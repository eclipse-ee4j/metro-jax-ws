/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.sun.xml.ws.encoding.xml;

import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.ContentType;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
import com.sun.xml.ws.api.WSFeatureList;
import com.sun.xml.ws.encoding.ContentTypeImpl;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public final class XMLCodec implements Codec {

    public static final String XML_APPLICATION_MIME_TYPE = "application/xml";

    public static final String XML_TEXT_MIME_TYPE = "text/xml";

    private static final ContentType contentType = new ContentTypeImpl(XML_TEXT_MIME_TYPE);

//  private final WSBinding binding;
    private WSFeatureList features;

    public XMLCodec(WSFeatureList f) {
//        this.binding = binding;
        features = f;
    }

    @Override
    public String getMimeType() {
        return XML_APPLICATION_MIME_TYPE;
    }

    @Override
    public ContentType getStaticContentType(Packet packet) {
        return contentType;
    }

    @Override
    public ContentType encode(Packet packet, OutputStream out) {
        String encoding = (String) packet.invocationProperties
                .get(XMLConstants.OUTPUT_XML_CHARACTER_ENCODING);

        XMLStreamWriter writer = null;

        if (encoding != null && encoding.length() > 0) {
            writer = XMLStreamWriterFactory.create(out, encoding);
        } else {
            writer = XMLStreamWriterFactory.create(out);
        }

        try {
            if (packet.getMessage().hasPayload()) {
                writer.writeStartDocument();
                packet.getMessage().writePayloadTo(writer);
                writer.flush();
            }
        } catch (XMLStreamException e) {
            throw new WebServiceException(e);
        }
        return contentType;
    }

    @Override
    public ContentType encode(Packet packet, WritableByteChannel buffer) {
        //TODO: not yet implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public Codec copy() {
        return this;
    }

    @Override
    public void decode(InputStream in, String contentType, Packet packet) throws IOException {
        Message message = XMLMessage.create(contentType, in, features);
        packet.setMessage(message);
    }

    @Override
    public void decode(ReadableByteChannel in, String contentType, Packet packet) {
        // TODO
        throw new UnsupportedOperationException();
    }
}
