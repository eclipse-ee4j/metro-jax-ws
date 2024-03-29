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

public interface FaultType
    extends TypedXmlWriter, Documented
{


    @XmlAttribute
    com.sun.xml.ws.wsdl.writer.document.FaultType message(QName value);

    @XmlAttribute
    com.sun.xml.ws.wsdl.writer.document.FaultType name(String value);

}
