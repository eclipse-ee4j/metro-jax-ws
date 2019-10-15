/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Sniffs the root element name and its attributes from SAX events.
 *
 * @author Kohsuke Kawaguchi
 */
public final class RootElementSniffer extends DefaultHandler {
    private String nsUri = "##error";
    private String localName = "##error";
    private Attributes atts;

    private final boolean parseAttributes;

    public RootElementSniffer(boolean parseAttributes) {
        this.parseAttributes = parseAttributes;
    }

    public RootElementSniffer() {
        this(true);
    }

    public void startElement(String uri, String localName, String qName, Attributes a) throws SAXException {
        this.nsUri = uri;
        this.localName = localName;
        
        if(parseAttributes) {
            if(a.getLength()==0)    // often there's no attribute
                this.atts = EMPTY_ATTRIBUTES;
            else
                this.atts = new AttributesImpl(a);
        }

        // no need to parse any further.
        throw aSAXException;
    }

    public String getNsUri() {
        return nsUri;
    }

    public String getLocalName() {
        return localName;
    }

    public Attributes getAttributes() {
        return atts;
    }

    private static final SAXException aSAXException = new SAXException();
    private static final Attributes EMPTY_ATTRIBUTES = new AttributesImpl();
}
