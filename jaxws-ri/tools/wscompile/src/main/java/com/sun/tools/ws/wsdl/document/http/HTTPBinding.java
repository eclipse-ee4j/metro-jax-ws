/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
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
 * A HTTP binding extension.
 *
 * @author WS Development Team
 */
public class HTTPBinding extends ExtensionImpl {

    public HTTPBinding(Locator locator) {
        super(locator);
    }

    @Override
    public QName getElementName() {
        return HTTPConstants.QNAME_BINDING;
    }

    public String getVerb() {
        return _verb;
    }

    public void setVerb(String s) {
        _verb = s;
    }

    @Override
    public void validateThis() {
        if (_verb == null) {
            failValidation("validation.missingRequiredAttribute", "verb");
        }
    }

    private String _verb;
}
