/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing.v200408;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME;
/**
 * @author Arun Gupta
 */
@XmlRootElement(name="ProblemAction", namespace= WSA_NAMESPACE_NAME)
public class ProblemAction {

    @XmlElement(name="Action", namespace= WSA_NAMESPACE_NAME)
    private String action;

    @XmlElement(name="SoapAction", namespace=WSA_NAMESPACE_NAME)
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
