/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.xmlelement.server;

import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;

@WebService(name="Echo", serviceName="echoService", targetNamespace="http://echo.org/")
public class EchoImpl {

    @XmlElement(required=true)
    public int echoInt(@XmlElement(nillable=true) int a) {
        return a;
    }

    @XmlElement(required=true)
    public String echoString(@XmlElement(nillable=true) String a) {
        return a;
    }

    @XmlElement(nillable=true)
    public Integer echoInteger(@XmlElement(required=true) Integer a) {
        return a;
    }

    @XmlElement(name="result")
    public String echoName(@XmlElement(name="input") String a) {
        return a;
    }

    @WebResult(name="result")
    @XmlElement(name="result")
    public String echoWebParamName(
            @WebParam(name="input")
            @XmlElement(name="input") String a) {
        return a;
    }
      
}
