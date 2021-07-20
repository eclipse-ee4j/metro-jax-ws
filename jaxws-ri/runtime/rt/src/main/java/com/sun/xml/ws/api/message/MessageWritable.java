/*
 * Copyright (c) 2012, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.message;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.ws.soap.MTOMFeature;

import com.oracle.webservices.api.message.ContentType;

/**
 * A Message implementation may implement this interface as an alternative way to write the
 * message into the OutputStream.
 * 
 * @author shih-chang.chen@oracle.com
 */
public interface MessageWritable {
    
    /**
     * Gets the Content-type of this message.
     * 
     * @return The MIME content type of this message
     */
    ContentType getContentType();

    /**
     * Writes the XML infoset portion of this MessageContext
     * (from &lt;soap:Envelope&gt; to &lt;/soap:Envelope&gt;).
     *
     * @param out
     *      Must not be null. The caller is responsible for closing the stream,
     *      not the callee.
     *
     * @return
     *      The MIME content type of this message (such as "application/xml").
     *      This information is often ncessary by transport.
     *
     * @throws IOException
     *      if a {@link OutputStream} throws {@link IOException}.
     */
    ContentType writeTo( OutputStream out ) throws IOException;

    /**
     * Passes configuration information to this message to ensure the proper
     * wire format is created. (from &lt;soap:Envelope&gt; to &lt;/soap:Envelope&gt;).
     * 
     * @param mtomFeature
     *            The standard <code>WebServicesFeature</code> for specifying
     *            the MTOM enablement and possibly threshold for the endpoint.
     *            This value may be <code>null</code>.
     */
    void setMTOMConfiguration(final MTOMFeature mtomFeature);
}
