/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsimport.extension_jms_transport.server;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Rama Pulavarthi
 */
@WebService(endpointInterface = "wsimport.extension_jms_transport.server.EchoServicePortType")
public class EchoImpl implements wsimport.extension_jms_transport.server.EchoServicePortType{
    @WebMethod(action = "echoOperation")
    @WebResult(name = "echoStringResponse", targetNamespace = "http://wssample/sei/echo/", partName = "parameter")
    public EchoStringResponse echoOperation(@WebParam(name = "echoStringInput", targetNamespace = "http://wssample/sei/echo/", partName = "parameter")
    EchoStringInput parameter) {
        //dummy impl
        return null;  
    }
}
