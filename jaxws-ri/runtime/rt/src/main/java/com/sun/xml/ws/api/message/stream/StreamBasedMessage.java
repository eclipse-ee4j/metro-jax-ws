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
import com.sun.xml.ws.message.AttachmentSetImpl;

/**
 * Base representation an XML or SOAP message as stream.
 *
 */
abstract class StreamBasedMessage {
    /**
     * The properties of the message.
     */    
    public final Packet properties;
    
    /**
     * The attachments of this message
     * (attachments live outside a message.)
     */
    public final AttachmentSet attachments;
    
    /**
     * Create a new message.
     *
     * @param properties
     *      the properties of the message.
     *
     */
    protected StreamBasedMessage(Packet properties) {
        this.properties = properties;
        this.attachments = new AttachmentSetImpl();        
    }
    
    /**
     * Create a new message.
     *
     * @param properties
     *      the properties of the message.
     *
     * @param attachments
     *      the attachments of the message.
     */
    protected StreamBasedMessage(Packet properties, AttachmentSet attachments) {
        this.properties = properties;
        this.attachments = attachments;
    }
}
