/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model.java;

/**
 *
 * @author WS Development Team
 */
public class JavaArrayType extends JavaType {

    public JavaArrayType() {
    }

    public JavaArrayType(String name) {
        super(name, true, "null");
    }

    public JavaArrayType(String name, String elementName,
        JavaType elementType) {

        super(name, true, "null");
        this.elementName = elementName;
        this.elementType = elementType;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String name) {
        elementName = name;
    }

    public JavaType getElementType() {
        return elementType;
    }

    public void setElementType(JavaType type) {
        elementType = type;
    }

    // bug fix:4904604
    public String getSOAPArrayHolderName() {
        return soapArrayHolderName;
    }

    public void setSOAPArrayHolderName(String holderName) {
        this.soapArrayHolderName = holderName;
    }

    private String elementName;
    private JavaType elementType;
    private String soapArrayHolderName;
}
