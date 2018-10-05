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

import com.sun.tools.ws.processor.model.AbstractType;

import javax.xml.namespace.QName;

/**
 * @author Vivek Pandey
 *
 * Represents RpcLit member parts
 */
public class RpcLitMember extends AbstractType {

    //wsdl:part type attribute java mapped object
    private String javaTypeName;
    private QName schemaTypeName;

    /**
     *
     */
    public RpcLitMember() {
        super();
        // TODO Auto-generated constructor stub
    }
    public RpcLitMember(QName name, String javaTypeName){
        setName(name);
        this.javaTypeName = javaTypeName;
    }
    public RpcLitMember(QName name, String javaTypeName, QName schemaTypeName){
        setName(name);
        this.javaTypeName = javaTypeName;
        this.schemaTypeName = schemaTypeName;
    }

    /**
     * @return Returns the type.
     */
    public String getJavaTypeName() {
        return javaTypeName;
    }
    /**
     * @param type The type to set.
     */
    public void setJavaTypeName(String type) {
        this.javaTypeName = type;
    }

    /**
     * @return Returns the type.
     */
    public QName getSchemaTypeName() {
        return schemaTypeName;
    }
    /**
     * @param type The type to set.
     */
    public void setSchemaTypeName(QName type) {
        this.schemaTypeName = type;
    }
}
