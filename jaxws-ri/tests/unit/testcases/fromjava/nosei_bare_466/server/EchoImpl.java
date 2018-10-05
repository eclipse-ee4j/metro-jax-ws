/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei_bare_466.server;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.ws.Holder;
import java.util.ArrayList;
import java.util.List;

@WebService(name="Echo", serviceName="EchoService", targetNamespace="http://echo.org/")
@SOAPBinding(parameterStyle=ParameterStyle.BARE)
public class EchoImpl {

    /**
     * Testcase to test generation of default name and partName in wsdl for headers.
     * Issue:466 
     */
    @WebMethod
    public Long echoIn3Header(Integer age, @WebParam(header=true)Long num,
                              @WebParam(header=true)String name) {
        return num+age;
    }
}
