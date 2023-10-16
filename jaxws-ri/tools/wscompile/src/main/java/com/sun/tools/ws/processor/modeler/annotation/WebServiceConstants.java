/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.modeler.annotation;

/**
 * @author dkohlert
 */
public enum WebServiceConstants {

    SERVICE("Service"),

    JAXWS_PACKAGE_PD("jaxws."),

    PD_JAXWS_PACKAGE_PD(".jaxws."),

    BEAN("Bean"),

    FAULT_INFO("faultInfo"),

    RESPONSE("Response");

    private final String value;

    WebServiceConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
