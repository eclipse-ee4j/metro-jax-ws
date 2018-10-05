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

/**
 * An exception signalling that an entity with the given name/id has already been defined.
 *
 * @author WS Development Team
 */
public class DuplicateEntityException extends ValidationException {

    public DuplicateEntityException(GloballyKnown entity) {
        super(
            "entity.duplicateWithType",
                entity.getElementName().getLocalPart(),
                entity.getName());
    }

    public DuplicateEntityException(Identifiable entity) {
        super(
            "entity.duplicateWithType",
                entity.getElementName().getLocalPart(),
                entity.getID());
    }

    public DuplicateEntityException(Entity entity, String name) {
        super(
            "entity.duplicateWithType",
                entity.getElementName().getLocalPart(), name);
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.tools.ws.resources.wsdl";
    }
}
