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
import javax.xml.transform.Source;
import jakarta.xml.ws.Response;
import jakarta.xml.ws.WebServiceException;

/**
 * Created by JAXRPC Development Team
 * <p/>
 * Date: Jul 15, 2004
 * Time: 3:38:43 PM
 */
 public class XMLAsyncHandlerRTEX implements jakarta.xml.ws.AsyncHandler{

        Source source;

        public XMLAsyncHandlerRTEX(Source source){
            this.source = source;
        }

        public void handleResponse(Response response) throws WebServiceException{
            try {
            System.out.println("Handling response xmlHandler");
            Object obj = response.get();
            if (obj != null){

                Assert.assertEquals(obj.getClass(),source.getClass());
                System.out.println("XML Assertion passes");
            }
            } catch (Exception e){
                throw new WebServiceException(e);
            }
        }
    }

