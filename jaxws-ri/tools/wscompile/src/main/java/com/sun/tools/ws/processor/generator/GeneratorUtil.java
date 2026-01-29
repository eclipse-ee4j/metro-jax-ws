/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.generator;

import com.sun.tools.ws.wscompile.Options;


/**
 *
 * @author WS Development Team
 */
public final class GeneratorUtil {

    private GeneratorUtil() {}

    public static boolean classExists(
        Options options,
        String className) {
        try {
            // Takes care of inner classes.
            getLoadableClassName(className, options.getClassLoader());
            return true;
        } catch(ClassNotFoundException ce) {
            return false;
        }
    }

    private static String getLoadableClassName(
        String className,
        ClassLoader classLoader)
        throws ClassNotFoundException {

        try {
            Class.forName(className, true, classLoader);
        } catch (ClassNotFoundException e) {
            int idx = className.lastIndexOf(GeneratorConstants.DOTC.getValue());
            if (idx > -1) {
                String tmp = className.substring(0, idx) + GeneratorConstants.SIG_INNERCLASS.getValue();
                tmp += className.substring(idx + 1);
                return getLoadableClassName(tmp, classLoader);
            }
            throw e;
        }
        return className;
    }
}
