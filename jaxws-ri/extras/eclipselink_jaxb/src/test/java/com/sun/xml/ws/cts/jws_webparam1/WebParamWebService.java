/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.jws_webparam1;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebParam.Mode;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;

import jakarta.xml.ws.Action;
import jakarta.xml.ws.FaultAction;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;

import com.sun.xml.ws.cts.jws_webparam1.Address;
import com.sun.xml.ws.cts.jws_webparam1.Employee;
import com.sun.xml.ws.cts.jws_webparam1.Name;
import com.sun.xml.ws.cts.jws_webparam1.NameException;
import com.sun.xml.ws.cts.jws_webparam1.NameException_Exception;


@WebService(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
  name="webParamWebService")
public interface WebParamWebService
{
  @WebMethod(action="urn:HelloString")
  @Action(input="urn:HelloString", output="http://server.webparam1.webparam.jws.tests.ts.sun.com/webParamWebService/helloString/Response")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @WebResult(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
    partName="helloStringResponse", name="helloStringResponse")
  public String helloString(@WebParam(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
      partName="string1", name="string1")
    String string1);

  @WebMethod(action="urn:HelloString2")
  @Action(input="urn:HelloString2", output="http://server.webparam1.webparam.jws.tests.ts.sun.com/webParamWebService/helloString2/Response")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @WebResult(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
    partName="helloString2Response", name="helloString2Response")
  public String helloString2(@WebParam(targetNamespace="helloString2/name", 
      partName="string2", name="name2")
    String string2);

  @WebMethod(action="urn:HelloString3")
  @Action(input="urn:HelloString3", output="http://server.webparam1.webparam.jws.tests.ts.sun.com/webParamWebService/helloString3/Response")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  public void helloString3(@WebParam(targetNamespace="helloString3/name", 
      header=true, partName="string3", name="name3")
    String string3, @WebParam(targetNamespace="helloString3/Name", mode=Mode.INOUT, 
      partName="Name", name="Name")
    Holder<Name> Name);

  @WebMethod(action="urn:HelloString4")
  @Action(input="urn:HelloString4", fault =
      { @FaultAction(value="http://server.webparam1.webparam.jws.tests.ts.sun.com/webParamWebService/helloString4/Fault/NameException", 
          className = NameException_Exception.class)
        } , output="http://server.webparam1.webparam.jws.tests.ts.sun.com/webParamWebService/helloString4/Response")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  public void helloString4(@WebParam(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
      partName="string4", name="name4")
    String string4, @WebParam(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
      mode=Mode.OUT, partName="Employee", name="Employee")
    Holder<Employee> Employee)
    throws NameException_Exception;

  @WebMethod(action="urn:HelloString5")
  @Action(input="urn:HelloString5", output="http://server.webparam1.webparam.jws.tests.ts.sun.com/webParamWebService/helloString5/Response")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @WebResult(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
    partName="helloString5Response", name="helloString5Response")
  public String helloString5(@WebParam(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
      partName="helloString5", name="helloString5")
    String helloString5);

  @WebMethod(action="urn:HelloString6")
  @Action(input="urn:HelloString6", output="http://server.webparam1.webparam.jws.tests.ts.sun.com/webParamWebService/helloString6/Response")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @WebResult(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
    partName="helloString6Response", name="helloString6Response")
  public String helloString6(@WebParam(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
      partName="helloString6", name="helloString6")
    int helloString6, @WebParam(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
      header=true, partName="name6", name="name6")
    String name6);

  @WebMethod(action="urn:HelloString7")
  @Action(input="urn:HelloString7", fault =
      { @FaultAction(value="http://server.webparam1.webparam.jws.tests.ts.sun.com/webParamWebService/helloString7/Fault/NameException", 
          className = NameException_Exception.class)
        } , output="http://server.webparam1.webparam.jws.tests.ts.sun.com/webParamWebService/helloString7/Response")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  public void helloString7(@WebParam(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
      header=true, partName="string7", name="name7")
    String string7, @WebParam(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
      partName="string8", name="name8")
    Name string8, @WebParam(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
      mode=Mode.OUT, partName="MyEmployee", name="MyEmployee")
    Holder<Employee> MyEmployee)
    throws NameException_Exception;

  @WebMethod(action="urn:HelloString8")
  @ResponseWrapper(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
    className="oracle.j2ee.ws.jaxws.cts.jws_webparam1.HelloString8Response", 
    localName="helloString8Response")
  @RequestWrapper(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
    className="oracle.j2ee.ws.jaxws.cts.jws_webparam1.HelloString8", 
    localName="helloString8")
  @Action(input="urn:HelloString8", output="http://server.webparam1.webparam.jws.tests.ts.sun.com/webParamWebService/helloString8/Response")
  @WebResult(targetNamespace="")
  public String helloString8(@WebParam(targetNamespace="", name="string8")
    String string8, @WebParam(targetNamespace="", name="arg1")
    Address arg1);
}
