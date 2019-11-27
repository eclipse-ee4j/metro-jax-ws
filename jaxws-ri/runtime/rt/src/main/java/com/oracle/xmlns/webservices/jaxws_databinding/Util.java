/*
 * Copyright (c) 2012, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.xmlns.webservices.jaxws_databinding;

import com.sun.xml.ws.model.RuntimeModelerException;

/**
 * Simple util to handle default values.
 *
 * @author miroslav.kos@oracle.com
 */
class Util {

    static String nullSafe(String value) {
        return value == null ? "" : value;
    }

    static <T> T nullSafe(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    @SuppressWarnings("unchecked")
    static <T extends Enum> T nullSafe(Enum value, T defaultValue) {
        return value == null ? defaultValue : (T) T.valueOf(defaultValue.getClass(), value.toString());
    }

    public static Class<?> findClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeModelerException("runtime.modeler.external.metadata.generic", e);
        }
    }
}
