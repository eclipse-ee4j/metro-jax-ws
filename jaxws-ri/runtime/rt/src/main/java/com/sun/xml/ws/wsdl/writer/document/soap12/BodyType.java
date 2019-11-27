/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.writer.document.soap12;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;

/**
 *
 * @author WS Development Team
 */
public interface BodyType
    extends TypedXmlWriter
{


    @XmlAttribute
    public com.sun.xml.ws.wsdl.writer.document.soap12.BodyType encodingStyle(String value);

    @XmlAttribute
    public com.sun.xml.ws.wsdl.writer.document.soap12.BodyType namespace(String value);

    @XmlAttribute
    public com.sun.xml.ws.wsdl.writer.document.soap12.BodyType use(String value);

    @XmlAttribute
    public com.sun.xml.ws.wsdl.writer.document.soap12.BodyType parts(String value);

}
