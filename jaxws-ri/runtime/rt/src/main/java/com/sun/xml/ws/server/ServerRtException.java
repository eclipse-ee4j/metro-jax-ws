/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import com.sun.istack.localization.Localizable;
import com.sun.xml.ws.util.exception.JAXWSExceptionBase;

/**
 */
public class ServerRtException extends JAXWSExceptionBase {

    private static final long serialVersionUID = 6733047469014038310L;

    @SuppressWarnings({"deprecation"})
    public ServerRtException(String key, Object... args) {
        super(key, args);
    }

    public ServerRtException(Throwable throwable) {
        super(throwable);
    }

    @SuppressWarnings({"deprecation"})
    public ServerRtException(Localizable arg) {
        super("server.rt.err", arg);
    }

    public ServerRtException(Localizable arg, Throwable t) {
        super(arg, t);
    }

    @Override
    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.server";
    }

}
