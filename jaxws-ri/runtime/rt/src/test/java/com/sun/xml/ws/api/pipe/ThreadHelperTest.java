/*
 * Copyright (c) 2024 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.pipe;

import com.sun.xml.ws.api.pipe.ThreadHelper.JDK9ThreadFactory;
import junit.framework.TestCase;

import java.lang.reflect.Constructor;

public class ThreadHelperTest extends TestCase {

    private static class TestClassLoader extends ClassLoader {
    }

    public void testJDK9ThreadFactoryShouldCreateThreadsThatInheritParentClassLoader() {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();

        try {
            Constructor<Thread> ctr = Thread.class.getConstructor(ThreadGroup.class, Runnable.class, String.class, long.class, boolean.class);
            JDK9ThreadFactory threadFactory = new JDK9ThreadFactory(ctr);

            ClassLoader testClassLoader = new TestClassLoader();
            Thread.currentThread().setContextClassLoader(testClassLoader);

            Thread newThread = threadFactory.newThread(() -> {});

            assertEquals(testClassLoader, newThread.getContextClassLoader());
        } catch (NoSuchMethodException e) {
            // ignore exception, this constructor is not present before Java 9
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }
}
