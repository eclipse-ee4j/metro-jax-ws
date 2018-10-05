/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.crinterop.s11.server;

import javax.jws.WebService;
import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.ws.WebServiceException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPException;
import javax.xml.namespace.QName;

import com.sun.xml.ws.api.SOAPVersion;

/**
 * @author Arun Gupta
 */
@WebService(endpointInterface="wsa.fromwsdl.crinterop.s11.server.WsaTestPortType")
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
            SOAPFault fault = SOAPVersion.SOAP_11.saajSoapFactory.createFault();
            fault.setFaultString(message);
            fault.setFaultCode(new QName("http://example.org/echo", "EmptyEchoString"));

            return fault;
        } catch (SOAPException ex) {
            throw new WebServiceException(ex);
        }
    }
}
