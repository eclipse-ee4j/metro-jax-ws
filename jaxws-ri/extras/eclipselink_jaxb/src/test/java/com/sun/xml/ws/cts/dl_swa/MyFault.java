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

import javax.xml.ws.WebFault;

@WebFault(targetNamespace="http://SwaTestService.org/wsdl", faultBean="oracle.j2ee.ws.jaxws.cts.dl_swa.MyFaultType", 
  name="MyFault")
public class MyFault
  extends Exception
{
  private MyFaultType faultInfo;

  public MyFault(String message, MyFaultType faultInfo)
  {
    super(message);
    this.faultInfo = faultInfo;
  }

  public MyFault(String message, MyFaultType faultInfo, Throwable t)
  {
    super(message,t);
    this.faultInfo = faultInfo;
  }

  public MyFaultType getFaultInfo()
  {
    return faultInfo;
  }

  public void setFaultInfo(MyFaultType faultInfo)
  {
    this.faultInfo = faultInfo;
  }
}
