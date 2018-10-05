/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.single_handlertube.server;

import static handler.single_handlertube.common.TestConstants.*;

import javax.xml.bind.JAXBException;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * @author Rama Pulavarthi
 */
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@javax.jws.WebService(serviceName = "HelloService12", portName="HelloPort12", targetNamespace="urn:test", endpointInterface="handler.single_handlertube.server.Hello12")
public class HelloService12_Impl implements Hello12 {
    
    public int hello12(int x) {
        System.out.println("Hello12Service_Impl received: " + x);
        if(x == SERVER_THROW_RUNTIME_EXCEPTION) {
            throw new RuntimeException(" Throwing RuntimeException as expected");
        }
        return x;
    }
    
}
