/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws;

import com.sun.xml.ws.util.Version;

/**
 * Obtains the version number of the JAX-WS tools.
 * @author Kohsuke Kawaguchi
 */
public abstract class ToolVersion {
    private ToolVersion() {}    // no instanciation please

    public static final Version VERSION = Version.create(ToolVersion.class.getResourceAsStream("version.properties"));
}
