/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message.stream;

import com.sun.istack.FinalArrayList;
import com.sun.istack.NotNull;
import com.sun.xml.stream.buffer.XMLStreamBuffer;
import com.sun.xml.stream.buffer.XMLStreamBufferSource;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.message.AbstractHeaderImpl;
import com.sun.xml.ws.util.xml.XmlUtil;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import java.util.List;
import java.util.Set;

/**
 * {@link Header} whose physical data representation is an XMLStreamBuffer.
 *
 * @author Paul.Sandoz@Sun.Com
 */
public abstract class StreamHeader extends AbstractHeaderImpl {
    protected final XMLStreamBuffer _mark;

    protected boolean _isMustUnderstand;

    /**
     * Role or actor value.
     */
    protected @NotNull String _role;

    protected boolean _isRelay;

    protected String _localName;

    protected String _namespaceURI;

    /**
     * Keep the information about an attribute on the header element.
     *
     * TODO: this whole attribute handling could be done better, I think.
     */
    protected static final class Attribute {
        /**
         * Can be empty but never null.
         */
        final String nsUri;
        final String localName;
        final String value;

        public Attribute(String nsUri, String localName, String value) {
            this.nsUri = fixNull(nsUri);
            this.localName = localName;
            this.value = value;
        }
    }

    /**
     * The attributes on the header element.
     * We expect there to be only a small number of them,
     * so the use of {@link List} would be justified.
     *
     * Null if no attribute is present.
     */
    private final FinalArrayList<Attribute> attributes;

    /**
     * Creates a {@link StreamHeader}.
     *
     * @param reader
     *      The parser pointing at the start of the mark.
     *      Technically this information is redundant,
     *      but it achieves a better performance.
     * @param mark
     *      The start of the buffered header content.
     */
    protected StreamHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
        assert reader!=null && mark!=null;
        _mark = mark;
        _localName = reader.getLocalName();
        _namespaceURI = reader.getNamespaceURI();
        attributes = processHeaderAttributes(reader);
    }

    /**
     * Creates a {@link StreamHeader}.
     *
     * @param reader
     *      The parser that points to the start tag of the header.
     *      By the end of this method, the parser will point at
     *      the end tag of this element.
     */
    protected StreamHeader(XMLStreamReader reader) throws XMLStreamException {
        _localName = reader.getLocalName();
        _namespaceURI = reader.getNamespaceURI();
        attributes = processHeaderAttributes(reader);
        // cache the body
        _mark = XMLStreamBuffer.createNewBufferFromXMLStreamReader(reader);
    }

    @Override
    public final boolean isIgnorable(@NotNull SOAPVersion soapVersion, @NotNull Set<String> roles) {
        // check mustUnderstand
        if(!_isMustUnderstand) return true;

        if (roles == null)
            return true;

        // now role
        return !roles.contains(_role);
    }

    @Override
    public @NotNull String getRole(@NotNull SOAPVersion soapVersion) {
        assert _role!=null;
        return _role;
    }

    @Override
    public boolean isRelay() {
        return _isRelay;
    }

    @Override
    public @NotNull String getNamespaceURI() {
        return _namespaceURI;
    }

    @Override
    public @NotNull String getLocalPart() {
        return _localName;
    }

    @Override
    public String getAttribute(String nsUri, String localName) {
        if(attributes!=null) {
            for(int i=attributes.size()-1; i>=0; i-- ) {
                Attribute a = attributes.get(i);
                if(a.localName.equals(localName) && a.nsUri.equals(nsUri))
                    return a.value;
            }
        }
        return null;
    }

    /**
     * Reads the header as a {@link XMLStreamReader}
     */
    @Override
    public XMLStreamReader readHeader() throws XMLStreamException {
        return _mark.readAsXMLStreamReader();
    }

    @Override
    public void writeTo(XMLStreamWriter w) throws XMLStreamException {
        if(_mark.getInscopeNamespaces().size() > 0)
            _mark.writeToXMLStreamWriter(w,true);
        else
            _mark.writeToXMLStreamWriter(w);
    }

    @Override
    public void writeTo(SOAPMessage saaj) throws SOAPException {
        try {
            // TODO what about in-scope namespaces
            // Not very efficient consider implementing a stream buffer
            // processor that produces a DOM node from the buffer.
            TransformerFactory tf = XmlUtil.newTransformerFactory(true);
            Transformer t = tf.newTransformer();
            XMLStreamBufferSource source = new XMLStreamBufferSource(_mark);
            DOMResult result = new DOMResult();
            t.transform(source, result);
            Node d = result.getNode();
            if(d.getNodeType() == Node.DOCUMENT_NODE)
                d = d.getFirstChild();
            SOAPHeader header = saaj.getSOAPHeader();
            if(header == null)
                header = saaj.getSOAPPart().getEnvelope().addHeader();
            Node node = header.getOwnerDocument().importNode(d, true);
            header.appendChild(node);
        } catch (Exception e) {
            throw new SOAPException(e);
        }
    }

    @Override
    public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
        _mark.writeTo(contentHandler);
    }

    /**
     * Creates an EPR without copying infoset.
     *
     * This is the most common implementation on which {@link Header#readAsEPR(AddressingVersion)}
     * is invoked on.
     */
    @Override @NotNull
    public WSEndpointReference readAsEPR(AddressingVersion expected) throws XMLStreamException {
        return new WSEndpointReference(_mark,expected);
    }

    protected abstract FinalArrayList<Attribute> processHeaderAttributes(XMLStreamReader reader);

    /**
     * Convert null to "".
     */
    private static String fixNull(String s) {
        if(s==null) return "";
        else        return s;
    }
}
