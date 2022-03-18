/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.util;

import com.sun.xml.ws.util.exception.JAXWSExceptionBase;

/**
  * @author WS Development Team
  */
public class WSDLParseException extends JAXWSExceptionBase {

    private static final long serialVersionUID = -221204250739166481L;

    public WSDLParseException(String key, Object... args) {
        super(key, args);
    }

    public WSDLParseException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getDefaultResourceBundleName() {
        return "com.sun.tools.ws.resources.util";
    }
}
