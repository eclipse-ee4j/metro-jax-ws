/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model.wsdl;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.model.wsdl.WSDLObject;
import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;

import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;

/**
 * @author Kohsuke Kawaguchi
 */
abstract class AbstractObjectImpl implements WSDLObject {
    // source location information
    private final int lineNumber;
    private final String systemId;


    /*package*/ AbstractObjectImpl(XMLStreamReader xsr) {
        Location loc = xsr.getLocation();
        this.lineNumber = loc.getLineNumber();
        this.systemId = loc.getSystemId();
    }

    /*package*/ AbstractObjectImpl(String systemId, int lineNumber) {
        this.systemId = systemId;
        this.lineNumber = lineNumber;
    }

    @Override
    public final @NotNull Locator getLocation() {
        LocatorImpl loc = new LocatorImpl();
        loc.setSystemId(systemId);
        loc.setLineNumber(lineNumber);
        return loc;
    }
}
