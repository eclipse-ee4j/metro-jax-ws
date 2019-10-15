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

import com.sun.xml.stream.buffer.XMLStreamBuffer;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.WSFeatureList;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.ContentType;
import com.sun.xml.ws.message.stream.StreamHeader11;

import javax.xml.stream.XMLStreamReader;
import java.util.Collections;
import java.util.List;

/**
 * {@link StreamSOAPCodec} for SOAP 1.1.
 *
 * @author Paul.Sandoz@Sun.Com
 */
final class StreamSOAP11Codec extends StreamSOAPCodec {

    public static final String SOAP11_MIME_TYPE = "text/xml";
    public static final String DEFAULT_SOAP11_CONTENT_TYPE =
            SOAP11_MIME_TYPE+"; charset="+SOAPBindingCodec.DEFAULT_ENCODING;

    private static final List<String> EXPECTED_CONTENT_TYPES = Collections.singletonList(SOAP11_MIME_TYPE);

    /*package*/  StreamSOAP11Codec() {
        super(SOAPVersion.SOAP_11);
    }

    /*package*/  StreamSOAP11Codec(WSBinding binding) {
        super(binding);
    }

    /*package*/  StreamSOAP11Codec(WSFeatureList features) {
        super(features);
    }

    public String getMimeType() {
        return SOAP11_MIME_TYPE;
    }
    
    @Override
    protected ContentType getContentType(Packet packet) {
        ContentTypeImpl.Builder b = getContenTypeBuilder(packet);
        b.soapAction = packet.soapAction;
        return b.build();
    }

    @Override
    protected String getDefaultContentType() {
        return DEFAULT_SOAP11_CONTENT_TYPE;
    }

    protected List<String> getExpectedContentTypes() {
        return EXPECTED_CONTENT_TYPES;
    }
}
