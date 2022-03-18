/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.wsdl_hello_lit_asynchandler.client;

import junit.framework.Assert;

import jakarta.xml.ws.Response;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.SOAPFaultException;
import java.util.concurrent.ExecutionException;
import junit.framework.TestCase;

/**
 * Created by JAXRPC Development Team
 * <p/>
 * Date: Jul 15, 2004
 * Time: 3:51:11 PM
 */

public class JAXBAsyncHandlerRTEX implements jakarta.xml.ws.AsyncHandler {

    Hello_Type hello;
    VoidTestResponse voidTest;

    public JAXBAsyncHandlerRTEX(Hello_Type request) {
        hello = request;
    }

    public JAXBAsyncHandlerRTEX(VoidTestResponse request) {
        voidTest = request;
    }

    public void handleResponse(Response response) throws WebServiceException {
        System.out.println("Handling JAXB Response");
        try {
            Object obj = response.get();
            if (obj != null) {

                System.out.println("obj is " + obj.getClass().getName());
                HelloOutput helloResponse = null;
                VoidTestResponse voidTestResponse = null;

                if (obj instanceof HelloOutput) {
                    helloResponse = (HelloOutput) obj;
                    Assert.assertEquals(helloResponse.getExtra(), hello.getExtra());
                    Assert.assertEquals(helloResponse.getArgument(), hello.getArgument());
                    System.out.println(" testHelloResponseBadHelloSrcJAXB FAILED");
                } else if (obj instanceof VoidTestResponse){
                    voidTestResponse = (VoidTestResponse) obj;
                    Assert.assertEquals(voidTestResponse.getClass(), voidTest.getClass());
                    System.out.println(" testHelloResponseBadVoidSrcJAXB FAILED");

                } else {
					    Assert.fail("response is not valid");
					 }
            } else Assert.fail("obj is null");

        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof ExecutionException);
            //Assert.assertTrue(ex.getCause() instanceof SOAPFaultException);


            System.out.println("SOAPFaultExceptionTestPassed ");
            return;

        }

    }

}

