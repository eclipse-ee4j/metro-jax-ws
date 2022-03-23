/*
 * Copyright (c) 2012, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.xmlns.webservices.jaxws_databinding;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.lang.annotation.Annotation;


/**
 * This file was generated by JAXB-RI v2.2.6 and afterwards modified
 * to implement appropriate Annotation
 *
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <attribute name="header" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       <attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       <attribute name="part-name" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       <attribute name="target-namespace" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "web-result")
public class XmlWebResult implements jakarta.jws.WebResult {

    @XmlAttribute(name = "header")
    protected Boolean header;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "part-name")
    protected String partName;
    @XmlAttribute(name = "target-namespace")
    protected String targetNamespace;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isHeader() {
        if (header == null) {
            return false;
        } else {
            return header;
        }
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHeader(Boolean value) {
        this.header = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        if (name == null) {
            return "";
        } else {
            return name;
        }
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the partName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartName() {
        if (partName == null) {
            return "";
        } else {
            return partName;
        }
    }

    /**
     * Sets the value of the partName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartName(String value) {
        this.partName = value;
    }

    /**
     * Gets the value of the targetNamespace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetNamespace() {
        if (targetNamespace == null) {
            return "";
        } else {
            return targetNamespace;
        }
    }

    /**
     * Sets the value of the targetNamespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetNamespace(String value) {
        this.targetNamespace = value;
    }

    @Override
    public String name() {
        return Util.nullSafe(name);
    }

    @Override
    public String partName() {
        return Util.nullSafe(partName);
    }

    @Override
    public String targetNamespace() {
        return Util.nullSafe(targetNamespace);
    }

    @Override
    public boolean header() {
        return Util.nullSafe(header, false);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return jakarta.jws.WebResult.class;
    }
}
