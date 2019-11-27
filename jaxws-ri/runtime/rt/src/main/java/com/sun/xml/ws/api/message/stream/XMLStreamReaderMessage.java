/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
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
import javax.xml.stream.XMLStreamReader;

/**
 * Low level representation of an XML or SOAP message as an {@link XMLStreamReader}.
 *
 */
public class XMLStreamReaderMessage extends StreamBasedMessage {  
    /**
     * The message represented as an {@link XMLStreamReader}.
     */
    public final XMLStreamReader msg;
    
    /** 
     * Create a new message.
     *
     * @param properties
     *      the properties of the message.
     *
     * @param msg
     *      always a non-null unconsumed {@link XMLStreamReader} that
     *      represents a request.
     */
    public XMLStreamReaderMessage(Packet properties, XMLStreamReader msg) {
        super(properties);
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
     * @param msg
     *      always a non-null unconsumed {@link XMLStreamReader} that
     *      represents a request.
     */
    public XMLStreamReaderMessage(Packet properties, AttachmentSet attachments, XMLStreamReader msg) {
        super(properties, attachments);
        this.msg = msg;
    }    
}
