/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.framework;

import com.sun.tools.ws.api.wsdl.TWSDLExtensible;
import com.sun.tools.ws.api.wsdl.TWSDLExtension;
import org.xml.sax.Locator;

/**
 * An entity extending another entity.
 *
 * @author WS Development Team
 */
public abstract class ExtensionImpl extends Entity implements TWSDLExtension {

    public ExtensionImpl(Locator locator) {
        super(locator);
    }

    @Override
    public TWSDLExtensible getParent() {
        return _parent;
    }

    public void setParent(TWSDLExtensible parent) {
        _parent = parent;
    }

    public void accept(ExtensionVisitor visitor) throws Exception {
        visitor.preVisit(this);
        visitor.postVisit(this);
    }

    private TWSDLExtensible _parent;
}
