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

import javax.xml.namespace.QName;
import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;
import com.sun.xml.ws.wsdl.writer.document.soap.SOAPBinding;

/**
 *
 * @author WS Development Team
 */
@XmlElement("binding")
public interface Binding
    extends TypedXmlWriter, StartWithExtensionsType
{


    @XmlAttribute
    com.sun.xml.ws.wsdl.writer.document.Binding type(QName value);

    @XmlAttribute
    com.sun.xml.ws.wsdl.writer.document.Binding name(String value);

    @XmlElement
    BindingOperationType operation();

    @XmlElement(value="binding",ns="http://schemas.xmlsoap.org/wsdl/soap/")
    SOAPBinding soapBinding();

    @XmlElement(value="binding",ns="http://schemas.xmlsoap.org/wsdl/soap12/")
    com.sun.xml.ws.wsdl.writer.document.soap12.SOAPBinding soap12Binding();

}
