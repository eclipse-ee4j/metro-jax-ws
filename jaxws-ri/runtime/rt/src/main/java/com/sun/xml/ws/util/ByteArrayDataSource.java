/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

/**
 * {@link DataSource} backed by a byte buffer.
 * 
 * @author Kohsuke Kawaguchi
 */
public final class ByteArrayDataSource implements DataSource {

    private final String contentType;
    private final byte[] buf;
    private final int start;
    private final int len;

    public ByteArrayDataSource(byte[] buf, String contentType) {
        this(buf,0,buf.length,contentType);
    }
    public ByteArrayDataSource(byte[] buf, int length, String contentType) {
        this(buf,0,length,contentType);
    }
    public ByteArrayDataSource(byte[] buf, int start, int length, String contentType) {
        this.buf = buf;
        this.start = start;
        this.len = length;
        this.contentType = contentType;
    }

    public String getContentType() {
        if(contentType==null)
            return "application/octet-stream";
        return contentType;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(buf,start,len);
    }

    public String getName() {
        return null;
    }

    public OutputStream getOutputStream() {
        throw new UnsupportedOperationException();
    }
}
