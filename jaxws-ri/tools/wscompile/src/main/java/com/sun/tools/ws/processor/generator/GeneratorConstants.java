/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.generator;

/**
 * @author WS Development Team
 */
public enum GeneratorConstants {

    DOTC("."),

    SIG_INNERCLASS("$"),

    JAVA_SRC_SUFFIX(".java"),

    QNAME_SUFFIX("_QNAME"),

    GET("get"),

    IS("is"),

    RESPONSE("Response"),

    FAULT_CLASS_MEMBER_NAME("faultInfo");

    private final String value;

    GeneratorConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
