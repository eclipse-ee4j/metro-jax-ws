/*
 * Copyright (c) 2013, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.message;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public interface StreamingSOAP {
    XMLStreamReader readEnvelope();
    QName getPayloadQName();
    XMLStreamReader readToBodyStarTag() throws XMLStreamException;
    XMLStreamReader readPayload() throws XMLStreamException;
    void writeToBodyStart(XMLStreamWriter w) throws XMLStreamException;
    void writePayloadTo(XMLStreamWriter writer)throws XMLStreamException;
    boolean isPayloadStreamReader();
}
