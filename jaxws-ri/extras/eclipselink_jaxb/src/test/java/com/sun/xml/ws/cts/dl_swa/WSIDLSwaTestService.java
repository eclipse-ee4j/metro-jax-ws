/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.dl_swa;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;


@WebServiceClient(wsdlLocation="file:/D:/oc4j/webservices/devtest/data/cts15/SwaTestService.wsdl", 
  targetNamespace="http://SwaTestService.org/wsdl", name="WSIDLSwaTestService")
public class WSIDLSwaTestService
  extends Service
{
  private static URL wsdlLocationURL;
  static {
    try
    {
      wsdlLocationURL = 
          new URL("file:/D:/oc4j/webservices/devtest/data/cts15/SwaTestService.wsdl");
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
  }

  public WSIDLSwaTestService()
  {
    super(wsdlLocationURL, 
          new QName("http://SwaTestService.org/wsdl", "WSIDLSwaTestService"));
  }

  public WSIDLSwaTestService(URL wsdlLocation, QName serviceName)
  {
    super(wsdlLocation, serviceName);
  }

  @WebEndpoint(name="SwaTestOnePort")
  public SwaTest1 getSwaTestOnePort()
  {
    return super.getPort(new QName("http://SwaTestService.org/wsdl",
                                              "SwaTestOnePort"), 
                                    SwaTest1.class);
  }

  @WebEndpoint(name="SwaTestTwoPort")
  public SwaTest2 getSwaTestTwoPort()
  {
    return super.getPort(new QName("http://SwaTestService.org/wsdl",
                                              "SwaTestTwoPort"), 
                                    SwaTest2.class);
  }
}
