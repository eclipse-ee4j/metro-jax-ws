/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding.fastinfoset;

import com.sun.xml.ws.api.pipe.ContentType;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
import com.sun.xml.ws.encoding.ContentTypeImpl;
import com.sun.xml.ws.message.stream.StreamHeader;
import com.sun.xml.ws.message.stream.StreamHeader12;
import com.sun.xml.stream.buffer.XMLStreamBuffer;

import javax.xml.stream.XMLStreamReader;

/**
 * A codec that converts SOAP 1.2 messages infosets to fast infoset
 * documents.
 *
 * @author Paul.Sandoz@Sun.Com
 */
final class FastInfosetStreamSOAP12Codec extends FastInfosetStreamSOAPCodec {
    /*package*/ FastInfosetStreamSOAP12Codec(StreamSOAPCodec soapCodec, boolean retainState) {
        super(soapCodec, SOAPVersion.SOAP_12, retainState, 
                (retainState) ? FastInfosetMIMETypes.STATEFUL_SOAP_12 : FastInfosetMIMETypes.SOAP_12);
    }

    private FastInfosetStreamSOAP12Codec(FastInfosetStreamSOAPCodec that) {
        super(that);
    }
    
    @Override
    public Codec copy() {
        return new FastInfosetStreamSOAP12Codec(this);
    }

    @Override
    protected StreamHeader createHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
        return new StreamHeader12(reader, mark);
    }
    
    @Override
    protected ContentType getContentType(String soapAction) {
        if (soapAction == null) {
            return _defaultContentType;
        } else {
            return new ContentTypeImpl(
                    _defaultContentType.getContentType() + ";action=\""+soapAction+"\"");
        }
    }
}
