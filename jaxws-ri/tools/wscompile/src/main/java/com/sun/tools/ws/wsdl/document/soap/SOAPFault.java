/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document.soap;

import com.sun.tools.ws.wsdl.framework.ExtensionImpl;
import com.sun.tools.ws.wsdl.framework.ValidationException;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;

/**
 * A SOAP fault extension.
 *
 * @author WS Development Team
 */
public class SOAPFault extends ExtensionImpl {

    public SOAPFault(Locator locator) {
        super(locator);
        _use = SOAPUse.LITERAL;
    }

    public QName getElementName() {
        return SOAPConstants.QNAME_FAULT;
    }

    public String getName() {
        return _name;
    }

    public void setName(String s) {
        _name = s;
    }

    public String getNamespace() {
        return _namespace;
    }

    public void setNamespace(String s) {
        _namespace = s;
    }

    public SOAPUse getUse() {
        return _use;
    }

    public void setUse(SOAPUse u) {
        _use = u;
    }

    public boolean isEncoded() {
        return _use == SOAPUse.ENCODED;
    }

    public boolean isLiteral() {
        return _use == SOAPUse.LITERAL;
    }

    public String getEncodingStyle() {
        return _encodingStyle;
    }

    public void setEncodingStyle(String s) {
        _encodingStyle = s;
    }

    public void validateThis() {
        if(_use == SOAPUse.ENCODED) {
            throw new ValidationException("validation.unsupportedUse.encoded", getLocator().getLineNumber(),getLocator().getSystemId());
        }
    }

    private String _name;
    private String _encodingStyle;
    private String _namespace;
    private SOAPUse _use = SOAPUse.LITERAL;
}
