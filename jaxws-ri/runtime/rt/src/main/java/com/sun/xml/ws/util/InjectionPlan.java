/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import jakarta.annotation.Resource;
import jakarta.xml.ws.WebServiceException;

/**
 * Encapsulates which field/method the injection is done, and performs the
 * injection.
 */
public abstract class InjectionPlan<T, R> {
    /**
     * Perform injection
     * 
     * @param instance
     *            Instance
     * @param resource
     *            Resource
     */
    public abstract void inject(T instance, R resource);

    /**
     * Perform injection, but resource is only generated if injection is
     * necessary.
     *
     */
    public void inject(T instance, Callable<R> resource) {
        try {
            inject(instance, resource.call());
        } catch(Exception e) {
            throw new WebServiceException(e);
        }
    }

    /*
     * Injects to a field.
     */
    public static class FieldInjectionPlan<T, R> extends
            InjectionPlan<T, R> {
        private final Field field;

        public FieldInjectionPlan(Field field) {
            this.field = field;
        }

        @Override
        public void inject(final T instance, final R resource) {
            AccessController.doPrivileged(new PrivilegedAction<>() {
                @Override
                public Object run() {
                    try {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        field.set(instance, resource);
                        return null;
                    } catch (IllegalAccessException e) {
                        throw new WebServiceException(e);
                    }
                }
            });
        }
    }

    /*
     * Injects to a method.
     */
    public static class MethodInjectionPlan<T, R> extends
            InjectionPlan<T, R> {
        private final Method method;

        public MethodInjectionPlan(Method method) {
            this.method = method;
        }

        @Override
        public void inject(T instance, R resource) {
            invokeMethod(method, instance, resource);
        }
    }

    /*
     * Helper for invoking a method with elevated privilege.
     */
    private static void invokeMethod(final Method method, final Object instance, final Object... args) {
        if(method==null)    return;
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                try {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    method.invoke(instance,args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new WebServiceException(e);
                }
                return null;
            }
        });
    }

    /*
     * Combines multiple {@link InjectionPlan}s into one.
     */
    private static class Compositor<T, R> extends InjectionPlan<T, R> {
        private final Collection<InjectionPlan<T, R>> children;

        public Compositor(Collection<InjectionPlan<T, R>> children) {
            this.children = children;
        }

        @Override
        public void inject(T instance, R res) {
            for (InjectionPlan<T, R> plan : children)
                plan.inject(instance, res);
        }
        
        @Override
        public void inject(T instance, Callable<R> resource) {
            if (!children.isEmpty()) {
                super.inject(instance, resource);
            }
        }
    }

    /*
     * Creates an {@link InjectionPlan} that injects the given resource type to the given class.
     *
     * @param isStatic
     *      Only look for static field/method
     *
     */
    public static <T,R> InjectionPlan<T,R> buildInjectionPlan(Class<? extends T> clazz, Class<R> resourceType, boolean isStatic) {
        List<InjectionPlan<T,R>> plan = new ArrayList<>();

        Class<?> cl = clazz;
        while(cl != Object.class) {
            for(Field field: cl.getDeclaredFields()) {
                Resource resource = field.getAnnotation(Resource.class);
                if (resource != null) {
                    if(isInjectionPoint(resource, field.getType(),
                        "Incorrect type for field"+field.getName(),
                        resourceType)) {

                        if(isStatic && !Modifier.isStatic(field.getModifiers()))
                            throw new WebServiceException("Static resource "+resourceType+" cannot be injected to non-static "+field);

                        plan.add(new FieldInjectionPlan<>(field));
                    }
                }
            }
            cl = cl.getSuperclass();
        }

        cl = clazz;
        while(cl != Object.class) {
            for(Method method : cl.getDeclaredMethods()) {
                Resource resource = method.getAnnotation(Resource.class);
                if (resource != null) {
                    Class[] paramTypes = method.getParameterTypes();
                    if (paramTypes.length != 1)
                        throw new WebServiceException("Incorrect no of arguments for method "+method);
                    if(isInjectionPoint(resource,paramTypes[0],
                        "Incorrect argument types for method"+method.getName(),
                        resourceType)) {

                        if(isStatic && !Modifier.isStatic(method.getModifiers()))
                            throw new WebServiceException("Static resource "+resourceType+" cannot be injected to non-static "+method);

                        plan.add(new MethodInjectionPlan<>(method));
                    }
                }
            }
            cl = cl.getSuperclass();
        }

        return new Compositor<>(plan);
    }

    /*
     * Returns true if the combination of {@link Resource} and the field/method type
     * are consistent for {@link WebServiceContext} injection.
     */
    private static boolean isInjectionPoint(Resource resource, Class fieldType, String errorMessage, Class resourceType ) {
        Class t = resource.type();
        if (t.equals(Object.class)) {
            return fieldType.equals(resourceType);
        } else if (t.equals(resourceType)) {
            if (fieldType.isAssignableFrom(resourceType)) {
                return true;
            } else {
                // type compatibility error
                throw new WebServiceException(errorMessage);
            }
        }
        return false;
    }
}
