/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import com.sun.istack.localization.Localizable;
import com.sun.xml.ws.util.exception.JAXWSExceptionBase;

/**
 * UtilException represents an exception that occurred while
 * one of the util classes is operating.
 * 
 * @see JAXWSExceptionBase
 * 
 * @author JAX-WS Development Team
 */
public class UtilException extends JAXWSExceptionBase {
    public UtilException(String key, Object... args) {
        super(key, args);
    }

    public UtilException(Throwable throwable) {
        super(throwable);
    }

    public UtilException(Localizable arg) {
        super("nestedUtilError", arg);
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.util";
    }

}
