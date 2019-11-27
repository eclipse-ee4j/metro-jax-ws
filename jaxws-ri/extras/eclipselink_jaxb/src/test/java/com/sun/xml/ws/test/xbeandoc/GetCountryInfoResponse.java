/*
 * Copyright (c) 2017, 2019 Oracle and/or its affiliates. All rights reserved.
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
 *         &lt;element name="CountryInfoType" type="{http://www.bea.com/wli/sb/transports/ejb/test/xbean}CountryInfoType"/>
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
    "countryInfoType"
})
@XmlRootElement(name = "getCountryInfoResponse")
public class GetCountryInfoResponse {

    @XmlElement(name = "CountryInfoType", required = true)
    protected CountryInfoType countryInfoType;

    /**
     * Gets the value of the countryInfoType property.
     *
     * @return
     *     possible object is
     *     {@link CountryInfoType }
     *
     */
    public CountryInfoType getCountryInfoType() {
        return countryInfoType;
    }

    /**
     * Sets the value of the countryInfoType property.
     *
     * @param value
     *     allowed object is
     *     {@link CountryInfoType }
     *
     */
    public void setCountryInfoType(CountryInfoType value) {
        this.countryInfoType = value;
    }

}
