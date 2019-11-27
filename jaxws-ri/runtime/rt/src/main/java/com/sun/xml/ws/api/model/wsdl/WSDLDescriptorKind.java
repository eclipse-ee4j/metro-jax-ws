/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model.wsdl;

/**
 * Enumeration that tells a wsdl:part that can be defined either using a type
 * attribute or an element attribute.
 *
 * @author Vivek Pandey
 */
public enum WSDLDescriptorKind {
    /**
     * wsdl:part is defined using element attribute.
     *
     * <pre>
     * for exmaple,
     * &lt;wsdl:part name="foo" element="ns1:FooElement">
     * </pre>
     */
    ELEMENT(0),

    /**
     * wsdl:part is defined using type attribute.
     *
     * <pre>
     * for exmaple,
     * &lt;wsdl:part name="foo" element="ns1:FooType">
     * </pre>
     */
    TYPE(1);

    WSDLDescriptorKind(int value) {
        this.value = value;
    }

    private final int value;
}
