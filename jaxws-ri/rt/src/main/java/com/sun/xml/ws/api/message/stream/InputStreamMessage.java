/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.message.stream;

import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Packet;
import java.io.InputStream;

/**
 * Low level representation of an XML or SOAP message as an {@link InputStream}.
 *
 */
public class InputStreamMessage extends StreamBasedMessage {
    /**
     * The MIME content-type of the encoding.
     */    
    public final String contentType;
    
    /**
     * The message represented as an {@link InputStream}.
     */
    public final InputStream msg;
    
    /**
     * Create a new message.
     *
     * @param properties
     *      the properties of the message.
     *
     * @param contentType
     *      the MIME content-type of the encoding.
     *
     * @param msg
     *      always a non-null unconsumed {@link InputStream} that
     *      represents a request.
     */
    public InputStreamMessage(Packet properties, String contentType, InputStream msg) {
        super(properties);
        
        this.contentType = contentType;
        this.msg = msg;
    }
    
    /**
     * Create a new message.
     *
     * @param properties
     *      the properties of the message.
     *
     * @param attachments
     *      the attachments of the message.
     *
     * @param contentType
     *      the MIME content-type of the encoding.
     *
     * @param msg
     *      always a non-null unconsumed {@link InputStream} that
     *      represents a request.
     */
    public InputStreamMessage(Packet properties, AttachmentSet attachments,
            String contentType, InputStream msg) {
        super(properties, attachments);
        
        this.contentType = contentType;
        this.msg = msg;
    }
}
