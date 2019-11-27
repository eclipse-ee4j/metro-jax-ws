/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.jws_common;

public class Parameters {

  private String implementationClass;
  private String wsdlUrl;
  private String portOperationName;
  private String serviceUrl;

  public String getImplementationClass() { 
    return implementationClass; 
  }

  public void setImplementationClass(String implClass) { 
    this.implementationClass = implClass;
  }

  public String getWsdlUrl() { 
    return wsdlUrl; 
  }

  public void setWsdlUrl(String wsdlUrl) {
    this.wsdlUrl = wsdlUrl;
  }

  public String getPortOperationName() { 
    return portOperationName;
  }

  public void setPortOperationName(String portOperationName) {
    this.portOperationName = portOperationName;
  }

  public String getServiceUrl() {
    return serviceUrl;
  }

  public void setServiceUrl(String serviceUrl) {
    this.serviceUrl = serviceUrl;
  }

  public String toString() {
    return "Impl Class:" + implementationClass + " WSDL:" + wsdlUrl +
      " Port Operation:" + portOperationName + " Service URL:" + serviceUrl;
  }

}
