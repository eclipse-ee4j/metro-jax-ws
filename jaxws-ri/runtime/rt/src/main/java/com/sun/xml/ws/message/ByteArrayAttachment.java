/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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
import com.sun.xml.ws.util.ByteArrayDataSource;
import com.sun.xml.ws.encoding.DataSourceStreamingDataHandler;

import java.io.ByteArrayInputStream;

import javax.activation.DataHandler;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Jitendra Kotamraju
 */
public final class ByteArrayAttachment implements Attachment {

    private final String contentId;
    private byte[] data;
    private int start;
    private final int len;
    private final String mimeType;

    public ByteArrayAttachment(@NotNull String contentId, byte[] data, int start, int len, String mimeType) {
        this.contentId = contentId;
        this.data = data;
        this.start = start;
        this.len = len;
        this.mimeType = mimeType;
    }
    
    public ByteArrayAttachment(@NotNull String contentId, byte[] data, String mimeType) {
        this(contentId, data, 0, data.length, mimeType);
    }

    public String getContentId() {
        return contentId;
    }

    public String getContentType() {
        return mimeType;
    }

    public byte[] asByteArray() {
        if(start!=0 || len!=data.length) {
            // if our buffer isn't exact, switch to the exact one
            byte[] exact = new byte[len];
            System.arraycopy(data,start,exact,0,len);
            start = 0;
            data = exact;
        }
        return data;
    }

    public DataHandler asDataHandler() {
        return new DataSourceStreamingDataHandler(new ByteArrayDataSource(data,start,len,getContentType()));
    }

    public Source asSource() {
        return new StreamSource(asInputStream());
    }

    public InputStream asInputStream() {
         return new ByteArrayInputStream(data,start,len);
    }

    public void writeTo(OutputStream os) throws IOException {
        os.write(asByteArray());
    }

    public void writeTo(SOAPMessage saaj) throws SOAPException {
        AttachmentPart part = saaj.createAttachmentPart();
        part.setDataHandler(asDataHandler());
        part.setContentId(contentId);
        saaj.addAttachmentPart(part);
    }

}
