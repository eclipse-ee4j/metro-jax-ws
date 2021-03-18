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
 * Version for {@code runtime < 9}.
 */
public class MrJarUtil {

    /**
     * Get property used for enabling instance pooling of xml readers / writers.
     *
     * @param baseName Name of a {@linkplain com.sun.xml.ws.api.streaming.XMLStreamReaderFactory} class or
     *                 {@linkplain com.sun.xml.ws.api.streaming.XMLStreamWriterFactory} class.
     *
     * @return true if *.noPool system property is set to true.
     */
    public static boolean getNoPoolProperty(final String baseName) {
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                return Boolean.getBoolean(baseName + ".noPool");
            }
        });
    }

    static InputStream getResourceAsStream(Class clazz, String resource) {
        URL url = clazz.getResource(resource);
        if (url == null) {
            url = Thread.currentThread().getContextClassLoader().
                    getResource(resource);
        }
        if (url == null) {
            String tmp = clazz.getPackage().getName();
            tmp = tmp.replace('.', '/');
            tmp += "/" + resource;
            url =
                    Thread.currentThread().getContextClassLoader().getResource(tmp);
        }
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
}
