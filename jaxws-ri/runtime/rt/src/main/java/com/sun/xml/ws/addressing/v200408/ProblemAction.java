/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing.v200408;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * @author Arun Gupta
 */
@XmlRootElement(name="ProblemAction", namespace= MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME)
public class ProblemAction {

    @XmlElement(name="Action", namespace= MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME)
    private String action;

    @XmlElement(name="SoapAction", namespace= MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME)
    private String soapAction;

    /** Creates a new instance of ProblemAction */
    public ProblemAction() {
    }

    public ProblemAction(String action) {
        this.action = action;
    }

    public ProblemAction(String action, String soapAction) {
        this.action = action;
        this.soapAction = soapAction;
    }

    public String getAction() {
        return action;
    }

    public String getSoapAction() {
        return soapAction;
    }
}
