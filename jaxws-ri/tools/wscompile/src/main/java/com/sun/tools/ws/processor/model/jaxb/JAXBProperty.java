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

import com.sun.tools.xjc.api.Property;

import javax.xml.namespace.QName;

/**
 * @author Kohsuke Kawaguchi
 */
public class JAXBProperty {

    /**
     * @see Property#name()
     */
    private String name;

    private JAXBTypeAndAnnotation type;
    /**
     * @see Property#elementName()
     */
    private QName elementName;

    /**
     * @see Property#rawName()
     */
    private QName rawTypeName;

    /**
     * Default constructor for the persistence.
     */
    public JAXBProperty() {}

    /**
     * Constructor that fills in the values from the given raw model
     */
    JAXBProperty( Property prop ) {
        this.name = prop.name();
        this.type = new JAXBTypeAndAnnotation(prop.type());
        this.elementName = prop.elementName();
        this.rawTypeName = prop.rawName();
    }

    /**
     * @see Property#name()
     */
    public String getName() {
        return name;
    }

    public QName getRawTypeName() {
        return rawTypeName;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public JAXBTypeAndAnnotation getType() {
        return type;
    }

    /**
     * @see Property#elementName()
     */
    public QName getElementName() {
        return elementName;
    }
}
