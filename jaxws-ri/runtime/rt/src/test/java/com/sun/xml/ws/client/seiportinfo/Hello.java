/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.seiportinfo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

@WebService(name = "Hello", targetNamespace = "urn:test")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface Hello {

    @WebMethod(action = "urn:test:hello")
    @WebResult(name = "HelloResponse", targetNamespace = "urn:test:types", partName = "parameters")
    public String hello(
            @WebParam(name = "Hello", targetNamespace = "urn:test:types", partName = "parameters")
            String parameters,
            @WebParam(name = "Extra", targetNamespace = "urn:test:types", header = true, mode = WebParam.Mode.INOUT, partName = "header")
            Holder<String> header);

}
