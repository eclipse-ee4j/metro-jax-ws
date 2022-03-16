/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.pipe;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.AttachmentSet;

import javax.xml.stream.XMLStreamReader;

/**
 * Reads events from {@link XMLStreamReader} and constructs a
 * {@link Message} for SOAP envelope. {@link Codecs} allows a
 * way to construct a whole codec that can handle MTOM, MIME
 * encoded packages using this codec.
 *
 *
 * @see Codecs
 * @author Jitendra Kotamraju
 */
public interface StreamSOAPCodec extends Codec {
    /**
     * Reads events from {@link XMLStreamReader} and constructs a
     * {@link Message} for SOAP envelope.
     *
     * @param reader that represents SOAP envelope infoset
     * @return a {@link Message} for SOAP envelope
     */
    @NotNull Message decode(@NotNull XMLStreamReader reader);

    /**
     * Reads events from {@link XMLStreamReader} and constructs a
     * {@link Message} for SOAP envelope.
     *
     * @param reader that represents SOAP envelope infoset
     * @param att attachments for the message
     * @return a {@link Message} for SOAP envelope
     */
    @NotNull Message decode(@NotNull XMLStreamReader reader, @NotNull AttachmentSet att);
}
