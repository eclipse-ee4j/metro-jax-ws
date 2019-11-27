/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.parser;

import com.sun.xml.stream.buffer.XMLStreamBufferResult;
import com.sun.xml.ws.api.server.SDDocumentSource;
import com.sun.xml.ws.api.wsdl.parser.XMLEntityResolver;
import com.sun.xml.ws.util.JAXWSUtils;
import com.sun.xml.ws.util.xml.XmlUtil;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity resolver that works on MEX Metadata
 *
 * @author Vivek Pandey
 */
public final class MexEntityResolver implements XMLEntityResolver {
    private final Map<String, SDDocumentSource> wsdls = new HashMap<String, SDDocumentSource>();

    public MexEntityResolver(List<? extends Source> wsdls) throws IOException {
        Transformer transformer = XmlUtil.newTransformer();
        for (Source source : wsdls) {
            XMLStreamBufferResult xsbr = new XMLStreamBufferResult();
            try {
                transformer.transform(source, xsbr);
            } catch (TransformerException e) {
                throw new WebServiceException(e);
            }
            String systemId = source.getSystemId();

            //TODO: can we do anything if the given mex Source has no systemId?
            if(systemId != null){
                SDDocumentSource doc = SDDocumentSource.create(JAXWSUtils.getFileOrURL(systemId), xsbr.getXMLStreamBuffer());
                this.wsdls.put(systemId, doc);
            }
        }
    }

    public Parser resolveEntity(String publicId, String systemId) throws SAXException, IOException, XMLStreamException {
        if (systemId != null) {
            SDDocumentSource src = wsdls.get(systemId);
            if (src != null)
                return new Parser(src);
        }
        return null;
    }
}
