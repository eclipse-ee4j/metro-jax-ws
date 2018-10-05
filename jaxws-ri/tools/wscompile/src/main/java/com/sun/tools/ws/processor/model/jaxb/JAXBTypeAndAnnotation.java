/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model.jaxb;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.api.TypeAndAnnotation;

/**
 * Holds JAXB JType and TypeAndAnnotation. This provides abstration over
 * types from JAXBMapping and Property.
 */
public class JAXBTypeAndAnnotation {
    TypeAndAnnotation typeAnn;
    JType type;

    public JAXBTypeAndAnnotation(TypeAndAnnotation typeAnn) {
        this.typeAnn = typeAnn;
        this.type = typeAnn.getTypeClass();
    }

    public JAXBTypeAndAnnotation(JType type) {
        this.type = type;
    }

    public JAXBTypeAndAnnotation(TypeAndAnnotation typeAnn, JType type) {
        this.typeAnn = typeAnn;
        this.type = type;
    }

    public void annotate(JAnnotatable typeVar) {
        if(typeAnn != null)
            typeAnn.annotate(typeVar);
    }

    public JType getType() {
        return type;
    }

    public String getName(){
        return type.fullName();
    }

    public TypeAndAnnotation getTypeAnn() {
        return typeAnn;
    }

    public void setTypeAnn(TypeAndAnnotation typeAnn) {
        this.typeAnn = typeAnn;
    }

    public void setType(JType type) {
        this.type = type;
    }
}
