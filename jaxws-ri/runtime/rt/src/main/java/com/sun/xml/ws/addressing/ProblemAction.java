/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import com.sun.xml.ws.addressing.W3CAddressingConstants;

/**
 * @author Arun Gupta
 */
@XmlRootElement(name="ProblemAction", namespace= W3CAddressingConstants.WSA_NAMESPACE_NAME)
public class ProblemAction {

    @XmlElement(name="Action", namespace=W3CAddressingConstants.WSA_NAMESPACE_NAME)
    private String action;

    @XmlElement(name="SoapAction", namespace=W3CAddressingConstants.WSA_NAMESPACE_NAME)
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
