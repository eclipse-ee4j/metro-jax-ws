/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.writer.document.xsd;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;
import com.sun.xml.ws.wsdl.writer.document.*;

/**
 *
 * @author WS Development Team
 */
@XmlElement("schema")
public interface Schema
    extends TypedXmlWriter, Documented
{


    @XmlElement("import")
    public Import _import();
    
    @XmlAttribute
    public com.sun.xml.ws.wsdl.writer.document.xsd.Schema targetNamespace(String value);
}
