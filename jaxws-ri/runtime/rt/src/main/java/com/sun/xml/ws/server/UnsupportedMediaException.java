/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import com.sun.xml.ws.util.exception.JAXWSExceptionBase;
import com.sun.xml.ws.resources.ServerMessages;
import com.sun.istack.NotNull;
import com.sun.xml.ws.api.pipe.Codec;

import java.util.List;

/**
 * {@link Codec} throws this exception when it doesn't understand request message's
 * Content-Type
 * @author Jitendra Kotamraju
 */
public final class UnsupportedMediaException extends JAXWSExceptionBase {

    public UnsupportedMediaException( @NotNull String contentType, List<String> expectedContentTypes) {
        super(ServerMessages.localizableUNSUPPORTED_CONTENT_TYPE(contentType, expectedContentTypes));
    }

    public UnsupportedMediaException() {
        super(ServerMessages.localizableNO_CONTENT_TYPE());
    }

    public UnsupportedMediaException(String charset) {
        super(ServerMessages.localizableUNSUPPORTED_CHARSET(charset));
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.server";
    }

}
