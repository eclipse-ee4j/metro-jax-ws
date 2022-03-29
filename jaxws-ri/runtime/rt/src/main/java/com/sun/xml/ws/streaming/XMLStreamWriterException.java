/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
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
 * <p> XMLWriterException represents an exception that occurred while writing
 * an XML document. </p>
 * 
 * @see JAXWSExceptionBase
 * 
 * @author WS Development Team
 */
public class XMLStreamWriterException extends JAXWSExceptionBase {

    private static final long serialVersionUID = 4568488681038250654L;

    @SuppressWarnings({"deprecation"})
    public XMLStreamWriterException(String key, Object... args) {
        super(key, args);
    }

    public XMLStreamWriterException(Throwable throwable) {
        super(throwable);
    }

    @SuppressWarnings({"deprecation"})
    public XMLStreamWriterException(Localizable arg) {
        super("xmlwriter.nestedError", arg);
    }

    @Override
    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.streaming";
    }
}
