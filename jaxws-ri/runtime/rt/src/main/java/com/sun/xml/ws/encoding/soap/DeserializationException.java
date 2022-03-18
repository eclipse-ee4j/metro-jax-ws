/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
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
 * DeserializationException represents an exception that occurred while
 * deserializing a Java value from XML.
 * 
 * @see JAXWSExceptionBase
 * 
 * @author WS Development Team
 */
public class DeserializationException extends JAXWSExceptionBase {

    private static final long serialVersionUID = 4324335381330106886L;

    public DeserializationException(String key, Object... args) {
        super(key, args);
    }

    public DeserializationException(Throwable throwable) {
        super(throwable);
    }

    public DeserializationException(Localizable arg) {
        super("nestedDeserializationError", arg);
    }

    @Override
    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.encoding";
    }
}
