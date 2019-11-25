/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.servlet;

import com.sun.xml.ws.util.exception.JAXWSExceptionBase;

/**
 *
 * @author WS Development Team
 */
final class WSServletException extends JAXWSExceptionBase {
    public WSServletException(String key, Object... args) {
        super(key, args);
    }

    public WSServletException(Throwable throwable) {
        super(throwable);
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.wsservlet";
    }
}
