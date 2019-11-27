/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.impl.internalspi.encoding;

import java.io.IOException;
import java.io.InputStream;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Message;

/**
 * Decodes SOAPEnvelope read from an <code>InputStream</code> into a <code>Message</code> instance.
 * This SPI allows for other implementations instead of the default, which is based on XMLStreamReader.
 * 
 * @since 2.2.9
 */
public interface StreamDecoder {
    Message decode(
            InputStream in, String charset, 
            AttachmentSet att, SOAPVersion soapVersion) throws IOException;
}
