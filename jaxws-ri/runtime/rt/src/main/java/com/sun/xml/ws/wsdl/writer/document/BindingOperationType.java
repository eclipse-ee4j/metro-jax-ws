/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.writer.document;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;
import com.sun.xml.ws.wsdl.writer.document.soap.SOAPOperation;

/**
 *
 * @author WS Development Team
 */
public interface BindingOperationType
    extends TypedXmlWriter, StartWithExtensionsType
{


    @XmlAttribute
    com.sun.xml.ws.wsdl.writer.document.BindingOperationType name(String value);

    @XmlElement(value="operation",ns="http://schemas.xmlsoap.org/wsdl/soap/")
    SOAPOperation soapOperation();

    @XmlElement(value="operation",ns="http://schemas.xmlsoap.org/wsdl/soap12/")
    com.sun.xml.ws.wsdl.writer.document.soap12.SOAPOperation soap12Operation();

    @XmlElement
    Fault fault();

    @XmlElement
    StartWithExtensionsType output();

    @XmlElement
    StartWithExtensionsType input();

}
