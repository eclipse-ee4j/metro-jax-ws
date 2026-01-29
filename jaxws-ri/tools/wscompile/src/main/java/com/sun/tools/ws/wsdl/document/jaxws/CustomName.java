/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document.jaxws;

public class CustomName {


    /**
     *
     */
    public CustomName() {
    }

    /**
     *
     */
    public CustomName(String name, String javaDoc) {
        this.name = name;
        this.javaDoc = javaDoc;
    }

    /**
     * @return Returns the javaDoc.
     */
    public String getJavaDoc() {
        return javaDoc;
    }
    /**
     * @param javaDoc The javaDoc to set.
     */
    public void setJavaDoc(String javaDoc) {
        this.javaDoc = javaDoc;
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

    private String javaDoc;
    private String name;
}
