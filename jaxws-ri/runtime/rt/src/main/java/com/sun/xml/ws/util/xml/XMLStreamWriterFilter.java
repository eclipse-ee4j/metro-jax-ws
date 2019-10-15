/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util.xml;

import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory.RecycleAware;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * {@link XMLStreamWriter} that delegates to another {@link XMLStreamWriter}.
 *
 * <p>
 * This class isn't very useful by itself, but works as a base class
 * for {@link XMLStreamWriter} filtering.
 *
 * @author Kohsuke Kawaguchi
 */
public class XMLStreamWriterFilter implements XMLStreamWriter, RecycleAware {
    protected XMLStreamWriter writer;

    public XMLStreamWriterFilter(XMLStreamWriter writer) {
        this.writer = writer;
    }

    public void close() throws XMLStreamException {
        writer.close();
    }

    public void flush() throws XMLStreamException {
        writer.flush();
    }

    public void writeEndDocument() throws XMLStreamException {
        writer.writeEndDocument();
    }

    public void writeEndElement() throws XMLStreamException {
        writer.writeEndElement();
    }

    public void writeStartDocument() throws XMLStreamException {
        writer.writeStartDocument();
    }

    private boolean isUnusualChar(char c) {
        if ((c<=31) && (c != '\t') && (c != '\n') && (c != '\r')) {
            return true;
        }
        return false;
    }

    private String transformWhiteSpaces(String text) {
        char[] cstr = text.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c:cstr) {
            if (isUnusualChar(c)) {
                sb.append("&#").append(Integer.toString(c)).append(";");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private void transformWhiteSpaces(char[] text, int start, int len) {
        for (int i=0; i<len; i++) {
            if (i+start >= text.length) {
                break;
            }
            if (isUnusualChar(text[i])) {
                text[i] = ' '; //change it to ' '
            }
        }
    }

    public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
        transformWhiteSpaces(text, start, len);
        writer.writeCharacters(text, start, len);
    }

    public void setDefaultNamespace(String uri) throws XMLStreamException {
        writer.setDefaultNamespace(uri);
    }

    public void writeCData(String data) throws XMLStreamException {
        writer.writeCData(data);
    }

    public void writeCharacters(String text) throws XMLStreamException {
        text = transformWhiteSpaces(text);
        writer.writeCharacters(text);
    }

    public void writeComment(String data) throws XMLStreamException {
        writer.writeComment(data);
    }

    public void writeDTD(String dtd) throws XMLStreamException {
        writer.writeDTD(dtd);
    }

    public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
        writer.writeDefaultNamespace(namespaceURI);
    }

    public void writeEmptyElement(String localName) throws XMLStreamException {
        writer.writeEmptyElement(localName);
    }

    public void writeEntityRef(String name) throws XMLStreamException {
        writer.writeEntityRef(name);
    }

    public void writeProcessingInstruction(String target) throws XMLStreamException {
        writer.writeProcessingInstruction(target);
    }

    public void writeStartDocument(String version) throws XMLStreamException {
        writer.writeStartDocument(version);
    }

    public void writeStartElement(String localName) throws XMLStreamException {
        writer.writeStartElement(localName);
    }

    public NamespaceContext getNamespaceContext() {
        return writer.getNamespaceContext();
    }

    public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
        writer.setNamespaceContext(context);
    }

    public Object getProperty(String name) throws IllegalArgumentException {
        return writer.getProperty(name);
    }

    public String getPrefix(String uri) throws XMLStreamException {
        return writer.getPrefix(uri);
    }

    public void setPrefix(String prefix, String uri) throws XMLStreamException {
        writer.setPrefix(prefix, uri);
    }

    public void writeAttribute(String localName, String value) throws XMLStreamException {
        writer.writeAttribute(localName, value);
    }

    public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
        writer.writeEmptyElement(namespaceURI, localName);
    }

    public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
        writer.writeNamespace(prefix, namespaceURI);
    }

    public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
        writer.writeProcessingInstruction(target, data);
    }

    public void writeStartDocument(String encoding, String version) throws XMLStreamException {
        writer.writeStartDocument(encoding, version);
    }

    public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
        writer.writeStartElement(namespaceURI, localName);
    }

    public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
        writer.writeAttribute(namespaceURI, localName, value);
    }

    public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        writer.writeEmptyElement(prefix, localName, namespaceURI);
    }

    public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        writer.writeStartElement(prefix, localName, namespaceURI);
    }

    public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
        writer.writeAttribute(prefix, namespaceURI, localName, value);
    }

    public void onRecycled() {
        XMLStreamWriterFactory.recycle(writer);
        writer = null;
    }
}
