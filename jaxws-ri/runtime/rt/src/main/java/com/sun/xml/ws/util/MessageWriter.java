/*
 * Copyright (c) 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Filter suppressing sending binary data to the underlying stream.
 * Methods in this filter throw {@link UtilException} if the {@code limit}
 * number of bytes was already written to given {@code out}.
 */
public final class MessageWriter extends FilterWriter {

    private boolean filtering;
    private boolean notPrinted;
    private boolean headers;
    private int size;
    private int limit;

    /**
     * Create a new filtered writer.
     *
     * @param out a Writer object to provide the underlying stream.
     * @param limit max number of bytes to send to {@code out}
     * @throws NullPointerException if {@code out} is {@code null}
     */
    public MessageWriter(Writer out, int limit) {
        super(out);
        filtering = false;
        notPrinted = true;
        headers = false;
        size = 0;
        this.limit = limit;
    }

    public void println(Object o) throws IOException {
        write(o.toString());
    }

    public void println(String str) throws IOException {
        write(str);
    }

    public void println() throws IOException {
        write("\n");
    }

    @Override
    public void write(String str) throws IOException {
        if (!headers) {
            if (str.startsWith("--")) {
                headers = true;
                filtering = false;
                notPrinted = true;
            }
        }

        if (headers) {
            if (str.contains("Content-Type: ")) {
                String ct = str.toLowerCase();
                if (ct.contains("image/") || ct.contains("/octet-stream") || ct.contains("fastinfoset")) {
                    filtering = true;
                }
            } else if (str.toLowerCase().contains("content-transfer-encoding: binary")) {
                filtering = true;
            }
            if (str.trim().isEmpty()) {
                headers = false;
            }
        }

        if (!headers && filtering) {
            if (notPrinted) {
                notPrinted = false;
                super.write("\n...binary data...");
                super.write("\n");
                size += 18;
            }
        } else {
            if (size + str.length() >= limit) {
                super.write(str.substring(0, limit - size));
                super.write("\n");
                throw new UtilException(new IOException("large input"));
            }
            size += str.length();
            super.write(str);
            super.write("\n");
        }
        flush();
    }
}
