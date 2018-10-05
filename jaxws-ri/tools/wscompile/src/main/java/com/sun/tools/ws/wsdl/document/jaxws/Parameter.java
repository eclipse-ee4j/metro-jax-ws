/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document.jaxws;

import javax.xml.namespace.QName;

/**
 * @author Vivek Pandey
 *
 * class representing jaxws:parameter
 *
 */
public class Parameter {
    private String part;
    private QName element;
    private String name;
    private String messageName;

    /**
     * @param part
     * @param element
     * @param name
     */
    public Parameter(String msgName, String part, QName element, String name) {
        this.part = part;
        this.element = element;
        this.name = name;
        this.messageName = msgName;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    /**
     * @return Returns the element.
     */
    public QName getElement() {
        return element;
    }

    /**
     * @param element The element to set.
     */
    public void setElement(QName element) {
        this.element = element;
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

    /**
     * @return Returns the part.
     */
    public String getPart() {
        return part;
    }

    /**
     * @param part The part to set.
     */
    public void setPart(String part) {
        this.part = part;
    }
}
