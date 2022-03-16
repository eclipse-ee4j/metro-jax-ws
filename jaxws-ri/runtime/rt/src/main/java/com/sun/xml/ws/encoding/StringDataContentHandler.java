/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding;

import jakarta.activation.ActivationDataFlavor;
import jakarta.activation.DataSource;
import jakarta.activation.DataContentHandler;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * JavaMail's data content handler for text/plain --&gt;String
 */
public class StringDataContentHandler implements DataContentHandler {
    private static final ActivationDataFlavor myDF = new ActivationDataFlavor(
            java.lang.String.class, "text/plain", "Text String");

    protected ActivationDataFlavor getDF() {
        return myDF;
    }

    /**
     * Return the DataFlavors for this <code>DataContentHandler</code>.
     *
     * @return The DataFlavors
     */
    public ActivationDataFlavor[] getTransferDataFlavors() {
        return new ActivationDataFlavor[]{getDF()};
    }

    /**
     * Return the Transfer Data of type DataFlavor from InputStream.
     *
     * @param df The DataFlavor
     * @param ds The DataSource corresponding to the data
     * @return String object
     */
    public Object getTransferData(ActivationDataFlavor df, DataSource ds)
            throws IOException {
        // use myDF.equals to be sure to get ActivationDataFlavor.equals,
        // which properly ignores Content-Type parameters in comparison
        if (getDF().equals(df))
            return getContent(ds);
        else
            return null;
    }

    public Object getContent(DataSource ds) throws IOException {
        String enc = null;
        InputStreamReader is;

        try {
            enc = getCharset(ds.getContentType());
            is = new InputStreamReader(ds.getInputStream(), enc);
        } catch (IllegalArgumentException iex) {
            /*
            * An unknown charset of the form ISO-XXX-XXX will cause
            * the JDK to throw an IllegalArgumentException.  The
            * JDK will attempt to create a classname using this string,
            * but valid classnames must not contain the character '-',
            * and this results in an IllegalArgumentException, rather than
            * the expected UnsupportedEncodingException.  Yikes.
            */
            throw new UnsupportedEncodingException(enc);
        }

        try {
            int pos = 0;
            int count;
            char[] buf = new char[1024];

            while ((count = is.read(buf, pos, buf.length - pos)) != -1) {
                pos += count;
                if (pos >= buf.length) {
                    int size = buf.length;
                    size += Math.min(size, 256 * 1024);
                    char[] tbuf = new char[size];
                    System.arraycopy(buf, 0, tbuf, 0, pos);
                    buf = tbuf;
                }
            }
            return new String(buf, 0, pos);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                // not much can be done
            }
        }
    }

    /**
     * Write the object to the output stream, using the specified MIME type.
     */
    public void writeTo(Object obj, String type, OutputStream os)
            throws IOException {
        if (!(obj instanceof String))
            throw new IOException("\"" + getDF().getMimeType() +
                    "\" DataContentHandler requires String object, " +
                    "was given object of type " + obj.getClass().toString());

        String enc = null;
        OutputStreamWriter osw;

        try {
            enc = getCharset(type);
            osw = new OutputStreamWriter(os, enc);
        } catch (IllegalArgumentException iex) {
            /*
            * An unknown charset of the form ISO-XXX-XXX will cause
            * the JDK to throw an IllegalArgumentException.  The
            * JDK will attempt to create a classname using this string,
            * but valid classnames must not contain the character '-',
            * and this results in an IllegalArgumentException, rather than
            * the expected UnsupportedEncodingException.  Yikes.
            */
            throw new UnsupportedEncodingException(enc);
        }

        String s = (String) obj;
        osw.write(s, 0, s.length());
        osw.flush();
    }

    private String getCharset(String type) {
        try {
            ContentType ct = new ContentType(type);
            String charset = ct.getParameter("charset");
            if (charset == null)
                // If the charset parameter is absent, use US-ASCII.
                charset = "us-ascii";

            return Charset.forName(charset).name();
            //return MimeUtility.javaCharset(charset);
        } catch (Exception ex) {
            return null;
        }
    }


}
