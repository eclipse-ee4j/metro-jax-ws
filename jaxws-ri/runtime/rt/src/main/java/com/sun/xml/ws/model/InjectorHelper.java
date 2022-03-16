/*
 * Copyright (c) 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.sun.xml.ws.model;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class InjectorHelper {

    private static Unsafe UNSAFE;
    private static long OFFSET = -1;

    static {
        UNSAFE = getUnsafe();
        if (UNSAFE != null) {
            Field f = AccessController.doPrivileged(new PrivilegedAction<>() {
                @Override
                public Field run() {
                    try {
                        return AccessibleObject.class.getDeclaredField("override");
                    } catch (NoSuchFieldException | SecurityException ex) {
                        return null;
                    }
                }
            });
            if (f == null) {
                try {
                    f = dummy.class.getDeclaredField("override");
                } catch (NoSuchFieldException ignore) {
                }
            }
            OFFSET = UNSAFE.objectFieldOffset(f);
        }
    }


    private InjectorHelper() {
    }

    static Method getMethod(final Class<?> c, final String methodname, final Class<?>... params) {
        try {
            Method m = c.getDeclaredMethod(methodname, params);
            setAccessible(m);
            return m;
        } catch (NoSuchMethodException e) {
            // impossible
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    static class dummy {

        boolean override;
        Object other;
    }

    private static <T extends AccessibleObject> T setAccessible(T accessor) {
        if (OFFSET != -1) {
            UNSAFE.putBoolean(accessor, OFFSET, true);
        } else {
            accessor.setAccessible(true); // this may fail
        }
        return accessor;
    }

    private static Unsafe getUnsafe() {
        try {
            Field f = AccessController.doPrivileged(new PrivilegedAction<>() {
                @Override
                public Field run() {
                    try {
                        return Unsafe.class.getDeclaredField("theUnsafe");
                    } catch (NoSuchFieldException | SecurityException ex) {
                        Logger.getLogger(InjectorHelper.class.getName()).log(Level.WARNING, null, ex);
                        return null;
                    }
                }
            });
            if (f != null) {
                setAccessible(f);
                return (Unsafe) f.get(null);
            }
        } catch (Throwable t) {
            Logger.getLogger(InjectorHelper.class.getName()).log(Level.WARNING, null, t);
        }
        return null;
    }

}
