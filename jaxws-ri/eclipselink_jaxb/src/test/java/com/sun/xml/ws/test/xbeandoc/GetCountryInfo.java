/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.test.xbeandoc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="param0" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="param1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "param0",
    "param1"
})
@XmlRootElement(name = "getCountryInfo")
public class GetCountryInfo {

    @XmlElement(required = true)
    protected String param0;
    @XmlElement(required = true)
    protected String param1;

    /**
     * Gets the value of the param0 property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParam0() {
        return param0;
    }

    /**
     * Sets the value of the param0 property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParam0(String value) {
        this.param0 = value;
    }

    /**
     * Gets the value of the param1 property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParam1() {
        return param1;
    }

    /**
     * Sets the value of the param1 property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParam1(String value) {
        this.param1 = value;
    }

}
