/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model;

import com.sun.xml.bind.api.Bridge;
import com.sun.xml.ws.spi.db.TypeInfo;

import javax.xml.ws.WebFault;

/**
 * This class provides abstractio to the  the exception class
 * corresponding to the wsdl:fault, such as class MUST have
 * {@link WebFault} annotation defined on it.
 *
 * Also the exception class must have
 *
 * <code>public WrapperException()String message, FaultBean){}</code>
 *
 * and method
 *
 * <code>public FaultBean getFaultInfo();</code>
 *
 * @author Vivek Pandey
 */
public interface CheckedException {
    /**
     * Gets the root {@link SEIModel} that owns this model.
     */
    SEIModel getOwner();

    /**
     * Gets the parent {@link JavaMethod} to which this checked exception belongs.
     */
    JavaMethod getParent();

    /**
     * The returned exception class would be userdefined or WSDL exception class.
     *
     * @return
     *      always non-null same object.
     */
    Class getExceptionClass();

    /**
     * The detail bean is serialized inside the detail entry in the SOAP message.
     * This must be known to the {@link javax.xml.bind.JAXBContext} inorder to get
     * marshalled/unmarshalled.
     *
     * @return the detail bean
     */
    Class getDetailBean();

    /**
     * Gives the {@link com.sun.xml.bind.api.Bridge} associated with the detail
     * @deprecated Why do you need this?
     */
    Bridge getBridge();

    /**
     * Tells whether the exception class is a userdefined or a WSDL exception.
     * A WSDL exception class follows the pattern defined in JSR 224. According to that
     * a WSDL exception class must have:
     *
     * <code>public WrapperException()String message, FaultBean){}</code>
     *
     * and accessor method
     *
     * <code>public FaultBean getFaultInfo();</code>     
     */
    ExceptionType getExceptionType();

    /**
     * Gives the wsdl:portType/wsdl:operation/wsdl:fault@message value - that is the wsdl:message
     * referenced by wsdl:fault
     */
    String getMessageName();

    /**
     * Gives the {@link com.sun.xml.ws.spi.db.TypeInfo} of the detail
     */
    TypeInfo getDetailType();
}
