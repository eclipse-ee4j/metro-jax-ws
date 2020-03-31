/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.fromjava_epr_extensions.server;

import jakarta.jws.WebService;
import javax.annotation.Resource;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.wsaddressing.W3CEndpointReference;

/**
 * @author Rama Pulavarthi
 */
@WebService(serviceName = "HelloService",portName = "HelloPort",name = "Hello",targetNamespace = "http://helloservice.org/wsdl")
public class HelloImpl{
    @Resource
    WebServiceContext wsContext;

    public W3CEndpointReference getW3CEPR() {
        return (W3CEndpointReference) wsContext.getEndpointReference();
    }
}
