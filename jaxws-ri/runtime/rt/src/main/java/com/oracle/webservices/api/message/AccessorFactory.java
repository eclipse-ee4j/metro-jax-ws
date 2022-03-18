/*
 * Copyright (c) 2021, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.api.message;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author lukas
 */
final class AccessorFactory {

    private AccessorFactory() {
    }

    static MethodHandles.Lookup createPrivateLookup(Class<?> clazz, MethodHandles.Lookup lookup) throws IllegalAccessException {
        return (AccessorFactory.class.getModule() == clazz.getModule())
                ? MethodHandles.privateLookupIn(clazz, MethodHandles.lookup())
                : MethodHandles.privateLookupIn(clazz, lookup);
    }

    static BasePropertySet.Accessor createAccessor(Field f, String name, MethodHandles.Lookup lookup) throws IllegalAccessException {
        return new VarHandleAccessor(
                lookup.unreflectVarHandle(f),
                name);
    }

    static BasePropertySet.Accessor createAccessor(Method getter, Method setter, String value, MethodHandles.Lookup lookup) throws IllegalAccessException {
        return new MethodHandleAccessor(
                lookup.unreflect(getter),
                setter != null ? lookup.unreflect(setter) : null,
                value);
    }

    private static final class VarHandleAccessor implements BasePropertySet.Accessor {

        /**
         * Field with the annotation.
         */
        private final VarHandle vh;

        /**
         * One of the values in {@link PropertySet.Property} annotation on {@link #vh}.
         */
        private final String name;

        private VarHandleAccessor(VarHandle vh, String name) {
            this.vh = vh;
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean hasValue(PropertySet props) {
            return get(props) != null;
        }

        @Override
        public Object get(PropertySet props) {
            return vh.get(props);
        }

        @Override
        public void set(PropertySet props, Object value) {
            vh.set(props, value);
        }
    }

    static final class MethodHandleAccessor implements BasePropertySet.Accessor {

        /**
         * Getter method.
         */
        private final @NotNull MethodHandle getter;
        /**
         * Setter method.
         * Some property is read-only.
         */
        private final @Nullable MethodHandle setter;

        /**
         * One of the values in {@link PropertySet.Property} annotation on {@link #getter}.
         */
        private final String name;

        private MethodHandleAccessor(MethodHandle getter, MethodHandle setter, String value) {
            this.getter = getter;
            this.setter = setter;
            this.name = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean hasValue(PropertySet props) {
            return get(props)!=null;
        }

        @Override
        public Object get(PropertySet props) {
            try {
                return getter.invoke(props);
            } catch (Throwable ex) {
                handle(ex);
                return 0;   // never reach here
            }
        }

        @Override
        public void set(PropertySet props, Object value) {
            if(setter==null) {
                throw new ReadOnlyPropertyException(getName());
            }
            try {
                setter.invoke(props, value);
            } catch (Throwable e) {
                handle(e);
            }
        }

        /**
         * Since we don't expect the getter/setter to throw a checked exception,
         * it should be possible to make the exception propagation transparent.
         * That's what we are trying to do here.
         */
        private Exception handle(Throwable t) {
            if (t instanceof Error) {
                throw (Error)t;
            }
            if (t instanceof RuntimeException) {
                throw (RuntimeException)t;
            }
            throw new Error(t);
        }
    }
}
