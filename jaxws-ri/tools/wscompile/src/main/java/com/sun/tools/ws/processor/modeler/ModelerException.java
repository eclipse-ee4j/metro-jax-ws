/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.modeler;

import com.sun.istack.localization.Localizable;
import com.sun.tools.ws.processor.ProcessorException;

/**
 * ModelerException represents an exception that occurred while
 * visiting service model.
 *
 * @see ProcessorException
 *
 * @author WS Development Team
*/
public class ModelerException extends ProcessorException {

    public ModelerException(String key) {
        super(key);
    }

    public ModelerException(String key, Object... args) {
        super(key, args);
    }

    public ModelerException(Throwable throwable) {
        super(throwable);
    }

    public ModelerException(Localizable arg) {
        super("modeler.nestedModelError", arg);
    }

    @Override
    public String getDefaultResourceBundleName() {
        return "com.sun.tools.ws.resources.modeler";
    }

}
