/*
 * Copyright (c) 2012, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.sdo.test;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.BindingType;

/**
 * Created by IntelliJ IDEA.
 * User: giglee
 * Date: Jun 2, 2009
 * Time: 3:55:20 PM
 * To change this template use File | Settings | File Templates.
 */
@WebService
@BindingType(value = "http://schemas.xmlsoap.org/wsdl/soap/http")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface AddNumbersPortType {
    @ResponseWrapper(localName = "addNumbersResponse", targetNamespace = "http://example.org",
            className = "com.sun.xml.ws.sdo.test.add.AddNumbersResponse")
    @RequestWrapper(localName = "addNumbers", targetNamespace = "http://example.org",
            className = "com.sun.xml.ws.sdo.test.add.AddNumbers")
    @WebResult(targetNamespace = "http://example.org", name = "return")
    public int addNumbers(@WebParam(targetNamespace = "http://example.org", name = "arg0")
    int number1, @WebParam(targetNamespace = "http://example.org", name = "arg1")
    int number2);

}
