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

import org.xml.sax.Locator;

import javax.xml.namespace.QName;

public class SOAP12Binding extends SOAPBinding{
    public SOAP12Binding(Locator locator) {
        super(locator);
    }

    @Override public QName getElementName() {
        return SOAP12Constants.QNAME_BINDING;
    }
}
