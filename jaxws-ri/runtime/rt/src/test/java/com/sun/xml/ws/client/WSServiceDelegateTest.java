/*
 * Copyright (c) 2019, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.sun.xml.ws.client;

import java.lang.reflect.Method;

import junit.framework.TestCase;

public class WSServiceDelegateTest extends TestCase {
    public void testGetDelegatingLoader() throws Exception {
        MyClassLoader loader2 = new MyClassLoader();
        MyClassLoader loader1 = new MyClassLoader(loader2);
        ClassLoader result = invokeGetDelegatingLoader(loader1, loader2);
        assertEquals(loader1, result);

        result = invokeGetDelegatingLoader(loader1, loader1);
        assertEquals(loader1, result);

        loader2 = new MyClassLoader();
        loader1 = new MyClassLoader();
        result = invokeGetDelegatingLoader(loader1, loader2);
        assertEquals(loader2, result.getParent());
        
        result = invokeGetDelegatingLoader(loader1, null);
        assertEquals(loader1, result);
        
        result = invokeGetDelegatingLoader(null, loader2);
        assertEquals(loader2, result);
    }
    
    private ClassLoader invokeGetDelegatingLoader(ClassLoader loader1, ClassLoader loader2) throws Exception {
        Method m = WSServiceDelegate.class.getDeclaredMethod("getDelegatingLoader", ClassLoader.class, ClassLoader.class);
        m.setAccessible(true);
        return (ClassLoader)m.invoke(WSServiceDelegate.class, loader1, loader2);
    }
private static class MyClassLoader extends ClassLoader {
    MyClassLoader() {}
    MyClassLoader(ClassLoader parent) {super(parent);}
}
}


