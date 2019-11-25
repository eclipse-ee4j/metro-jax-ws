/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.impl.encoding;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLStreamReader;

import com.oracle.webservices.impl.internalspi.encoding.StreamDecoder;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
import com.sun.xml.ws.encoding.StreamSOAPCodec;
import com.sun.xml.ws.streaming.TidyXMLStreamReader;

public class StreamDecoderImpl implements StreamDecoder {

    @Override
    public Message decode(InputStream in, String charset,
            AttachmentSet att, SOAPVersion soapVersion) throws IOException {
        XMLStreamReader reader = XMLStreamReaderFactory.create(null, in, charset, true);
        reader =  new TidyXMLStreamReader(reader, in);
        return StreamSOAPCodec.decode(soapVersion, reader, att);
    }

}
