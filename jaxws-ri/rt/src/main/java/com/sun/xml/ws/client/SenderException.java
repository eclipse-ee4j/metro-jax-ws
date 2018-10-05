/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

import com.sun.istack.localization.Localizable;
import com.sun.xml.ws.util.exception.JAXWSExceptionBase;


/**
 * @author WS Development Team
 */
public class SenderException extends JAXWSExceptionBase {
    public SenderException(String key, Object... args) {
        super(key, args);
    }

    public SenderException(Throwable throwable) {
        super(throwable);
    }

    public SenderException(Localizable arg) {
        super("sender.nestedError", arg);
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.sender";
    }
}
