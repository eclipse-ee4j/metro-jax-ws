/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.soap12.nosei.server;

import java.lang.Float;
import java.lang.Integer;
import java.lang.String;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "FooException", namespace = "urn:test:types")
public class FooException {

    @XmlElement(name = "varString", namespace = "", type = String.class)
    protected String varString;
    @XmlElement(name = "varInt", namespace = "", type = Integer.class)
    protected int varInt;
    @XmlElement(name = "varFloat", namespace = "", type = Float.class)
    protected float varFloat;

    /**
     * Gets the value of the varString property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    @XmlTransient
    public String getVarString() {
        return varString;
    }

    /**
     * Sets the value of the varString property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setVarString(String value) {
        this.varString = value;
    }

    /**
     * Gets the value of the varInt property.
     * 
     */
    @XmlTransient
    public int getVarInt() {
        return varInt;
    }

    /**
     * Sets the value of the varInt property.
     * 
     */
    public void setVarInt(int value) {
        this.varInt = value;
    }

    /**
     * Gets the value of the varFloat property.
     * 
     */
    @XmlTransient
    public float getVarFloat() {
        return varFloat;
    }

    /**
     * Sets the value of the varFloat property.
     * 
     */
    public void setVarFloat(float value) {
        this.varFloat = value;
    }

}
