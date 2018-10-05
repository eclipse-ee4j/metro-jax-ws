/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document;

import com.sun.tools.ws.wsdl.framework.Entity;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;

/**
 * Entity corresponding to the "import" WSDL element.
 *
 * @author WS Development Team
 */
public class Import extends Entity{

    public Import(Locator locator) {
        super(locator);
    }

    public String getNamespace() {
        return _namespace;
    }

    public void setNamespace(String s) {
        _namespace = s;
    }

    public String getLocation() {
        return _location;
    }

    public void setLocation(String s) {
        _location = s;
    }

    public QName getElementName() {
        return WSDLConstants.QNAME_IMPORT;
    }

    public Documentation getDocumentation() {
        return _documentation;
    }

    public void setDocumentation(Documentation d) {
        _documentation = d;
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    public void validateThis() {
        if (_location == null) {
            failValidation("validation.missingRequiredAttribute", "location");
        }
        if (_namespace == null) {
            failValidation("validation.missingRequiredAttribute", "namespace");
        }
    }

    private Documentation _documentation;
    private String _location;
    private String _namespace;
}
