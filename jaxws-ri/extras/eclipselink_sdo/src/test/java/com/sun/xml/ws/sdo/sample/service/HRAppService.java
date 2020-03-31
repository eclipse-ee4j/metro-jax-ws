/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

@jakarta.jws.WebService(targetNamespace="http://sdo.sample.service/", name="HRAppService", wsdlLocation="build/client/data/sdo/HRAppService.wsdl")
@jakarta.jws.soap.SOAPBinding(parameterStyle=jakarta.jws.soap.SOAPBinding.ParameterStyle.WRAPPED,
                            style=jakarta.jws.soap.SOAPBinding.Style.DOCUMENT)
//@SchemaLocation(value = "data/sdo/HRAppService.wsdl")

public interface HRAppService
{
  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.CreateEmpElement", 
                               localName="createEmpElement")
  @jakarta.jws.WebMethod(action="http://sdo.sample.service//createEmp",
                       operationName="createEmp")
  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.CreateEmpResponseElement",
                                localName="createEmpResponseElement")
  @jakarta.jws.WebResult(name="emp")
  public Emp createEmp(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                           name="emp")
    Emp emp);

  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.DeleteEmpElement", localName="deleteEmpElement")
  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.DeleteEmpResponseElement",
                                localName="deleteEmpResponseElement")
  @jakarta.jws.WebMethod(action="http://sdo.sample.service//deleteEmp",
                       operationName="deleteEmp")
  public void deleteEmp(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                            name="emp")
    Emp emp);

  @jakarta.jws.WebMethod(action="http://sdo.sample.service//findClerks",
                       operationName="findClerks")
  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.FindClerksResponseElement",
                                localName="findClerksResponseElement")
  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.FindClerksElement", localName="findClerksElement")
  @jakarta.jws.WebResult(name="emp")
  public List<Emp> findClerks(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                  name="findCriteria")
    FindCriteria findCriteria, @jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                   name="findControl")
    FindControl findControl);

  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.FindDeptsElement", localName="findDeptsElement")
  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.FindDeptsResponseElement",
                                localName="findDeptsResponseElement")
  @jakarta.jws.WebMethod(action="http://sdo.sample.service//findDepts",
                       operationName="findDepts")
  @jakarta.jws.WebResult(name="dept")
  public List<Dept> findDepts(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                  name="findCriteria")
    FindCriteria findCriteria, @jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                   name="findControl")
    FindControl findControl);

  @jakarta.jws.WebMethod(action="http://sdo.sample.service//findEmps",
                       operationName="findEmps")
  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.FindEmpsResponseElement",
                                localName="findEmpsResponseElement")
  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.FindEmpsElement", localName="findEmpsElement")
  @jakarta.jws.WebResult(name="emp")
  public List<Emp> findEmps(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                name="findCriteria")
    FindCriteria findCriteria, @jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                   name="findControl")
    FindControl findControl);

  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.FindEmpsByJobElement", localName="findEmpsByJobElement")
  @jakarta.jws.WebMethod(action="http://sdo.sample.service//findEmpsByJob",
                       operationName="findEmpsByJob")
  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.FindEmpsByJobResponseElement",
                                localName="findEmpsByJobResponseElement")
  @jakarta.jws.WebResult(name="emp")
  public List<Emp> findEmpsByJob(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                     name="findCriteria")
    FindCriteria findCriteria, @jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                   name="job")
    String job, @jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN, name="findControl")
    FindControl findControl);

  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.GetDeptResponseElement",
                                localName="getDeptResponseElement")
  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.GetDeptElement", localName="getDeptElement")
  @jakarta.jws.WebMethod(action="http://sdo.sample.service//getDept",
                       operationName="getDept")
  @jakarta.jws.WebResult(name="dept")
  public Dept getDept(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                          name="deptno")
    BigInteger deptno);

  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.GetEmpElement", localName="getEmpElement")
  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.GetEmpResponseElement", localName="getEmpResponseElement")
  @jakarta.jws.WebMethod(action="http://sdo.sample.service//getEmp",
                       operationName="getEmp")
  @jakarta.jws.WebResult(name="emp")
  public Emp getEmp(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                        name="empno")
    BigInteger empno);

  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.GetManagerAndPeersResponseElement",
                                localName="getManagerAndPeersResponseElement")
  @jakarta.jws.WebMethod(action="http://sdo.sample.service//getManagerAndPeers",
                       operationName="getManagerAndPeers")
  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.GetManagerAndPeersElement",
                               localName="getManagerAndPeersElement")
  @jakarta.jws.WebResult(name="emp")
  public List<Emp> getManagerAndPeers(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                          name="empno")
    BigInteger empno);

  @jakarta.jws.WebMethod(action="http://sdo.sample.service//getTotalComp",
                       operationName="getTotalComp")
  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.GetTotalCompResponseElement",
                                localName="getTotalCompResponseElement")
  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.GetTotalCompElement", localName="getTotalCompElement")
  @jakarta.jws.WebResult(name="result")
  public java.math.BigDecimal getTotalComp(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                name="empno")
    BigInteger empno);

  @jakarta.jws.WebMethod(action="http://sdo.sample.service//mergeEmp",
                       operationName="mergeEmp")
  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.MergeEmpResponseElement",
                                localName="mergeEmpResponseElement")
  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.MergeEmpElement", localName="mergeEmpElement")
  @jakarta.jws.WebResult(name="emp")
  public Emp mergeEmp(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                          name="emp")
    Emp emp);

  @jakarta.jws.WebMethod(action="http://sdo.sample.service//processEmps",
                       operationName="processEmps")
  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.ProcessEmpsResponseElement",
                                localName="processEmpsResponseElement")
  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.ProcessEmpsElement", localName="processEmpsElement")
  @jakarta.jws.WebResult(name="emp")
  public List<Emp> processEmps(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                   name="changeOperation")
    String changeOperation, @jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                                name="emp")
    List<Emp> emp, @jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                       name="processControl")
    ProcessControl processControl);

  @jakarta.xml.ws.RequestWrapper(targetNamespace="http://sdo.sample.service/types/",
                               className="com.sun.xml.ws.sdo.sample.service.types.UpdateEmpElement", localName="updateEmpElement")
  @jakarta.xml.ws.ResponseWrapper(targetNamespace="http://sdo.sample.service/types/",
                                className="com.sun.xml.ws.sdo.sample.service.types.UpdateEmpResponseElement",
                                localName="updateEmpResponseElement")
  @jakarta.jws.WebMethod(action="http://sdo.sample.service//updateEmp",
                       operationName="updateEmp")
  @jakarta.jws.WebResult(name="emp")
  public Emp updateEmp(@jakarta.jws.WebParam(mode=jakarta.jws.WebParam.Mode.IN,
                                           name="emp")
    Emp emp);
}
