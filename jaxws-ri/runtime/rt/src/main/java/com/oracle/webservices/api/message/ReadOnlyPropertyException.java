/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.api.message;

/**
 * Used to indicate that {@link PropertySet#put(String, Object)} failed
 * because a property is read-only.
 *
 * @author Kohsuke Kawaguchi
 */
public class ReadOnlyPropertyException extends IllegalArgumentException {

    private static final long serialVersionUID = -3328766024488469509L;

    private final String propertyName;

    public ReadOnlyPropertyException(String propertyName) {
        super(propertyName+" is a read-only property.");
        this.propertyName = propertyName;
    }

    /**
     * Gets the name of the property that was read-only.
     */
    public String getPropertyName() {
        return propertyName;
    }
}
