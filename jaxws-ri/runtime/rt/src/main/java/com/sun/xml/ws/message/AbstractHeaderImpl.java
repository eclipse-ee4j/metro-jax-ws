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
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
import com.sun.xml.ws.spi.db.XMLBridge;

import org.xml.sax.helpers.AttributesImpl;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Set;

/**
 * Partial default implementation of {@link Header}.
 *
 * <p>
 * This is meant to be a convenient base class
 * for {@link Header}-derived classes.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class AbstractHeaderImpl implements Header {

    protected AbstractHeaderImpl() {
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
        try {
            return (T)unmarshaller.unmarshal(readHeader());
        } catch (Exception e) {
            throw new JAXBException(e);
        }
    }

    @Override
    public <T> T readAsJAXB(XMLBridge<T> bridge) throws JAXBException {
        try {
            return bridge.unmarshal(readHeader(), null);
        } catch (XMLStreamException e) {
            throw new JAXBException(e);
        }
    }

    /**
     * Default implementation that copies the infoset. Not terribly efficient.
     */
    @Override
    public WSEndpointReference readAsEPR(AddressingVersion expected) throws XMLStreamException {
        XMLStreamReader xsr = readHeader();
        WSEndpointReference epr = new WSEndpointReference(xsr, expected);
        XMLStreamReaderFactory.recycle(xsr);
        return epr;
    }

    @Override
    public boolean isIgnorable(@NotNull SOAPVersion soapVersion, @NotNull Set<String> roles) {
        // check mustUnderstand
        String v = getAttribute(soapVersion.nsUri, "mustUnderstand");
        if(v==null || !parseBool(v)) return true;

        if (roles == null) return true;
        
        // now role
        return !roles.contains(getRole(soapVersion));
    }

    @Override
    public @NotNull String getRole(@NotNull SOAPVersion soapVersion) {
        String v = getAttribute(soapVersion.nsUri, soapVersion.roleAttributeName);
        if(v==null)
            v = soapVersion.implicitRole;
        return v;
    }

    @Override
    public boolean isRelay() {
        String v = getAttribute(SOAPVersion.SOAP_12.nsUri,"relay");
        if(v==null) return false;   // on SOAP 1.1 message there shouldn't be such an attribute, so this works fine
        return parseBool(v);
    }

    @Override
    public String getAttribute(QName name) {
        return getAttribute(name.getNamespaceURI(),name.getLocalPart());
    }

    /**
     * Parses a string that looks like {@code xs:boolean} into boolean.
     *
     * This method assumes that the whilespace normalization has already taken place.
     */
    protected final boolean parseBool(String value) {
        if(value.length()==0)
            return false;

        char ch = value.charAt(0);
        return ch=='t' || ch=='1';
    }

    @Override
    public String getStringContent() {
        try {
            XMLStreamReader xsr = readHeader();
            xsr.nextTag();
            return xsr.getElementText();
        } catch (XMLStreamException e) {
            return null;
        }
    }

    protected static final AttributesImpl EMPTY_ATTS = new AttributesImpl();
}
