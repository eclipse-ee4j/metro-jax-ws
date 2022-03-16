/*
 * Copyright (c) 2008, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model;

import jakarta.xml.ws.WebServiceException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A {@link ClassLoader} used to "inject" wrapper and exception bean classes
 * into the VM.
 *
 * @author Jitendra kotamraju
 */
final class Injector {

    private static final Logger LOGGER = Logger.getLogger(Injector.class.getName());

    private static Method defineClass;

    static {
        try {
            defineClass = AccessController.doPrivileged(
                    new PrivilegedAction<>() {
                        @Override
                        public Method run() {
                            return InjectorHelper.getMethod(ClassLoader.class, "defineClass",
                                    String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
                        }
                    });

        } catch (Throwable t) {
            Logger.getLogger(Injector.class.getName()).log(Level.SEVERE, null, t);
            WebServiceException we = new WebServiceException(t);
            throw we;
        }
    }

    static synchronized Class inject(ClassLoader cl, String className, byte[] image) {
        // To avoid race conditions let us check if the classloader
        // already contains the class
        try {
            return cl.loadClass(className);
        } catch (ClassNotFoundException e) {
            // nothing to do
        }
        try {
                return (Class) defineClass.invoke(cl,
                        className.replace('/', '.'), image, 0, image.length, Injector.class.getProtectionDomain());
        } catch (ReflectiveOperationException e) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "Unable to inject " + className, e);
            }
            throw new WebServiceException(e);
        }
    }

}
