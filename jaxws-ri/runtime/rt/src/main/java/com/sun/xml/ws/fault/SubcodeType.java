/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.fault;

import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;

/**
 * <pre>{@code
 *      <env:Subcode>
 *          <env:Value>m:MessageTimeout1</env:Value>
 *          <env:Subcode>
 *              <env:Value>m:MessageTimeout2</env:Value>
 *          </env:Subcode>
 *      </env:Subcode>
 *  }</pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubcodeType", namespace = "http://www.w3.org/2003/05/soap-envelope", propOrder = {
    "Value",
    "Subcode"
        })
class SubcodeType {
    @XmlTransient
    private static final String ns="http://www.w3.org/2003/05/soap-envelope";
    /**
     * mandatory, minOccurs=1
     */
    @XmlElement(namespace = ns)
    private QName Value;

    /**
     * optional, minOcccurs=0
     */
    @XmlElements(@XmlElement(namespace = ns))
    private SubcodeType Subcode;

    public SubcodeType(QName value) {
        Value = value;
    }

    public SubcodeType() {
    }

    QName getValue() {
        return Value;
    }

    SubcodeType getSubcode() {
        return Subcode;
    }

    void setSubcode(SubcodeType subcode) {
        Subcode = subcode;
    }
}
