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
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * This file was generated by JAXB-RI v2.2.6 and afterwards modified
 * to implement appropriate Annotation
 *
 * <p>Java class for existing-annotations-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>{@code
 * <simpleType name="existing-annotations-type">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="merge"/>
 *     <enumeration value="ignore"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "existing-annotations-type")
@XmlEnum
public enum ExistingAnnotationsType {

    @XmlEnumValue("merge")
    MERGE("merge"),
    @XmlEnumValue("ignore")
    IGNORE("ignore");
    private final String value;

    ExistingAnnotationsType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExistingAnnotationsType fromValue(String v) {
        for (ExistingAnnotationsType c: ExistingAnnotationsType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
