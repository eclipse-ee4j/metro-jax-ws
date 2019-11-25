/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model;
/**
 * Type of java exception as defined by JAXWS 2.0 JSR 224.
 *
 * Tells whether the exception class is a userdefined or a WSDL exception.
 * A WSDL exception class follows the pattern defined in JSR 224. According to that
 * a WSDL exception class must have:
 *
 * <code>public WrapperException()String message, FaultBean){}</code>
 *
 * and accessor method
 *
 * <code>public FaultBean getFaultInfo();</code>
 * 
 * @author Vivek Pandey
 */
public enum ExceptionType {
    WSDLException(0), UserDefined(1);

    ExceptionType(int exceptionType){
        this.exceptionType = exceptionType;
    }

    public int value() {
        return exceptionType;
    }
    private final int exceptionType;
}
