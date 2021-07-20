/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * WS-Addressing &lt;RelatesTo$gt; header.
 *
 * Used for outbound only.
 *
 * @author Arun Gupta
 */
public final class RelatesToHeader extends StringHeader {
    protected String type;
    private final QName typeAttributeName;

    public RelatesToHeader(QName name, String messageId, String type) {
        super(name, messageId);
        this.type = type;
        this.typeAttributeName = new QName(name.getNamespaceURI(), "type");
    }

    public RelatesToHeader(QName name, String mid) {
        super(name, mid);
        this.typeAttributeName = new QName(name.getNamespaceURI(), "type");
    }

    public String getType() {
        return type;
    }

    @Override
    public void writeTo(XMLStreamWriter w) throws XMLStreamException {
        w.writeStartElement("", name.getLocalPart(), name.getNamespaceURI());
        w.writeDefaultNamespace(name.getNamespaceURI());
        if (type != null)
            w.writeAttribute("type", type);
        w.writeCharacters(value);
        w.writeEndElement();
    }

    @Override
    public void writeTo(SOAPMessage saaj) throws SOAPException {
        SOAPHeader header = saaj.getSOAPHeader();
        if (header == null)
            header = saaj.getSOAPPart().getEnvelope().addHeader();
        SOAPHeaderElement she = header.addHeaderElement(name);

        if (type != null)
            she.addAttribute(typeAttributeName, type);
        she.addTextNode(value);
    }
}
