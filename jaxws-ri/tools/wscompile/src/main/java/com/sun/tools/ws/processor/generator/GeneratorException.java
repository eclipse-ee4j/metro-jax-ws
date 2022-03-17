/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.generator;

import com.sun.tools.ws.processor.ProcessorException;

/**
 *
 * @author WS Development Team
 */
public class GeneratorException extends ProcessorException {

    public GeneratorException(String key, Object... args) {
        super(key, args);
    }

    public GeneratorException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getDefaultResourceBundleName() {
        return "com.sun.tools.ws.resources.generator";
    }
}
