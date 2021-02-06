/*
 * Copyright (c) 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.api.message;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author lukas
 */
final class AccessorFactory {

    static MethodHandles.Lookup createPrivateLookup(Class c, MethodHandles.Lookup lookup) {
        //no private lookup on JDK 8
        return null;
    }

    private AccessorFactory() {
    }

    static BasePropertySet.Accessor createAccessor(Field f, String name, MethodHandles.Lookup lookup) throws IllegalAccessException {
        return new BasePropertySet.FieldAccessor(f, name);
    }

    static BasePropertySet.Accessor createAccessor(Method getter, Method setter, String value, MethodHandles.Lookup lookup) throws IllegalAccessException {
        return new BasePropertySet.MethodAccessor(getter, setter, value);
    }

}
