/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.util;

/**
 * @author WS Development Team
 */

public final class ClassNameInfo {

    public static String getName(String className) {
        String qual = getQualifier(className);
        int len = className.length();
        int closingBracket = className.indexOf('>');
        if(closingBracket > 0)
            len = closingBracket;
        return qual != null
            ? className.substring(qual.length() + 1, len)
            : className;
    }


    /**
     *
     *
     * @param className Generic class, such as java.util.List&lt;java.lang.String&gt;
     * @return the generic class, such as java.util.List
     */
    public static String getGenericClass(String className) {
       int index = className.indexOf('<');
       if(index < 0)
           return className;
       return (index > 0)?className.substring(0, index):className;
    }


    public static String getQualifier(String className) {
        int idot = className.indexOf(' ');
        if (idot <= 0)
            idot = className.length();
        else
            idot -= 1; // back up over previous dot
        int index = className.lastIndexOf('.', idot - 1);
        return (index < 0) ? null : className.substring(0, index);
    }

    public static String replaceInnerClassSym(String name) {
        return name.replace('$', '_');
    }
}
