/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
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
 * A SOAP binding extension.
 *
 * @author WS Development Team
 */
public class SOAPBinding extends ExtensionImpl {

    public SOAPBinding(Locator locator) {
        super(locator);
        _style = SOAPStyle.DOCUMENT;
    }

    @Override
    public QName getElementName() {
        return SOAPConstants.QNAME_BINDING;
    }

    public String getTransport() {
        return _transport;
    }

    public void setTransport(String s) {
        _transport = s;
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

    @Override
    public void validateThis() {
    }

    private String _transport;
    private SOAPStyle _style;
}
