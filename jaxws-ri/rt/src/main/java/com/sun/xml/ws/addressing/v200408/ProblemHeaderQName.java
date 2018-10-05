/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing.v200408;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

import static com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME;

/**
 * @author Arun Gupta
 */
@XmlRootElement(name="ProblemHeaderQName", namespace= WSA_NAMESPACE_NAME)
public class ProblemHeaderQName {
    @XmlValue
    private QName value;

    /** Creates a new instance of ProblemHeaderQName */
    public ProblemHeaderQName() {
    }

    public ProblemHeaderQName(QName name) {
        this.value = name;
    }
}
