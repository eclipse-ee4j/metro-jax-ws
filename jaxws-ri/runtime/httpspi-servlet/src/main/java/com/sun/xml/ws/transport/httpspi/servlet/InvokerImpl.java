/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.httpspi.servlet;

import jakarta.xml.ws.spi.Invoker;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.annotation.PostConstruct;

import com.sun.xml.ws.util.InjectionPlan;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author Jitendra Kotamraju
 */
class InvokerImpl extends Invoker {
    private final Class implType;
    private final Object impl;
    private final Method postConstructMethod;
//    private final Method preDestroyMethod;

    InvokerImpl(Class implType) {
        this.implType = implType;
        postConstructMethod = findAnnotatedMethod(implType, PostConstruct.class);
//        preDestroyMethod = findAnnotatedMethod(implType, PreDestroy.class);
        try {
            impl = implType.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new WebServiceException(e);
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

    @Override
    public void inject(WebServiceContext webServiceContext) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        InjectionPlan.buildInjectionPlan(
            implType, WebServiceContext.class,false).inject(impl,webServiceContext);
        invokeMethod(postConstructMethod, impl);
    }

    @Override
    public Object invoke(Method m, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return m.invoke(impl, args);
    }

    /*
     * Finds the method that has the given annotation, while making sure that
     * there's only at most one such method.
     */
    private static Method findAnnotatedMethod(Class clazz, Class<? extends Annotation> annType) {
        boolean once = false;
        Method r = null;
        for(Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(annType) != null) {
                if (once)
                    throw new WebServiceException("Only one method should have the annotation"+annType);
                if (method.getParameterTypes().length != 0)
                    throw new WebServiceException("Method"+method+"shouldn't have any arguments");
                r = method;
                once = true;
            }
        }
        return r;
    }
}
