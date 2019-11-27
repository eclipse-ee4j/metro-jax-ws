/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.dl_swa;

import javax.activation.DataHandler;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;

import javax.xml.ws.Action;

@WebService(targetNamespace="http://SwaTestService.org/wsdl", name="SwaTest2")
@SOAPBinding(parameterStyle=ParameterStyle.BARE, style=Style.DOCUMENT)
public interface SwaTest2
{
  @WebMethod
  @Action(input="http://SwaTestService.org/wsdl/SwaTest2/putMultipleAttachments/Request", 
    output="http://SwaTestService.org/wsdl/SwaTest2/putMultipleAttachments/Response")
  @WebResult(targetNamespace="http://SwaTestService.org/xsd", partName="response", 
    name="OutputResponseString")
  public OutputResponseString putMultipleAttachments(@WebParam(targetNamespace="http://SwaTestService.org/xsd", 
      partName="request", name="InputRequestPut")
    InputRequestPut request, @WebParam(targetNamespace="", partName="attach1", 
      name="attach1")
    DataHandler attach1, @WebParam(targetNamespace="", partName="attach2", 
      name="attach2")
    DataHandler attach2);

  @WebMethod
  @Action(input="http://SwaTestService.org/wsdl/SwaTest2/echoNoAttachments/Request", 
    output="http://SwaTestService.org/wsdl/SwaTest2/echoNoAttachments/Response")
  @WebResult(targetNamespace="http://SwaTestService.org/xsd", partName="response", 
    name="OutputResponseString")
  public OutputResponseString echoNoAttachments(@WebParam(targetNamespace="http://SwaTestService.org/xsd", 
      partName="request", name="InputRequestString")
    InputRequestString request);
}
