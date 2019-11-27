/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.writer;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.sun.xml.txw2.TypedXmlWriter;

public class TXWContentHandler implements ContentHandler {
    Stack<TypedXmlWriter> stack;
    
    public TXWContentHandler(TypedXmlWriter txw) {
        stack = new Stack<TypedXmlWriter>();
        stack.push(txw);
    }

    public void setDocumentLocator(Locator locator) {
    }

    public void startDocument() throws SAXException {
    }
    
    public void endDocument() throws SAXException {
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }
    
    public void endPrefixMapping(String prefix) throws SAXException {
    }

    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        TypedXmlWriter txw = stack.peek()._element(uri, localName, TypedXmlWriter.class);
        stack.push(txw);
        if (atts != null) {
            for(int i = 0; i < atts.getLength(); i++)  {
                String auri = atts.getURI(i);
                if ("http://www.w3.org/2000/xmlns/".equals(auri)) {
                    if ("xmlns".equals(atts.getLocalName(i)))
                        txw._namespace(atts.getValue(i), "");
                    else
                        txw._namespace(atts.getValue(i),atts.getLocalName(i));
                } else {
                    if ("schemaLocation".equals(atts.getLocalName(i))
                            && "".equals(atts.getValue(i)))
                        continue;
                    txw._attribute(auri, atts.getLocalName(i), atts.getValue(i));
                }
            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        stack.pop();
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
    }
    
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    public void processingInstruction(String target, String data) throws SAXException {
    }

    public void skippedEntity(String name) throws SAXException {
    }

}
