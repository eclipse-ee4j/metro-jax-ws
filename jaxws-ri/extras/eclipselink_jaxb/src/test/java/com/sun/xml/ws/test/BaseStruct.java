/*
 * Copyright (c) 2012, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.test;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for baseStruct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="baseStruct">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="floatMessage" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         <element name="shortMessage" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseStruct", propOrder = {
    "floatMessage",
    "shortMessage"
})
@XmlSeeAlso({
    ExtendedStruct.class
})
public class BaseStruct {

    protected float floatMessage;
    protected short shortMessage;

    /**
     * Gets the value of the floatMessage property.
     * 
     */
    public float getFloatMessage() {
        return floatMessage;
    }

    /**
     * Sets the value of the floatMessage property.
     * 
     */
    public void setFloatMessage(float value) {
        this.floatMessage = value;
    }

    /**
     * Gets the value of the shortMessage property.
     * 
     */
    public short getShortMessage() {
        return shortMessage;
    }

    /**
     * Sets the value of the shortMessage property.
     * 
     */
    public void setShortMessage(short value) {
        this.shortMessage = value;
    }

}
