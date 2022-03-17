/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
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
public class ClientTransportException extends JAXWSExceptionBase {
    public ClientTransportException(Localizable msg) {
        super(msg);
    }

    public ClientTransportException(Localizable msg, Throwable cause) {
        super(msg, cause);
    }

    public ClientTransportException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.client";
    }
}

