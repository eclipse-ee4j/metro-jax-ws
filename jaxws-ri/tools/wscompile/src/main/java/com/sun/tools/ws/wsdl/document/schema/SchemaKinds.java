/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document.schema;

import com.sun.tools.ws.wsdl.framework.Kind;

/**
 *
 * @author WS Development Team
 */
public class SchemaKinds {
    public static final Kind XSD_ATTRIBUTE = new Kind("xsd:attribute");
    public static final Kind XSD_ATTRIBUTE_GROUP =
        new Kind("xsd:attributeGroup");
    public static final Kind XSD_CONSTRAINT = new Kind("xsd:constraint");
    public static final Kind XSD_ELEMENT = new Kind("xsd:element");
    public static final Kind XSD_GROUP = new Kind("xsd:group");
    public static final Kind XSD_IDENTITY_CONSTRAINT =
        new Kind("xsd:identityConstraint");
    public static final Kind XSD_NOTATION = new Kind("xsd:notation");
    public static final Kind XSD_TYPE = new Kind("xsd:type");

    private SchemaKinds() {
    }
}
