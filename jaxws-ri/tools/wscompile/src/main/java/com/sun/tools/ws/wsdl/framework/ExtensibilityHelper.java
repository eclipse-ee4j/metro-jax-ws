/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
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
import java.util.Objects;

/**
 * A helper class for extensible entities.
 *
 * @author WS Development Team
 */
@SuppressWarnings({"deprecation"})
public class ExtensibilityHelper {

    public ExtensibilityHelper() {
    }

    public void addExtension(TWSDLExtension e) {
        if (_extensions == null) {
            _extensions = new ArrayList<>();
        }
        _extensions.add(e);
    }

    public Iterable<TWSDLExtension> extensions() {
        return Objects.requireNonNullElseGet(_extensions, ArrayList::new);
    }

    public void withAllSubEntitiesDo(EntityAction action) {
        if (_extensions != null) {
            for (TWSDLExtension extension : _extensions) {
                action.perform((Entity) extension);
            }
        }
    }

    public void accept(ExtensionVisitor visitor) throws Exception {
        if (_extensions != null) {
            for (TWSDLExtension extension : _extensions) {
                ((ExtensionImpl) extension).accept(visitor);
            }
        }
    }

    private List<TWSDLExtension> _extensions;
}
