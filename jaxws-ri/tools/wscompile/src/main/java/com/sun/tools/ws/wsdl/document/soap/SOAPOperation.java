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
import org.xml.sax.Locator;

import javax.xml.namespace.QName;

/**
 * A SOAP operation extension.
 *
 * @author WS Development Team
 */
public class SOAPOperation extends ExtensionImpl {

    public SOAPOperation(Locator locator) {
        super(locator);

    }

    public QName getElementName() {
        return SOAPConstants.QNAME_OPERATION;
    }

    public String getSOAPAction() {
        return _soapAction;
    }

    public void setSOAPAction(String s) {
        _soapAction = s;
    }

    public SOAPStyle getStyle() {
        return _style;
    }

    public void setStyle(SOAPStyle s) {
        _style = s;
    }

    public boolean isDocument() {
        return _style == SOAPStyle.DOCUMENT;
    }

    public boolean isRPC() {
        return _style == SOAPStyle.RPC;
    }

    public void validateThis() {
    }

    private String _soapAction;
    private SOAPStyle _style;
}
