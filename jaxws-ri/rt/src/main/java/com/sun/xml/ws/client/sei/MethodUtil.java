/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.sei;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to invoke com.sun.xml.ws.util.MethodUtil.invoke() if available. If not (other then Oracle JDK) fallbacks
 * to java.lang,reflect.Method.invoke()
 * <p/>
 * Be careful, copy of this class exists in several packages, iny modification must be done to other copies too!
 */
class MethodUtil {

    private static final Logger LOGGER = Logger.getLogger(MethodUtil.class.getName());

    static Object invoke(Object target, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        // com.sun.xml.ws.util.MethodUtil.invoke(method, owner, args)
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Invoking method using com.sun.xml.ws.util.MethodUtil");
        }
        try {
            return com.sun.xml.ws.util.MethodUtil.invoke(method, target, args);
        } catch (InvocationTargetException ite) {
            // unwrap invocation exception added by reflection code ...
            throw unwrapException(ite);
        }
    }

    private static InvocationTargetException unwrapException(InvocationTargetException ite) {
        Throwable targetException = ite.getTargetException();
        if (targetException != null && targetException instanceof InvocationTargetException) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "Unwrapping invocation target exception");
            }
            return (InvocationTargetException) targetException;
        } else {
            return ite;
        }
    }

}
