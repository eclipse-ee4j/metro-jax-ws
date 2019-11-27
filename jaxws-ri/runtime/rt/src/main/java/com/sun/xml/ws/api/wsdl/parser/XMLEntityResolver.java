/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.wsdl.parser;

import com.sun.xml.ws.api.server.SDDocumentSource;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.net.URL;

/**
 * Resolves a reference to {@link XMLStreamReader}.
 *
 * This is kinda like {@link EntityResolver} but works
 * at the XML infoset level.
 *
 * @author Kohsuke Kawaguchi
 */
public interface XMLEntityResolver {
    /**
     * See {@link EntityResolver#resolveEntity(String, String)} for the contract.
     */
    Parser resolveEntity(String publicId,String systemId)
        throws SAXException, IOException, XMLStreamException;

    public static final class Parser {
        /**
         * System ID of the document being parsed.
         */
        public final URL systemId;
        /**
         * The parser instance parsing the infoset.
         */
        public final XMLStreamReader parser;

        public Parser(URL systemId, XMLStreamReader parser) {
            assert parser!=null;
            this.systemId = systemId;
            this.parser = parser;
        }

        /**
         * Creates a {@link Parser} that reads from {@link SDDocumentSource}.
         */
        public Parser(SDDocumentSource doc) throws IOException, XMLStreamException {
            this.systemId = doc.getSystemId();
            this.parser = doc.read();
        }

    }
}
