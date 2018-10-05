/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.framework;

import org.xml.sax.Locator;
import com.sun.tools.ws.wscompile.ErrorReceiver;

/**
 * An entity that can be defined in a target namespace.
 *
 * @author WS Development Team
 */
public abstract class GlobalEntity extends Entity implements GloballyKnown {

    public GlobalEntity(Defining defining, Locator locator, ErrorReceiver errorReceiver) {
        super(locator);
        _defining = defining;
        this.errorReceiver = errorReceiver;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public abstract Kind getKind();

    public Defining getDefining() {
        return _defining;
    }

    private Defining _defining;
    private String _name;
}
