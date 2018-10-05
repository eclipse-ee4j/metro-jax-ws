/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model;

import com.sun.istack.localization.Localizable;
import com.sun.tools.ws.processor.ProcessorException;

/**
 * ModelException represents an exception that occurred while
 * visiting service model.
 *
 * @see ProcessorException
 *
 * @author WS Development Team
 */
public class ModelException extends ProcessorException {

    public ModelException(String key, Object... args) {
        super(key, args);
    }

    public ModelException(Throwable throwable) {
        super(throwable);
    }

    public ModelException(Localizable arg) {
        super("model.nestedModelError", arg);
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.tools.ws.resources.model";
    }
}
