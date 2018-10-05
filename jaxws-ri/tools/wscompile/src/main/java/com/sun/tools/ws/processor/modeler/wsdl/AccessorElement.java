/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.modeler.wsdl;

import javax.xml.namespace.QName;


/**
 * @author Vivek Pandey
 *
 * Rpc/Lit AccessorElement to be used to generate pseudo schema
 */
class AccessorElement {

    private QName type;
    private String name;


    /**
     * @param type
     * @param name
     */
    public AccessorElement(String name, QName type) {
        this.type = type;
        this.name = name;
    }
    /**
     * @return Returns the type.
     */
    public QName getType() {
        return type;
    }
    /**
     * @param type The type to set.
     */
    public void setType(QName type) {
        this.type = type;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
