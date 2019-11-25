/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

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
}
