/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document.http;

import com.sun.tools.ws.wsdl.framework.ExtensionImpl;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;

/**
 * A HTTP urlReplacement extension.
 *
 * @author WS Development Team
 */
public class HTTPUrlReplacement extends ExtensionImpl {

    public HTTPUrlReplacement(Locator locator) {
        super(locator);
    }

    public QName getElementName() {
        return HTTPConstants.QNAME_URL_REPLACEMENT;
    }

    public void validateThis() {
    }
}
