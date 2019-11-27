/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.server;

import com.sun.istack.Nullable;
import com.sun.istack.localization.Localizable;
import com.sun.xml.ws.api.server.InstanceResolver;
import com.sun.xml.ws.api.server.ResourceInjector;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.resources.ServerMessages;
import com.sun.xml.ws.server.ServerRtException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Partial implementation of {@link InstanceResolver} with
 * convenience methods to do the resource injection.
 *
 * @author Kohsuke Kawaguchi
 * @since 2.2.6
 */
public abstract class AbstractInstanceResolver<T> extends InstanceResolver<T> {

    protected static ResourceInjector getResourceInjector(WSEndpoint endpoint) {
        ResourceInjector ri = endpoint.getContainer().getSPI(ResourceInjector.class);
        if(ri==null)
            ri = ResourceInjector.STANDALONE;
        return ri;
    }

    /**
     * Helper for invoking a method with elevated privilege.
     */
    protected static void invokeMethod(final @Nullable Method method, final Object instance, final Object... args) {
        if(method==null)    return;
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    MethodUtil.invoke(instance,method, args);
                } catch (IllegalAccessException e) {
                    throw new ServerRtException("server.rt.err",e);
                } catch (InvocationTargetException e) {
                    throw new ServerRtException("server.rt.err",e);
                }
                return null;
            }
        });
    }

    /**
     * Finds the method that has the given annotation, while making sure that
     * there's only at most one such method.
     */
    protected final @Nullable Method findAnnotatedMethod(Class clazz, Class<? extends Annotation> annType) {
        boolean once = false;
        Method r = null;
        for(Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(annType) != null) {
                if (once)
                    throw new ServerRtException(ServerMessages.ANNOTATION_ONLY_ONCE(annType));
                if (method.getParameterTypes().length != 0)
                    throw new ServerRtException(ServerMessages.NOT_ZERO_PARAMETERS(method));
                r = method;
                once = true;
            }
        }
        return r;
    }
}
