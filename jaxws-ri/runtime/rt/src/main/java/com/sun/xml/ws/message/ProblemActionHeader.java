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
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.message.Header;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPHeaderElement;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * {@link Header} that represents &lt;wsa:ProblemAction&gt;
 * @author Arun Gupta
 */
public class ProblemActionHeader extends AbstractHeaderImpl {
    protected @NotNull String action;
    protected String soapAction;
    protected @NotNull AddressingVersion av;

    private static final String actionLocalName = "Action";
    private static final String soapActionLocalName = "SoapAction";

    public ProblemActionHeader(@NotNull String action, @NotNull AddressingVersion av) {
        this(action,null,av);
    }

    public ProblemActionHeader(@NotNull String action, String soapAction, @NotNull AddressingVersion av) {
        assert action!=null;
        assert av!=null;
        this.action = action;
        this.soapAction = soapAction;
        this.av = av;
    }

    @Override
    public
    @NotNull
    String getNamespaceURI() {
        return av.nsUri;
    }

    @Override
    public
    @NotNull
    String getLocalPart() {
        return "ProblemAction";
    }

    @Override
    @Nullable
    public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
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
        w.writeStartElement("", getLocalPart(), getNamespaceURI());
        w.writeDefaultNamespace(getNamespaceURI());
        w.writeStartElement(actionLocalName);
        w.writeCharacters(action);
        w.writeEndElement();
        if (soapAction != null) {
            w.writeStartElement(soapActionLocalName);
            w.writeCharacters(soapAction);
            w.writeEndElement();
        }
        w.writeEndElement();
    }

    @Override
    public void writeTo(SOAPMessage saaj) throws SOAPException {
        SOAPHeader header = saaj.getSOAPHeader();
        if(header == null)
            header = saaj.getSOAPPart().getEnvelope().addHeader();
        SOAPHeaderElement she = header.addHeaderElement(new QName(getNamespaceURI(), getLocalPart()));
        she.addChildElement(actionLocalName);
        she.addTextNode(action);
        if (soapAction != null) {
            she.addChildElement(soapActionLocalName);
            she.addTextNode(soapAction);
        }
    }

    @Override
    public void writeTo(ContentHandler h, ErrorHandler errorHandler) throws SAXException {
        String nsUri = getNamespaceURI();
        String ln = getLocalPart();

        h.startPrefixMapping("",nsUri);
        h.startElement(nsUri,ln,ln,EMPTY_ATTS);
        h.startElement(nsUri,actionLocalName,actionLocalName,EMPTY_ATTS);
        h.characters(action.toCharArray(),0,action.length());
        h.endElement(nsUri,actionLocalName,actionLocalName);
        if (soapAction != null) {
            h.startElement(nsUri,soapActionLocalName,soapActionLocalName,EMPTY_ATTS);
            h.characters(soapAction.toCharArray(),0,soapAction.length());
            h.endElement(nsUri,soapActionLocalName,soapActionLocalName);
        }
        h.endElement(nsUri,ln,ln);
    }
}
