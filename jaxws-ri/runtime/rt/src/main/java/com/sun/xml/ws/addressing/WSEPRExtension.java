/*
 * Copyright (c) 2009, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing;

import com.sun.xml.stream.buffer.XMLStreamBuffer;
import com.sun.xml.ws.api.addressing.WSEndpointReference;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;

/**
 * Implementation backed by XMLStreamBuffer
 * 
 * @author Rama.Pulavarthi@sun.com
 */
public class WSEPRExtension extends WSEndpointReference.EPRExtension {
    XMLStreamBuffer xsb;
    final QName qname;

    public WSEPRExtension(XMLStreamBuffer xsb, QName qname) {
        this.xsb = xsb;
        this.qname = qname;
    }


    @Override
    public XMLStreamReader readAsXMLStreamReader() throws XMLStreamException {
        return xsb.readAsXMLStreamReader();
    }

    @Override
    public QName getQName() {
        return qname;
    }

}
