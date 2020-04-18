/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.context_wsdl_op.server;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.handler.MessageContext;
import javax.xml.namespace.QName;
import jakarta.annotation.Resource;

/**
 * This tests the WSDL_OPERATION property in MessageContext
 * @author Rama Pulavarthi
 */
@jakarta.jws.HandlerChain(name="",file="handlers.xml")
@WebService(name="Hello", serviceName="HelloService", targetNamespace="urn:test")
public class HelloService_Impl {
    private final QName expected_wsdl_op = new QName("urn:test", "sayHello");
    @Resource
    private WebServiceContext wsc;

    @WebMethod
    public int sayHello(@WebParam(name="x")int x) {
        System.out.println("HelloService_Impl received: " + x);
        QName got_wsdl_op = (QName) (wsc.getMessageContext().get(MessageContext.WSDL_OPERATION));
        //System.out.println(got_wsdl_op);
        if (expected_wsdl_op.equals(got_wsdl_op))
            return x;
        else
            throw new WebServiceException("WSDL Operation property not available in Endpoint Implementation"); 


    }

}
