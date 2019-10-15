/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.streaming;

import com.sun.istack.localization.Localizable;
import com.sun.xml.ws.util.exception.JAXWSExceptionBase;

/**
 * <p> XMLReaderException represents an exception that occurred while reading an
 * XML document. </p>
 * 
 * @see JAXWSExceptionBase
 * 
 * @author WS Development Team
 */
public class XMLReaderException extends JAXWSExceptionBase {

    public XMLReaderException(String key, Object... args) {
        super(key, args);
    }

    public XMLReaderException(Throwable throwable) {
        super(throwable);
    }

    public XMLReaderException(Localizable arg) {
        super("xmlreader.nestedError", arg);
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.streaming";
    }
}
