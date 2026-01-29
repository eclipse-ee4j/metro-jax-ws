/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
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
import com.sun.xml.ws.wsdl.writer.document.Documented;

/**
 *
 * @author WS Development Team
 */
@XmlElement("import")
public interface Import extends TypedXmlWriter, Documented {

    @XmlAttribute
    com.sun.xml.ws.wsdl.writer.document.xsd.Import schemaLocation(String value);

    @XmlAttribute
    com.sun.xml.ws.wsdl.writer.document.xsd.Import namespace(String value);

}
