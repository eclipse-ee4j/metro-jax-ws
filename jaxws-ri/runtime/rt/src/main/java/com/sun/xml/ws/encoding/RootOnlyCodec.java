/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Codec;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

/**
 * {@link Codec} that works only on the root part of the MIME/multipart.
 * It doesn't work on the attachment parts, so it takes {@link AttachmentSet}
 * as an argument and creates a corresponding {@link Message}. This enables
 * attachments to be parsed lazily by wrapping the mimepull parser into an
 * {@link AttachmentSet}
 *
 * @author Jitendra Kotamraju
 */
public interface RootOnlyCodec extends Codec {

    /**
     * Reads root part bytes from {@link InputStream} and constructs a {@link Message}
     * along with the given attachments.
     *
     * @param in root part's data
     *
     * @param contentType root part's MIME content type (like "application/xml")
     *
     * @param packet the new created {@link Message} is set in this packet
     *
     * @param att attachments
     *
     * @throws IOException
     *      if {@link InputStream} throws an exception.
     */
    void decode(@NotNull InputStream in, @NotNull String contentType, @NotNull Packet packet, @NotNull AttachmentSet att)
            throws IOException;

    /**
     *
     * @see #decode(InputStream, String, Packet, AttachmentSet)
     */
    void decode(@NotNull ReadableByteChannel in, @NotNull String contentType, @NotNull Packet packet, @NotNull AttachmentSet att);
}
