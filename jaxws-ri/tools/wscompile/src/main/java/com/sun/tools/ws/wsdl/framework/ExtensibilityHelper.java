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

import com.sun.tools.ws.api.wsdl.TWSDLExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A helper class for extensible entities.
 *
 * @author WS Development Team
 */
public class ExtensibilityHelper {

    public ExtensibilityHelper() {
    }

    @SuppressWarnings({"deprecation"})
    public void addExtension(TWSDLExtension e) {
        if (_extensions == null) {
            _extensions = new ArrayList();
        }
        _extensions.add(e);
    }

    @SuppressWarnings({"deprecation"})
    public Iterable<TWSDLExtension> extensions() {
        if (_extensions == null) {
            return new ArrayList<>();
        } else {
            return _extensions;
        }
    }

    public void withAllSubEntitiesDo(EntityAction action) {
        if (_extensions != null) {
            for (Iterator iter = _extensions.iterator(); iter.hasNext();) {
                action.perform((Entity) iter.next());
            }
        }
    }

    public void accept(ExtensionVisitor visitor) throws Exception {
        if (_extensions != null) {
            for (Iterator iter = _extensions.iterator(); iter.hasNext();) {
                ((ExtensionImpl) iter.next()).accept(visitor);
            }
        }
    }

    @SuppressWarnings({"deprecation"})
    private List<TWSDLExtension> _extensions;
}
