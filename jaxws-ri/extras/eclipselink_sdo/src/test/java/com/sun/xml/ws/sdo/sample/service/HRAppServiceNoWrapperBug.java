/*
 * Copyright (c) 2012, 2019 Oracle and/or its affiliates. All rights reserved.
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

@javax.jws.WebService(targetNamespace="http://sdo.sample.service/", name="HRAppService")
@javax.jws.soap.SOAPBinding(parameterStyle=javax.jws.soap.SOAPBinding.ParameterStyle.WRAPPED,
                            style=javax.jws.soap.SOAPBinding.Style.DOCUMENT)

public interface HRAppServiceNoWrapperBug {


    @javax.jws.WebMethod(action="http://sdo.sample.service//getTotalComp",
                         operationName="getTotalComp")
//    @javax.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
//                                  className="com.sun.xml.ws.sdo.sample.service.types.GetTotalCompResponse",
//                                  localName="getTotalCompResponse")
//                                  className="com.sun.xml.ws.sdo.sample.service.types.GetTotalCompResponseElement",
//                                  localName="getTotalCompResponseElement")
//    @javax.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
//                                 className="com.sun.xml.ws.sdo.sample.service.types.GetTotalComp", 
//                                 localName="getTotalComp")
//                                 className="com.sun.xml.ws.sdo.sample.service.types.GetTotalCompElement", 
//                                 localName="getTotalCompElement")
    @javax.jws.WebResult(name="result", targetNamespace="http://sdo.sample.service/types/")
    public java.math.BigDecimal getTotalComp(@javax.jws.WebParam(mode=javax.jws.WebParam.Mode.IN,
                                                  name="empno", targetNamespace="http://sdo.sample.service/types/")
      BigInteger empno);
}
