/*
 * Copyright (c) 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.parser;

import com.sun.tools.ws.resources.WsdlMessages;
import com.sun.tools.ws.wsdl.document.jaxws.JAXWSBindingsConstants;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.LocatorImpl;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * Upgrades the jaxws 2.x customizations in a WSDL document
 * to version 3.0 and prints warnings.
 *
 */
final class V2NSHandler extends XMLFilterImpl {

    private Locator locator;

    /**
     * Stores the location of the start tag of the root tag.
     */
    private Locator rootTagStart;

    /** Will be set to true once we hit the root element. */
    private boolean seenRoot = false;

    /** Will be set to true once we hit a JAXWS 2.x binding declaration. */
    private boolean seenOldBindings = false;

    /** Will be set to true once we hit a JAXWS 2.x binding version attribute declaration. */
    private boolean seenOldBindingsVersion = false;

    public V2NSHandler(ContentHandler handler, ErrorHandler eh, EntityResolver er) {
        setContentHandler(handler);
        if(eh!=null)    setErrorHandler(eh);
        if(er!=null)    setEntityResolver(er);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if(!seenRoot) {
            // if this is the root element
            seenRoot = true;
            rootTagStart = new LocatorImpl(locator);
        }

        AttributesImpl as = new AttributesImpl();
        for (int i = 0; i < atts.getLength(); i++) {
            if ("http://java.sun.com/xml/ns/jaxws".equals(atts.getURI(i))) {
                if ("version".equals(atts.getLocalName(i))) {
                    as.addAttribute(JAXWSBindingsConstants.NS_JAXWS_BINDINGS, atts.getLocalName(i), atts.getQName(i), atts.getType(i), "3.0");
                    seenOldBindingsVersion = true;
                } else {
                    as.addAttribute(JAXWSBindingsConstants.NS_JAXWS_BINDINGS, atts.getLocalName(i), atts.getQName(i), atts.getType(i), atts.getValue(i));
                    seenOldBindings = true;
                }
            } else {
                as.addAttribute(atts.getURI(i), atts.getLocalName(i), atts.getQName(i), atts.getType(i), atts.getValue(i));
            }
        }
        if ("http://java.sun.com/xml/ns/jaxws".equals(uri)) {
            super.startElement(JAXWSBindingsConstants.NS_JAXWS_BINDINGS, localName, qName, as);
            seenOldBindings = true;
        } else {
            super.startElement(uri, localName, qName, as);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        if (seenOldBindings) {
            SAXParseException e = new SAXParseException(WsdlMessages.INTERNALIZER_OLD_CUSTOMIZATION_NS(), rootTagStart);
            getErrorHandler().warning(e);
        }
        if (seenOldBindingsVersion) {
            SAXParseException e = new SAXParseException(WsdlMessages.INTERNALIZER_OLD_CUSTOMIZATION_VERSION(), rootTagStart);
            getErrorHandler().warning(e);
        }
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
        this.locator = locator;
    }
}
