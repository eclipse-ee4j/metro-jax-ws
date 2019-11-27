/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.streaming;

import com.sun.xml.ws.message.jaxb.JAXBMessage;
import com.sun.xml.ws.encoding.MtomCodec;

import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.stream.XMLStreamWriter;

/**
 * A {@link XMLStreamWriter} that used for MTOM encoding may provide its own
 * {@link AttachmentMarshaller}. The marshaller could do processing based on
 * MTOM threshold, and make decisions about inlining the attachment data or not.
 *
 * 
 * @author Jitendra Kotamraju
 * @see JAXBMessage
 * @see MtomCodec
 * 
 * @deprecated use org.jvnet.staxex.util.MtomStreamWriter
 */
public interface MtomStreamWriter {
    AttachmentMarshaller getAttachmentMarshaller();
}
