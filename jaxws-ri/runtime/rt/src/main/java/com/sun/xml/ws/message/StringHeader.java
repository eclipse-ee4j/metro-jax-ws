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

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.Header;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPHeaderElement;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * {@link Header} that has a single text value in it
 * (IOW, of the form &lt;foo&gt;text&lt;/foo&gt;.)
 *
 * @author Rama Pulavarthi
 * @author Arun Gupta
 */
public class StringHeader extends AbstractHeaderImpl {
    /**
     * Tag name.
     */
    protected final QName name;
    /**
     * Header value.
     */
    protected final String value;

    protected boolean mustUnderstand = false;
    protected SOAPVersion soapVersion;

    public StringHeader(@NotNull QName name, @NotNull String value) {
        assert name != null;
        assert value != null;
        this.name = name;
        this.value = value;
    }

    public StringHeader(@NotNull QName name, @NotNull String value, @NotNull SOAPVersion soapVersion, boolean mustUnderstand ) {
        this.name = name;
        this.value = value;
        this.soapVersion = soapVersion;
        this.mustUnderstand = mustUnderstand;
    }

    @Override
    public @NotNull String getNamespaceURI() {
        return name.getNamespaceURI();
    }

    @Override
    public @NotNull String getLocalPart() {
        return name.getLocalPart();
    }

    @Override
    @Nullable public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
        if(mustUnderstand && soapVersion.nsUri.equals(nsUri) && MUST_UNDERSTAND.equals(localName)) {
            return getMustUnderstandLiteral(soapVersion);
        }
        return null;
    }

    @Override
    public XMLStreamReader readHeader() throws XMLStreamException {
        MutableXMLStreamBuffer buf = new MutableXMLStreamBuffer();
        XMLStreamWriter w = buf.createFromXMLStreamWriter();
        writeTo(w);
        return buf.readAsXMLStreamReader();
    }

    @Override
    public void writeTo(XMLStreamWriter w) throws XMLStreamException {
        w.writeStartElement("", name.getLocalPart(), name.getNamespaceURI());
        w.writeDefaultNamespace(name.getNamespaceURI());
        if (mustUnderstand) {
            //Writing the ns declaration conditionally checking in the NSContext breaks XWSS. as readHeader() adds ns declaration,
            // where as writing alonf with the soap envelope does n't add it.
            //Looks like they expect the readHeader() and writeTo() produce the same infoset, Need to understand their usage

            //if(w.getNamespaceContext().getPrefix(soapVersion.nsUri) == null) {
            w.writeNamespace("S", soapVersion.nsUri);
            w.writeAttribute("S", soapVersion.nsUri, MUST_UNDERSTAND, getMustUnderstandLiteral(soapVersion));
            // } else {
            // w.writeAttribute(soapVersion.nsUri,MUST_UNDERSTAND, getMustUnderstandLiteral(soapVersion));
            // }
        }
        w.writeCharacters(value);
        w.writeEndElement();
    }

    @Override
    public void writeTo(SOAPMessage saaj) throws SOAPException {
        SOAPHeader header = saaj.getSOAPHeader();
        if(header == null)
            header = saaj.getSOAPPart().getEnvelope().addHeader();
        SOAPHeaderElement she = header.addHeaderElement(name);
        if(mustUnderstand) {
            she.setMustUnderstand(true);
        }
        she.addTextNode(value);
    }

    @Override
    public void writeTo(ContentHandler h, ErrorHandler errorHandler) throws SAXException {
        String nsUri = name.getNamespaceURI();
        String ln = name.getLocalPart();

        h.startPrefixMapping("",nsUri);
        if(mustUnderstand) {
            AttributesImpl attributes = new AttributesImpl();
            attributes.addAttribute(soapVersion.nsUri,MUST_UNDERSTAND,"S:"+MUST_UNDERSTAND,"CDATA", getMustUnderstandLiteral(soapVersion));
            h.startElement(nsUri,ln,ln,attributes);
        } else {
            h.startElement(nsUri,ln,ln,EMPTY_ATTS);
        }
        h.characters(value.toCharArray(),0,value.length());
        h.endElement(nsUri,ln,ln);
    }

    private static String getMustUnderstandLiteral(SOAPVersion sv) {
        if(sv == SOAPVersion.SOAP_12) {
            return S12_MUST_UNDERSTAND_TRUE;
        } else {
            return S11_MUST_UNDERSTAND_TRUE;
        }

    }

    protected static final String MUST_UNDERSTAND = "mustUnderstand";
    protected static final String S12_MUST_UNDERSTAND_TRUE ="true";
    protected static final String S11_MUST_UNDERSTAND_TRUE ="1";
}
