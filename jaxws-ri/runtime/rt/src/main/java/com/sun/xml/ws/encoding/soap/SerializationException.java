/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding.soap;

import com.sun.istack.localization.Localizable;
import com.sun.xml.ws.util.exception.JAXWSExceptionBase;

/**
 * SerializationException represents an exception that occurred while
 * serializing a Java value as XML.
 * 
 * @see JAXWSExceptionBase
 * 
 * @author WS Development Team
 */
public class SerializationException extends JAXWSExceptionBase {

    public SerializationException(String key, Object... args) {
        super(key, args);
    }

    public SerializationException(Localizable arg) {
        super("nestedSerializationError", arg);
    }

    public SerializationException(Throwable throwable) {
        super(throwable);
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.encoding";
    }

}
