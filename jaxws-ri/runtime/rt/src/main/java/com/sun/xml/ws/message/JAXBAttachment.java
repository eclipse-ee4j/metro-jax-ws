/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Attachment;
import com.sun.xml.ws.spi.db.XMLBridge;
import com.sun.xml.ws.util.ByteArrayBuffer;
import com.sun.xml.ws.encoding.DataSourceStreamingDataHandler;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.bind.JAXBException;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Jitendra Kotamraju
 */
public final class JAXBAttachment implements Attachment, DataSource {

    private final String contentId;
    private final String mimeType;
    private final Object jaxbObject;
    private final XMLBridge bridge;

    public JAXBAttachment(@NotNull String contentId, Object jaxbObject, XMLBridge bridge, String mimeType) {
        this.contentId = contentId;
        this.jaxbObject = jaxbObject;
        this.bridge = bridge;
        this.mimeType = mimeType;
    }

    @Override
    public String getContentId() {
        return contentId;
    }

    @Override
    public String getContentType() {
        return mimeType;
    }

    @Override
    public byte[] asByteArray() {
        ByteArrayBuffer bab = new ByteArrayBuffer();
        try {
            writeTo(bab);
        } catch (IOException e) {
            throw new WebServiceException(e);
        }
        return bab.getRawData();
    }

    @Override
    public DataHandler asDataHandler() {
        return new DataSourceStreamingDataHandler(this);
    }

    @Override
    public Source asSource() {
        return new StreamSource(asInputStream());
    }

    @Override
    public InputStream asInputStream() {
        ByteArrayBuffer bab = new ByteArrayBuffer();
        try {
            writeTo(bab);
        } catch (IOException e) {
            throw new WebServiceException(e);
        }
        return bab.newInputStream();
    }

    @Override
    public void writeTo(OutputStream os) throws IOException {
        try {
            bridge.marshal(jaxbObject, os, null, null);
        } catch (JAXBException e) {
            throw new WebServiceException(e);
        }
    }

    @Override
    public void writeTo(SOAPMessage saaj) throws SOAPException {
        AttachmentPart part = saaj.createAttachmentPart();
        part.setDataHandler(asDataHandler());
        part.setContentId(contentId);
        saaj.addAttachmentPart(part);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return asInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return null;
    }

}
