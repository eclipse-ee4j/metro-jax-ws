/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.soap12.bindingtype_xsoap12.server;

import jakarta.jws.*;

import jakarta.xml.ws.Holder;
import jakarta.xml.ws.*;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.http.HTTPBinding;
import jakarta.xml.soap.*;
import jakarta.xml.ws.soap.SOAPFaultException;
import javax.xml.namespace.QName;

@WebService(name="Echo", serviceName="EchoService", targetNamespace="http://echo.org/")
@BindingType(value="http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
public class EchoImpl {
    @WebMethod
    public String echoString(String str) {
        return str;
    }

    @WebMethod
    public long echoLong(long lng) {
        return lng;
    }    
}
