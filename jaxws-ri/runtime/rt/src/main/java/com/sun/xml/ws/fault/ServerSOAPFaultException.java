/*
 * Copyright (c) 2012, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.fault;

import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

/**
 * Represents the SOAPFaultException that has occurred on the server side.
 *
 * <p>
 * When an exception occurs on the server, JAX-WS RI sends the SOAPFaultException
 * to the client. On the client side, instances of this class are used to represent
 * SOAPFaultException that adds diagnostic information to error message for easily
 * identifying the cause of exception.
 *
 * @author chinmay.patel
 *
 */
public class ServerSOAPFaultException extends SOAPFaultException {

    public ServerSOAPFaultException(SOAPFault soapFault) {
        super(soapFault);
    }

    public String getMessage() {
        return "Client received SOAP Fault from server: "
                + super.getMessage()
                + " Please see the server log to find more detail regarding exact cause of the failure.";
    }
}
