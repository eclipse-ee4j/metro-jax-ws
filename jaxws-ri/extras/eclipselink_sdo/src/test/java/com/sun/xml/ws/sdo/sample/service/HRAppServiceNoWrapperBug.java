/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.sdo.sample.service;

import java.math.BigInteger;


import java.util.List;

import com.sun.xml.ws.sdo.sample.service.types.*;
//import oracle.j2ee.ws.common.sdo.SchemaLocation;

@jakarta.jws.WebService(targetNamespace="http://sdo.sample.service/", name="HRAppService")
@jakarta.jws.soap.SOAPBinding(parameterStyle=jakarta.jws.soap.SOAPBinding.ParameterStyle.WRAPPED,
                            style=jakarta.jws.soap.SOAPBinding.Style.DOCUMENT)

public interface HRAppServiceNoWrapperBug {


    @jakarta.jws.WebMethod(action="http://sdo.sample.service//getTotalComp",
                         operationName="getTotalComp")
//    @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
//                                  className="com.sun.xml.ws.sdo.sample.service.types.GetTotalCompResponse",
//                                  localName="getTotalCompResponse")
//                                  className="com.sun.xml.ws.sdo.sample.service.types.GetTotalCompResponseElement",
//                                  localName="getTotalCompResponseElement")
//    @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
//                                 className="com.sun.xml.ws.sdo.sample.service.types.GetTotalComp", 
//                                 localName="getTotalComp")
//                                 className="com.sun.xml.ws.sdo.sample.service.types.GetTotalCompElement", 
//                                 localName="getTotalCompElement")
    @jakarta.jws.WebResult(name="result", targetNamespace="http://sdo.sample.service/types/")
    public java.math.BigDecimal getTotalComp(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                  name="empno", targetNamespace="http://sdo.sample.service/types/")
      BigInteger empno);
}
