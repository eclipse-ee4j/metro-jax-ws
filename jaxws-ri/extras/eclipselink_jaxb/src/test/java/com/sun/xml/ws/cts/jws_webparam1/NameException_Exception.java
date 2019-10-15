/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.jws_webparam1;

import javax.xml.ws.WebFault;

import com.sun.xml.ws.cts.jws_webparam1.NameException;


@WebFault(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
  faultBean="oracle.j2ee.ws.jaxws.cts.jws_webparam1.NameException", name="NameException")
public class NameException_Exception
  extends Exception
{
  private NameException faultInfo;

  public NameException_Exception(String message, NameException faultInfo)
  {
    super(message);
    this.faultInfo = faultInfo;
  }

  public NameException_Exception(String message, NameException faultInfo, 
                                 Throwable t)
  {
    super(message,t);
    this.faultInfo = faultInfo;
  }

  public NameException getFaultInfo()
  {
    return faultInfo;
  }

  public void setFaultInfo(NameException faultInfo)
  {
    this.faultInfo = faultInfo;
  }
}
