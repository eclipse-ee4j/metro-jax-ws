/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.protocol.xml;

import com.sun.istack.localization.Localizable;
import com.sun.xml.ws.util.exception.JAXWSExceptionBase;

/**
 * @author WS Development Team
 */
public class XMLMessageException extends JAXWSExceptionBase {

    private static final long serialVersionUID = 918497924897686976L;

    @SuppressWarnings({"deprecation"})
    public XMLMessageException(String key, Object... args) {
        super(key, args);
    }

    public XMLMessageException(Throwable throwable) {
        super(throwable);
    }

    @SuppressWarnings({"deprecation"})
    public XMLMessageException(Localizable arg) {
        super("server.rt.err", arg);
    }

    @Override
    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.xmlmessage";
    }

}
