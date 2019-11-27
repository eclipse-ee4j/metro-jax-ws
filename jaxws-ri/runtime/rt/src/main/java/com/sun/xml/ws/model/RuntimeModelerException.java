/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model;
import com.sun.istack.localization.Localizable;
import com.sun.xml.ws.util.exception.JAXWSExceptionBase;

/**
 * RuntimeModelerException represents an exception that occurred while
 * serializing a Java value as XML.
 * 
 * @see JAXWSExceptionBase
 * 
 * @author WS Development Team
 */
public class RuntimeModelerException extends JAXWSExceptionBase {

    public RuntimeModelerException(String key, Object... args) {
        super(key, args);
    }

    public RuntimeModelerException(Throwable throwable) {
        super(throwable);
    }

    public RuntimeModelerException(Localizable arg) {
        super("nestedModelerError", arg);
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.modeler";
    }

}
