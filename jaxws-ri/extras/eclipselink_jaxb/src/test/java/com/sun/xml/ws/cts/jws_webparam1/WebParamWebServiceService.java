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

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;

import com.sun.xml.ws.cts.jws_webparam1.WebParamWebService;


@WebServiceClient(wsdlLocation="file:/D:/oc4j/webservices/devtest/data/cts15/WebParamWebServiceService.wsdl", 
  targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
  name="webParamWebServiceService")
public class WebParamWebServiceService
  extends Service
{
  private static URL wsdlLocationURL;
  static {
    try
    {
      wsdlLocationURL = 
          new URL("file:/D:/oc4j/webservices/devtest/data/cts15/WebParamWebServiceService.wsdl");
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
  }

  public WebParamWebServiceService()
  {
    super(wsdlLocationURL, 
          new QName("http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
                    "webParamWebServiceService"));
  }

  public WebParamWebServiceService(URL wsdlLocation, QName serviceName)
  {
    super(wsdlLocation, serviceName);
  }

  @WebEndpoint(name="webParamWebServicePort")
  public WebParamWebService getWebParamWebServicePort()
  {
    return (WebParamWebService) super.getPort(new QName("http://server.webparam1.webparam.jws.tests.ts.sun.com/", 
                                                        "webParamWebServicePort"), 
                                              WebParamWebService.class);
  }
}
