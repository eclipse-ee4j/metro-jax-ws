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

import java.net.URL;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;

@WebService(
    portName="SwaTestOnePort",
    serviceName="WSIDLSwaTestService",
    targetNamespace="http://SwaTestService.org/wsdl",
//    wsdlLocation="WEB-INF/wsdl/SwaTestService.wsdl",
    endpointInterface="com.sun.xml.ws.cts.dl_swa.SwaTest1"
)

public class SwaTestImpl1 implements SwaTest1 {
    public void getMultipleAttachments(InputRequestGet request, Holder<OutputResponse> response, Holder<DataHandler> attach1, Holder<DataHandler> attach2)  {
	try {
	    System.out.println("Enter getMultipleAttachments() ......");
	    OutputResponse theResponse = new OutputResponse();
	    theResponse.setMimeType1(request.getMimeType1());
	    theResponse.setMimeType2(request.getMimeType2());
	    theResponse.setResult("ok");
	    theResponse.setReason("ok");
	    response.value = theResponse;
	    DataHandler dh1 = new DataHandler(new URL(request.getUrl1()));
	    DataHandler dh2 = new DataHandler(new URL(request.getUrl2()));
	    attach1.value = dh1;
	    attach2.value = dh2;
	    System.out.println("Leave getMultipleAttachments() ......");
	} catch (Exception e) {
	    throw new WebServiceException(e.getMessage());
	}
    }

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

    public OutputResponse echoMultipleAttachments(InputRequest request, Holder<DataHandler> attach1, Holder<DataHandler> attach2)  {
	try {
	    System.out.println("Enter echoMultipleAttachments() ......");
	    OutputResponse theResponse = new OutputResponse();
	    theResponse.setMimeType1(request.getMimeType1());
	    theResponse.setMimeType2(request.getMimeType2());
	    theResponse.setResult("ok");
	    theResponse.setReason("ok");
	    if(attach1 == null || attach1.value == null) {
		System.err.println("attach1.value is null (unexpected)");
		theResponse.setReason("attach1.value is null (unexpected)");
		theResponse.setResult("not ok");
	    }
	    if(attach2 == null || attach2.value == null) {
		System.err.println("attach2.value is null (unexpected)");
		if(theResponse.getReason().equals("ok"))
		    theResponse.setReason("attach2.value is null (unexpected)");
		else
		    theResponse.setReason(theResponse.getReason() +
			"\nattach2.value is null (unexpected)");
		theResponse.setResult("not ok");
	    }
	    System.out.println("Leave echoMultipleAttachments() ......");
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

    public OutputResponseAll echoAllAttachmentTypes(VoidRequest request, Holder<DataHandler> attach1, Holder<DataHandler> attach2, Holder<javax.xml.transform.Source> attach3, Holder<java.awt.Image> attach4, Holder<java.awt.Image> attach5)  {
	try {
	    System.out.println("Enter echoAllAttachmentTypes() ......");
	    OutputResponseAll theResponse = new OutputResponseAll();
	    theResponse.setResult("ok");
	    theResponse.setReason("ok");
	    if(attach1 == null || attach1.value == null) {
		System.err.println("attach1.value is null (unexpected)");
		theResponse.setReason("attach1.value is null (unexpected)");
		theResponse.setResult("not ok");
	    }
	    if(attach2 == null || attach2.value == null) {
		System.err.println("attach2.value is null (unexpected)");
		if(theResponse.getReason().equals("ok"))
		    theResponse.setReason("attach2.value is null (unexpected)");
		else
		    theResponse.setReason(theResponse.getReason() +
			"\nattach2.value is null (unexpected)");
		theResponse.setResult("not ok");
	    }
	    if(attach3 == null || attach3.value == null) {
		System.err.println("attach3.value is null (unexpected)");
		if(theResponse.getReason().equals("ok"))
		    theResponse.setReason("attach3.value is null (unexpected)");
		else
		    theResponse.setReason(theResponse.getReason() +
			"\nattach3.value is null (unexpected)");
		theResponse.setResult("not ok");
	    }
	    if(attach4 == null || attach4.value == null) {
		System.err.println("attach4.value is null (unexpected)");
		if(theResponse.getReason().equals("ok"))
		    theResponse.setReason("attach4.value is null (unexpected)");
		else
		    theResponse.setReason(theResponse.getReason() +
			"\nattach4.value is null (unexpected)");
		theResponse.setResult("not ok");
	    }
	    if(attach5 == null || attach5.value == null) {
		System.err.println("attach5.value is null (unexpected)");
		if(theResponse.getReason().equals("ok"))
		    theResponse.setReason("attach5.value is null (unexpected)");
		else
		    theResponse.setReason(theResponse.getReason() +
			"\nattach5.value is null (unexpected)");
		theResponse.setResult("not ok");
	    }
	    System.out.println("Leave echoAllAttachmentTypes() ......");
	    return theResponse;
	} catch (Exception e) {
	    throw new WebServiceException(e.getMessage());
	}
    }

    public OutputResponse echoAttachmentsAndThrowAFault(InputRequestThrowAFault request, Holder<DataHandler> attach1, Holder<DataHandler> attach2)  throws MyFault {
	System.out.println("Enter echoAttachmentsAndThrowAFault() ......");
	System.out.println("Throwing back a fault [MyFault] ......");
	throw new MyFault("This is my fault", new MyFaultType());
    }

    public OutputResponse echoAttachmentsWithHeader(InputRequestWithHeader request, MyHeader header, Holder<DataHandler> attach1, Holder<DataHandler> attach2) throws MyFault {
	System.out.println("Enter echoAttachmentsWithHeader() ......");
	if(header.getMessage().equals("do throw a fault")) {
	    System.out.println("Throwing back a fault [MyFault] ......");
	    throw new MyFault("This is my fault", new MyFaultType());
	}
	try {
	    OutputResponse theResponse = new OutputResponse();
	    theResponse.setMimeType1(request.getMimeType1());
	    theResponse.setMimeType2(request.getMimeType2());
	    theResponse.setResult("ok");
	    theResponse.setReason("ok");
	    System.out.println("Leave echoAttachmentsWithHeader() ......");
	    return theResponse;
	} catch (Exception e) {
	    throw new WebServiceException(e.getMessage());
	}
    }
    
    

    public void echoData( String body, Holder<byte[]> data){
    }
}
