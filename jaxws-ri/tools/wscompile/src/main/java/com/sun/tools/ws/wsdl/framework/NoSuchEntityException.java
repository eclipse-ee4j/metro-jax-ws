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

import javax.xml.namespace.QName;

/**
 * An exception signalling that an entity with the given name/id does not exist.
 *
 * @author WS Development Team
 */
public class NoSuchEntityException extends ValidationException {

    public NoSuchEntityException(QName name) {
        super(
            "entity.notFoundByQName",
                name.getLocalPart(), name.getNamespaceURI());
    }

    public NoSuchEntityException(String id) {
        super("entity.notFoundByID", id);
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.tools.ws.resources.wsdl";
    }
}
