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
import javax.xml.ws.Response;
import javax.xml.ws.WebServiceException;
import java.util.concurrent.ExecutionException;

/**
 * Created by JAXRPC Development Team
 * <p/>
 * Date: Jul 15, 2004
 * Time: 3:51:11 PM
 */

public class JAXBAsyncHandlerRTEX2 implements javax.xml.ws.AsyncHandler{

       Hello_Type hello;
       VoidTestResponse voidTest;

       public JAXBAsyncHandlerRTEX2(Hello_Type request){
           hello = request;
       }

       public JAXBAsyncHandlerRTEX2(VoidTestResponse request){
           voidTest = request;
       }

       public void handleResponse(Response response) throws WebServiceException {
           try {
           System.out.println("Handling JAXB Response");
           Object obj = response.get();

           if (obj != null) {

               HelloOutput helloResponse = null;
               VoidTestResponse voidTestResponse = null;

               if (obj instanceof HelloOutput) {
                   helloResponse = (HelloOutput) obj;
                   Assert.assertEquals(helloResponse.getExtra(), hello.getExtra());
                   Assert.assertEquals(helloResponse.getArgument(), hello.getArgument());
                   System.out.println("JAXB Assertion passes");
               } else if (obj instanceof VoidTestResponse){
                   voidTestResponse = (VoidTestResponse) obj;
                   Assert.assertEquals(voidTestResponse.getClass(), voidTest.getClass());
                   System.out.println("JAXB Assertion passes");
               } else Assert.fail("Not valid Response");
           }  else throw new WebServiceException("No response. test fails");
           } catch (Exception ex){
               System.out.println("Got Exception " + ex.getMessage());
           }

       }
   }

