/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.dl_swa;

import java.awt.Image;

import jakarta.activation.DataHandler;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebParam.Mode;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;

import javax.xml.transform.Source;
import jakarta.xml.ws.Action;
import jakarta.xml.ws.FaultAction;
import jakarta.xml.ws.Holder;

@WebService(targetNamespace="http://SwaTestService.org/wsdl", name="SwaTest1")
@SOAPBinding(parameterStyle=ParameterStyle.BARE, style=Style.DOCUMENT)
public interface SwaTest1
{
  @WebMethod
  @Action(input="http://SwaTestService.org/wsdl/SwaTest1/getMultipleAttachments/Request", 
    output="http://SwaTestService.org/wsdl/SwaTest1/getMultipleAttachments/Response")
  public void getMultipleAttachments(@WebParam(targetNamespace="http://SwaTestService.org/xsd", 
      partName="request", name="InputRequestGet")
    InputRequestGet request, @WebParam(targetNamespace="http://SwaTestService.org/xsd", 
      mode=Mode.OUT, partName="response", name="OutputResponse")
    Holder<OutputResponse> response, @WebParam(targetNamespace="", mode=Mode.OUT, 
      partName="attach1", name="attach1")
    Holder<DataHandler> attach1, @WebParam(targetNamespace="", mode=Mode.OUT, 
      partName="attach2", name="attach2")
    Holder<DataHandler> attach2);

  @WebMethod
  @Action(input="http://SwaTestService.org/wsdl/SwaTest1/putMultipleAttachments/Request", 
    output="http://SwaTestService.org/wsdl/SwaTest1/putMultipleAttachments/Response")
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
  @Action(input="http://SwaTestService.org/wsdl/SwaTest1/echoMultipleAttachments/Request", 
    output="http://SwaTestService.org/wsdl/SwaTest1/echoMultipleAttachments/Response")
  @WebResult(targetNamespace="http://SwaTestService.org/xsd", partName="response", 
    name="OutputResponse")
  public OutputResponse echoMultipleAttachments(@WebParam(targetNamespace="http://SwaTestService.org/xsd", 
      partName="request", name="InputRequest")
    InputRequest request, @WebParam(targetNamespace="", mode=Mode.INOUT, 
      partName="attach1", name="attach1")
    Holder<DataHandler> attach1, @WebParam(targetNamespace="", mode=Mode.INOUT, 
      partName="attach2", name="attach2")
    Holder<DataHandler> attach2);

  @WebMethod
  @Action(input="http://SwaTestService.org/wsdl/SwaTest1/echoNoAttachments/Request", 
    output="http://SwaTestService.org/wsdl/SwaTest1/echoNoAttachments/Response")
  @WebResult(targetNamespace="http://SwaTestService.org/xsd", partName="response", 
    name="OutputResponseString")
  public OutputResponseString echoNoAttachments(@WebParam(targetNamespace="http://SwaTestService.org/xsd", 
      partName="request", name="InputRequestString")
    InputRequestString request);

  @WebMethod
  @Action(input="http://SwaTestService.org/wsdl/SwaTest1/echoAllAttachmentTypes/Request", 
    output="http://SwaTestService.org/wsdl/SwaTest1/echoAllAttachmentTypes/Response")
  @WebResult(targetNamespace="http://SwaTestService.org/xsd", partName="response", 
    name="OutputResponseAll")
  public OutputResponseAll echoAllAttachmentTypes(@WebParam(targetNamespace="http://SwaTestService.org/xsd", 
      partName="request", name="VoidRequest")
    VoidRequest request, @WebParam(targetNamespace="", mode=Mode.INOUT, 
      partName="attach1", name="attach1")
    Holder<DataHandler> attach1, @WebParam(targetNamespace="", mode=Mode.INOUT, 
      partName="attach2", name="attach2")
    Holder<DataHandler> attach2, @WebParam(targetNamespace="", mode=Mode.INOUT, 
      partName="attach3", name="attach3")
    Holder<Source> attach3, @WebParam(targetNamespace="", mode=Mode.INOUT, 
      partName="attach4", name="attach4")
    Holder<Image> attach4, @WebParam(targetNamespace="", mode=Mode.INOUT, 
      partName="attach5", name="attach5")
    Holder<Image> attach5);

  @WebMethod
  @Action(input="http://SwaTestService.org/wsdl/SwaTest1/echoAttachmentsAndThrowAFault/Request", fault =
      { @FaultAction(value="http://SwaTestService.org/wsdl/SwaTest1/echoAttachmentsAndThrowAFault/Fault/MyFault", 
          className = MyFault.class)
        } , output="http://SwaTestService.org/wsdl/SwaTest1/echoAttachmentsAndThrowAFault/Response")
  @WebResult(targetNamespace="http://SwaTestService.org/xsd", partName="response", 
    name="OutputResponse")
  public OutputResponse echoAttachmentsAndThrowAFault(@WebParam(targetNamespace="http://SwaTestService.org/xsd", 
      partName="request", name="InputRequestThrowAFault")
    InputRequestThrowAFault request, @WebParam(targetNamespace="", mode=Mode.INOUT, 
      partName="attach1", name="attach1")
    Holder<DataHandler> attach1, @WebParam(targetNamespace="", mode=Mode.INOUT, 
      partName="attach2", name="attach2")
    Holder<DataHandler> attach2)
    throws MyFault;

  @WebMethod
  @Action(input="http://SwaTestService.org/wsdl/SwaTest1/echoAttachmentsWithHeader/Request", fault =
      { @FaultAction(value="http://SwaTestService.org/wsdl/SwaTest1/echoAttachmentsWithHeader/Fault/MyFault", 
          className = MyFault.class)
        } , output="http://SwaTestService.org/wsdl/SwaTest1/echoAttachmentsWithHeader/Response")
  @WebResult(targetNamespace="http://SwaTestService.org/xsd", partName="response", 
    name="OutputResponse")
  public OutputResponse echoAttachmentsWithHeader(@WebParam(targetNamespace="http://SwaTestService.org/xsd", 
      partName="request", name="InputRequestWithHeader")
    InputRequestWithHeader request, @WebParam(targetNamespace="http://SwaTestService.org/xsd", 
      header=true, partName="header", name="MyHeader")
    MyHeader header, @WebParam(targetNamespace="", mode=Mode.INOUT, 
      partName="attach1", name="attach1")
    Holder<DataHandler> attach1, @WebParam(targetNamespace="", mode=Mode.INOUT, 
      partName="attach2", name="attach2")
    Holder<DataHandler> attach2)
    throws MyFault;
  

  /**
   * EnableMIMEContent = false.
   */
  @WebMethod
  @Action(input="http://example.org/mime/Hello/echoData/Request", output="http://example.org/mime/Hello/echoData/Response")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  public void echoData(@WebParam(targetNamespace="http://example.org/mime/data", 
      partName="body", name="body")
    String body, @WebParam(targetNamespace="", mode=Mode.INOUT, partName="data", 
      name="data")
    Holder<byte[]> data);
}
