/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.w3c.fromwsdl.crinterop_s11.server;

import jakarta.jws.WebService;
import jakarta.xml.ws.soap.SOAPFaultException;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.soap.SOAPException;
import javax.xml.namespace.QName;

import com.sun.xml.ws.api.SOAPVersion;

@WebService(endpointInterface="wsa.w3c.fromwsdl.crinterop_s11.server.WsaTestPortType")
public class WsaTestImpl implements WsaTestPortType {

    private final String NAME = getClass().getName();

    public void notify(String param) {
        System.out.println(NAME + ".notify: " + param);
    }

    public String echo(String param) {
        System.out.println(NAME + ".echo: " + param);

        String message = null;

        if (param.equals(""))
            message = "The echo string was empty";

       if (param.startsWith("fault"))
           message = "\"" + param + "\" triggered the fault";

        if (message != null)
            throw new SOAPFaultException(createSOAPFault(message));

        return param;
    }

    SOAPFault createSOAPFault(String message) {
        try {
            SOAPFault fault = SOAPVersion.SOAP_11.getSOAPFactory().createFault();
            fault.setFaultString(message);
            fault.setFaultCode(new QName("http://example.org/echo", "EmptyEchoString"));

            return fault;
        } catch (SOAPException ex) {
            throw new WebServiceException(ex);
        }
    }
}
