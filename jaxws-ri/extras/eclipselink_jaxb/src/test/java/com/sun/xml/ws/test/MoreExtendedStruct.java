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
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for moreExtendedStruct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="moreExtendedStruct">
 *   <complexContent>
 *     <extension base="{http://performance.bea.com}extendedStruct">
 *       <sequence>
 *         <element name="booleanMessage" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "moreExtendedStruct", propOrder = {
    "booleanMessage"
})
public class MoreExtendedStruct
    extends ExtendedStruct
{

    protected boolean booleanMessage;

    /**
     * Gets the value of the booleanMessage property.
     * 
     */
    public boolean isBooleanMessage() {
        return booleanMessage;
    }

    /**
     * Sets the value of the booleanMessage property.
     * 
     */
    public void setBooleanMessage(boolean value) {
        this.booleanMessage = value;
    }

}
