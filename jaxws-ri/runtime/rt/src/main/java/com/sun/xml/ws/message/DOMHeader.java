/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message;

import org.glassfish.jaxb.runtime.api.Bridge;
import org.glassfish.jaxb.core.unmarshaller.DOMScanner;
import com.sun.xml.ws.streaming.DOMStreamReader;
import com.sun.xml.ws.util.DOMUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * {@link com.sun.xml.ws.api.message.Header} implementation for a DOM.
 *
 * @author Kohsuke Kawaguchi
 */
public class DOMHeader<N extends Element> extends AbstractHeaderImpl {
    protected final N node;

    private final String nsUri;
    private final String localName;

    public DOMHeader(N node) {
        assert node!=null;
        this.node = node;

        this.nsUri = fixNull(node.getNamespaceURI());
        this.localName = node.getLocalName();
    }


    @Override
    public String getNamespaceURI() {
        return nsUri;
    }

    @Override
    public String getLocalPart() {
        return localName;
    }

    @Override
    public XMLStreamReader readHeader() throws XMLStreamException {
        DOMStreamReader r = new DOMStreamReader(node);
        r.nextTag();    // move ahead to the start tag
        return r;
    }

    @Override
    public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
        return (T) unmarshaller.unmarshal(node);
    }
    /** @deprecated */
    @Override
    public <T> T readAsJAXB(Bridge<T> bridge) throws JAXBException {
        return bridge.unmarshal(node);
    }

    @Override
    public void writeTo(XMLStreamWriter w) throws XMLStreamException {
        DOMUtil.serializeNode(node, w);
    }

    private static String fixNull(String s) {
        if(s!=null)     return s;
        else            return "";
    }

    @Override
    public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
        DOMScanner ds = new DOMScanner();
        ds.setContentHandler(contentHandler);
        ds.scan(node);
    }

    @Override
    public String getAttribute(String nsUri, String localName) {
        if(nsUri.length()==0)   nsUri=null; // DOM wants null, not "".
        return node.getAttributeNS(nsUri,localName);
    }

    @Override
    public void writeTo(SOAPMessage saaj) throws SOAPException {
        SOAPHeader header = saaj.getSOAPHeader();
        if(header == null)
            header = saaj.getSOAPPart().getEnvelope().addHeader();
        Node clone = header.getOwnerDocument().importNode(node,true);
        header.appendChild(clone);
    }

    @Override
    public String getStringContent() {
        return node.getTextContent();
    }
    
    public N getWrappedNode() {
        return node;
    }


    @Override
    public int hashCode() {
        return getWrappedNode().hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DOMHeader) {
            return getWrappedNode().equals(((DOMHeader) obj).getWrappedNode());
        } else {
            return false;
        }
    }
    
    
}
