/*
 * Copyright (c) 2017, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Utility class used as a JEP 238 multi release jar versioned class.
 *
 * Version for {@code runtime >= 9}.
 */
public class MrJarUtil {

    /**
     * Get property used for disabling instance pooling of xml readers / writers.
     *
     * @param baseName Name of a {@linkplain com.sun.xml.ws.api.streaming.XMLStreamReaderFactory} class or
     *                 {@linkplain com.sun.xml.ws.api.streaming.XMLStreamWriterFactory} class.
     *
     * @return true if *.noPool system property is not set or is set to true.
     */
    public static boolean getNoPoolProperty(String baseName) {
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                String noPool = System.getProperty(baseName + ".noPool");
                return noPool == null || Boolean.parseBoolean(noPool);
            }
        });
    }

    static InputStream getResourceAsStream(Class clazz, String resource) {
        Package pkg = clazz.getPackage();
        String fullpath = addPackagePath(resource, pkg);
        InputStream is;

        is = moduleResource(clazz, resource);
        if (is != null) return is;

        is = moduleResource(clazz, fullpath);
        if (is != null) return is;

        URL url = cpResource(clazz, resource);
        if (url == null) url = cpResource(clazz, fullpath);

        if (url == null) {
            throw new UtilException("util.failed.to.find.handlerchain.file",
                    clazz.getName(), resource);
        }
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new UtilException("util.failed.to.parse.handlerchain.file",
                    clazz.getName(), resource);
        }
    }

    private static URL cpResource(Class clazz, String name) {
        URL url = clazz.getResource(name);
        if (url == null) {
            ClassLoader tccl = Thread.currentThread().getContextClassLoader();
            url = tccl.getResource(name);
        }
        return url;
    }

    private static InputStream moduleResource(Class resolvingClass, String name) {
        Module module = resolvingClass.getModule();
        try {
            InputStream stream = module.getResourceAsStream(name);
            if (stream != null) {
                return stream;
            }
        } catch(IOException e) {
            throw new UtilException("util.failed.to.find.handlerchain.file",
                    resolvingClass.getName(), name);
        }
        return null;
    }

    private static String addPackagePath(String file, Package pkg) {
        String tmp = pkg.getName();
        tmp = tmp.replace('.', '/');
        tmp += "/" + file;
        return tmp;
    }
}
