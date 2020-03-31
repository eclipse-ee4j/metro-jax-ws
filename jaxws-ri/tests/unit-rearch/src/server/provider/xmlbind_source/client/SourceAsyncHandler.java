/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.xmlbind_source.client;

import jakarta.xml.bind.JAXBContext;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.WebServiceException;
import junit.framework.Assert;

import jakarta.xml.ws.Response;

/**
 * @author WS Development Team
 */
public class SourceAsyncHandler implements jakarta.xml.ws.AsyncHandler {
    JAXBContext jaxbContext;
    
    public SourceAsyncHandler() { 
        try {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        } catch(jakarta.xml.bind.JAXBException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }
    
    public void handleResponse(Response response) {
        System.out.println("Handling async response ...");
        try {
            Object obj = response.get();
            Assert.assertNotNull(obj);
            Assert.assertTrue(obj instanceof Source);
            HelloResponse result = (HelloResponse)jaxbContext.createUnmarshaller().unmarshal(((StreamSource)obj).getInputStream());
            
            Assert.assertTrue("foo".equals(result.getArgument()));
            Assert.assertTrue("bar".equals(result.getExtra()));
            System.out.println(result.getArgument());
            System.out.println(result.getExtra());
        } catch (Exception e) {
            System.out.println("SourceAsyncHandler thru exception " + e.getMessage());
        }
        System.out.println("... done handling the response.");
    }
}    
