/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.pipe;

public class ThreadLocalHelper {
    private static final ThreadLocal<Integer> integerHolder = new ThreadLocal<Integer>();
    
    public static void set(Integer i) {
        integerHolder.set(i);
    }

    public static void unset() {
        integerHolder.remove();
    }

    public static Integer get() {
        return integerHolder.get();
    }
}
