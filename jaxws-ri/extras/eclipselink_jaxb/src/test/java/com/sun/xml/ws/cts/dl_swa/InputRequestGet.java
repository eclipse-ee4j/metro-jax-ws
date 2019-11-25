/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.dl_swa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InputRequestGet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InputRequestGet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mimeType1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mimeType2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="url1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="url2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputRequestGet", propOrder = {
    "mimeType1",
    "mimeType2",
    "url1",
    "url2"
})
public class InputRequestGet {

    @XmlElement(required = true)
    protected String mimeType1;
    @XmlElement(required = true)
    protected String mimeType2;
    @XmlElement(required = true)
    protected String url1;
    @XmlElement(required = true)
    protected String url2;

    /**
     * Gets the value of the mimeType1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType1() {
        return mimeType1;
    }

    /**
     * Sets the value of the mimeType1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType1(String value) {
        this.mimeType1 = value;
    }

    /**
     * Gets the value of the mimeType2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType2() {
        return mimeType2;
    }

    /**
     * Sets the value of the mimeType2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType2(String value) {
        this.mimeType2 = value;
    }

    /**
     * Gets the value of the url1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl1() {
        return url1;
    }

    /**
     * Sets the value of the url1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl1(String value) {
        this.url1 = value;
    }

    /**
     * Gets the value of the url2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl2() {
        return url2;
    }

    /**
     * Sets the value of the url2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl2(String value) {
        this.url2 = value;
    }

}
