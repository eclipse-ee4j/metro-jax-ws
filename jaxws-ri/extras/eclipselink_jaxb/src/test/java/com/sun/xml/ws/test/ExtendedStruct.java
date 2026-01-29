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
 * <p>Java class for extendedStruct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="extendedStruct">
 *   <complexContent>
 *     <extension base="{http://performance.bea.com}baseStruct">
 *       <sequence>
 *         <element name="anotherIntMessage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="intMessage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="stringMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "extendedStruct", propOrder = {
    "anotherIntMessage",
    "intMessage",
    "stringMessage"
})
@XmlSeeAlso({
    MoreExtendedStruct.class
})
public class ExtendedStruct
    extends BaseStruct
{

    protected int anotherIntMessage;
    protected int intMessage;
    protected String stringMessage;

    /**
     * Gets the value of the anotherIntMessage property.
     * 
     */
    public int getAnotherIntMessage() {
        return anotherIntMessage;
    }

    /**
     * Sets the value of the anotherIntMessage property.
     * 
     */
    public void setAnotherIntMessage(int value) {
        this.anotherIntMessage = value;
    }

    /**
     * Gets the value of the intMessage property.
     * 
     */
    public int getIntMessage() {
        return intMessage;
    }

    /**
     * Sets the value of the intMessage property.
     * 
     */
    public void setIntMessage(int value) {
        this.intMessage = value;
    }

    /**
     * Gets the value of the stringMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStringMessage() {
        return stringMessage;
    }

    /**
     * Sets the value of the stringMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStringMessage(String value) {
        this.stringMessage = value;
    }

}
