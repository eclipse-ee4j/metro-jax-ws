/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import java.io.InputStream;
import java.io.IOException;

/**
 * Obtains the version number of the JAX-WS runtime.
 *
 * @author Kohsuke Kawaguchi
 * @author Jitendra Kotamraju
 */
public final class RuntimeVersion {

    public static final Version VERSION;

    static {
        Version version = null;
        InputStream in = RuntimeVersion.class.getResourceAsStream("version.properties");
        try {
            version = Version.create(in);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch(IOException ioe) {
                    // Nothing to do
                }
            }
        }
        VERSION = version == null ? Version.create(null) : version;
    }

    private RuntimeVersion() {}

    /**
     * Get JAX-WS version
     */
    public String getVersion() {
        return VERSION.toString();
    }
    
}
