/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.epr_extensions.common;

import com.sun.xml.ws.api.server.EndpointReferenceExtensionContributor;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.api.addressing.WSEndpointReference;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;

/**
 * @author Rama.Pulavarthi@sun.com
 */
public class IdentityEPRExtnContributor extends EndpointReferenceExtensionContributor {
    private String s = "<Identity xmlns=\"http://example.com/addressingidentity\">" +
            "<KeyInfo xmlns=\"http://www.w3.org/2000/09/xmldsig#\">" +
            "<X509Data>" +
            "<X509Certificate>" +
            "1234567890" +
            "</X509Certificate>" +
            "</X509Data>" +
            "</KeyInfo>" +
            "</Identity>";

    QName ID_QNAME = new QName("http://example.com/addressingidentity", "Identity");

    public WSEndpointReference.EPRExtension getEPRExtension(WSEndpoint endpoint, WSEndpointReference.EPRExtension extension) {
        return new WSEndpointReference.EPRExtension() {
            public XMLStreamReader readAsXMLStreamReader() throws XMLStreamException {
                XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new ByteArrayInputStream(s.getBytes()));
                return reader;
            }

            public QName getQName() {
                return ID_QNAME;
            }
        };
    }

    public QName getQName() {
        return ID_QNAME;
    }
}
