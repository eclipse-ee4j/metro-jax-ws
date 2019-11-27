/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing.model;

import com.sun.xml.ws.resources.AddressingMessages;

import javax.xml.ws.WebServiceException;
import javax.xml.namespace.QName;

/**
 * This exception captures SOAP Fault information when a WS-Addressing 1.0 Message Addressing
 * Property is invalid and cannot be processed.
 * 
 * @author Rama Pulavarthi
 */
public class InvalidAddressingHeaderException extends WebServiceException {
    private QName problemHeader;
    private QName subsubcode;

    /**
     * Creates a InvalidAddressingHeader exception capturing information about the invalid
     * Addressing Message Property and the reason in Subsubcode.
     * @param problemHeader
     *      represents the invalid Addressing Header.
     * @param subsubcode
     *      represents the reason why the Addressing header in question is invalid. 
     */
    public InvalidAddressingHeaderException(QName problemHeader, QName subsubcode) {
        super(AddressingMessages.INVALID_ADDRESSING_HEADER_EXCEPTION(problemHeader,subsubcode));
        this.problemHeader = problemHeader;
        this.subsubcode = subsubcode;
    }

    public QName getProblemHeader() {
        return problemHeader;
    }

    public QName getSubsubcode() {
        return subsubcode;
    }
}
