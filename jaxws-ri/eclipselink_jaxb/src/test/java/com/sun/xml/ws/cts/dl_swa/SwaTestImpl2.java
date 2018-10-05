/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * @(#)SwaTestImpl2.java	1.10 06/03/25
 */
package com.sun.xml.ws.cts.dl_swa;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

@WebService(
    portName="SwaTestTwoPort",
    serviceName="WSIDLSwaTestService",
    targetNamespace="http://SwaTestService.org/wsdl",
    wsdlLocation="WEB-INF/wsdl/SwaTestService.wsdl",
    endpointInterface="com.sun.xml.ws.cts.dl_swa.SwaTest2"
)

public class SwaTestImpl2 implements SwaTest2 {
    public OutputResponseString putMultipleAttachments(InputRequestPut request, DataHandler attach1, DataHandler attach2)  {
	try {
	    OutputResponseString theResponse = new OutputResponseString();
 	    theResponse.setMyString("ok");
	    System.out.println("Enter putMultipleAttachments() ......");
	    if(attach1 == null) {
		System.err.println("attach1 is null (unexpected)");
		theResponse.setMyString("not ok");
	    }
	    if(attach2 == null) {
		System.err.println("attach2 is null (unexpected)");
		theResponse.setMyString("not ok");
	    }
	    System.out.println("Leave putMultipleAttachments() ......");
	    return theResponse;
	} catch (Exception e) {
	    throw new WebServiceException(e.getMessage());
	}
    }

    public OutputResponseString echoNoAttachments(InputRequestString request)  {
	try {
	    System.out.println("Enter echoNoAttachments() ......");
	    OutputResponseString theResponse = new OutputResponseString();
 	    theResponse.setMyString(request.getMyString());
	    System.out.println("Leave echoNoAttachments() ......");
	    return theResponse;
	} catch (Exception e) {
	    throw new WebServiceException(e.getMessage());
	}
    }
}
