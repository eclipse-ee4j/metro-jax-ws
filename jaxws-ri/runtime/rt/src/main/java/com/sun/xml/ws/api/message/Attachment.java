/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.message;

import com.sun.istack.NotNull;

import jakarta.activation.DataHandler;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPException;
import javax.xml.transform.Source;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Attachment.
 */
public interface Attachment {

    /**
     * Content ID of the attachment. Uniquely identifies an attachment.
     * 
     * http://www.ietf.org/rfc/rfc2392.txt (which is referred by the ws-i attachment profile 
     * http://www.ws-i.org/Profiles/AttachmentsProfile-1.0.html)
     * 
     * content-id    = url-addr-spec
     * url-addr-spec = addr-spec  ; URL encoding of RFC 822 addr-spec
     * cid-url       = "cid" ":" content-id
     * 
     * A "cid" URL is converted to the corresponding Content-ID message header [MIME] by 
     * removing the "cid:" prefix, converting the % encoded character to their equivalent 
     * US-ASCII characters, and enclosing the remaining parts with an angle bracket pair, 
     * "{@literal <}" and "{@literal >}".  For  example, "cid:foo4%25foo1@bar.net" corresponds to
     *      Content-ID: {@literal <}foo4%25foo1@bar.net{@literal >}
     *
     * @return
     *      The content ID like "foo-bar-zot@abc.com", without
     *      surrounding '&lt;' and '&gt;' used as the transfer syntax.
     */
    @NotNull String getContentId();

    /**
     * Gets the MIME content-type of this attachment.
     */
    String getContentType();

    /**
     * Gets the attachment as an exact-length byte array.
     */
    byte[] asByteArray();

    /**
     * Gets the attachment as a {@link DataHandler}.
     */
    DataHandler asDataHandler();

    /**
     * Gets the attachment as a {@link Source}.
     * Note that there's no guarantee that the attachment is actually an XML.
     */
    Source asSource();

    /**
     * Obtains this attachment as an {@link InputStream}.
     */
    InputStream asInputStream();

    /**
     * Writes the contents of the attachment into the given stream.
     */
    void writeTo(OutputStream os) throws IOException;

    /**
     * Writes this attachment to the given {@link SOAPMessage}.
     */
    void writeTo(SOAPMessage saaj) throws SOAPException;
}
