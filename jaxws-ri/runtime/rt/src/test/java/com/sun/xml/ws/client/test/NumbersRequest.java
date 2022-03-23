/*
 * Copyright (c) 2013, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.test;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for numbersRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="numbersRequest">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="number1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="number2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="guess" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "numbersRequest", propOrder = {
    "number1",
    "number2",
    "guess"
})
public class NumbersRequest {

    protected int number1;
    protected int number2;
    protected int guess;

    /**
     * Gets the value of the number1 property.
     * 
     */
    public int getNumber1() {
        return number1;
    }

    /**
     * Sets the value of the number1 property.
     * 
     */
    public void setNumber1(int value) {
        this.number1 = value;
    }

    /**
     * Gets the value of the number2 property.
     * 
     */
    public int getNumber2() {
        return number2;
    }

    /**
     * Sets the value of the number2 property.
     * 
     */
    public void setNumber2(int value) {
        this.number2 = value;
    }

    /**
     * Gets the value of the guess property.
     * 
     */
    public int getGuess() {
        return guess;
    }

    /**
     * Sets the value of the guess property.
     * 
     */
    public void setGuess(int value) {
        this.guess = value;
    }

}
