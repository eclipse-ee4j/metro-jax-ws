/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message.jaxb;

import com.sun.istack.NotNull;
import com.sun.istack.XMLStreamException2;
import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
import com.sun.xml.stream.buffer.XMLStreamBuffer;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.encoding.SOAPBindingCodec;
import com.sun.xml.ws.message.AbstractHeaderImpl;
import com.sun.xml.ws.message.RootElementSniffer;
import com.sun.xml.ws.spi.db.BindingContext;
import com.sun.xml.ws.spi.db.XMLBridge;
import com.sun.xml.ws.streaming.XMLStreamWriterUtil;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.util.JAXBResult;
import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPHeader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;

/**
 * {@link Header} whose physical data representation is a JAXB bean.
 *
 * @author Kohsuke Kawaguchi
 */
public final class JAXBHeader extends AbstractHeaderImpl {

    /**
     * The JAXB object that represents the header.
     */
    private final Object jaxbObject;

    private final XMLBridge bridge;

    // information about this header. lazily obtained.
    private String nsUri;
    private String localName;
    private Attributes atts;

    /**
     * Once the header is turned into infoset,
     * this buffer keeps it.
     */
    private XMLStreamBuffer infoset;

    public JAXBHeader(BindingContext context, Object jaxbObject) {
        this.jaxbObject = jaxbObject;
        this.bridge = context.createFragmentBridge();

        if (jaxbObject instanceof JAXBElement) {
            JAXBElement e = (JAXBElement) jaxbObject;
            this.nsUri = e.getName().getNamespaceURI();
            this.localName = e.getName().getLocalPart();
        }
    }

    public JAXBHeader(XMLBridge bridge, Object jaxbObject) {
        this.jaxbObject = jaxbObject;
        this.bridge = bridge;

        QName tagName = bridge.getTypeInfo().tagName;
        this.nsUri = tagName.getNamespaceURI();
        this.localName = tagName.getLocalPart();
    }

    /**
     * Lazily parse the first element to obtain attribute values on it.
     */
    private void parse() {
        RootElementSniffer sniffer = new RootElementSniffer();
        try {
            bridge.marshal(jaxbObject,sniffer,null);
        } catch (JAXBException e) {
            // if it's due to us aborting the processing after the first element,
            // we can safely ignore this exception.
            //
            // if it's due to error in the object, the same error will be reported
            // when the readHeader() method is used, so we don't have to report
            // an error right now.
            nsUri = sniffer.getNsUri();
            localName = sniffer.getLocalName();
            atts = sniffer.getAttributes();
        }
    }


    @Override
    public @NotNull String getNamespaceURI() {
        if(nsUri==null)
            parse();
        return nsUri;
    }

    @Override
    public @NotNull String getLocalPart() {
        if(localName==null)
            parse();
        return localName;
    }

    @Override
    public String getAttribute(String nsUri, String localName) {
        if(atts==null)
            parse();
        return atts.getValue(nsUri,localName);
    }

    @Override
    public XMLStreamReader readHeader() throws XMLStreamException {
        if(infoset==null) {
            MutableXMLStreamBuffer buffer = new MutableXMLStreamBuffer();
            writeTo(buffer.createFromXMLStreamWriter());
            infoset = buffer;
        }
        return infoset.readAsXMLStreamReader();
    }

    @Override
    public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
        try {
            JAXBResult r = new JAXBResult(unmarshaller);
            // bridge marshals a fragment, so we need to add start/endDocument by ourselves
            r.getHandler().startDocument();
            bridge.marshal(jaxbObject,r);
            r.getHandler().endDocument();
            return (T)r.getResult();
        } catch (SAXException e) {
            throw new JAXBException(e);
        }
    }

    @Override
    public <T> T readAsJAXB(XMLBridge<T> bond) throws JAXBException {
        return bond.unmarshal(new JAXBBridgeSource(this.bridge,jaxbObject),null);
	}

    @Override
    public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
        try {
            // Get the encoding of the writer
            String encoding = XMLStreamWriterUtil.getEncoding(sw);

            // Get output stream and use JAXB UTF-8 writer
            OutputStream os = bridge.supportOutputStream() ? XMLStreamWriterUtil.getOutputStream(sw) : null;
            if (os != null && encoding != null && encoding.equalsIgnoreCase(SOAPBindingCodec.UTF8_ENCODING)) {
                bridge.marshal(jaxbObject, os, sw.getNamespaceContext(), null);
            } else {
                bridge.marshal(jaxbObject,sw, null);
            }
        } catch (JAXBException e) {
            throw new XMLStreamException2(e);
        }
    }

    @Override
    public void writeTo(SOAPMessage saaj) throws SOAPException {
        try {
            SOAPHeader header = saaj.getSOAPHeader();
            if (header == null)
                header = saaj.getSOAPPart().getEnvelope().addHeader();
            bridge.marshal(jaxbObject,header);
        } catch (JAXBException e) {
            throw new SOAPException(e);
        }
    }

    @Override
    public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
        try {
            bridge.marshal(jaxbObject,contentHandler,null);
        } catch (JAXBException e) {
            SAXParseException x = new SAXParseException(e.getMessage(),null,null,-1,-1,e);
            errorHandler.fatalError(x);
            throw x;
        }
    }
}
