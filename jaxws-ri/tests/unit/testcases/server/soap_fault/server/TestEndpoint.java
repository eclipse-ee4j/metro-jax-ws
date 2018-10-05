/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.soap_fault.server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPException;
import javax.xml.namespace.QName;

/**
 * @author Rama Pulavarthi
 */
@WebService(portName = "TestEndpointPort", serviceName = "TestEndpointService")
@BindingType("http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
public class TestEndpoint {
    @WebMethod()
    public String echo(String s) {
        if (s.contains("fault"))
            throw createSFE();
        return s;

    }

    private SOAPFaultException createSFE() {
        try {
            SOAPFactory fac = SOAPFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
            SOAPFault sf = fac.createFault("Some error message.", new QName("http://www.w3.org/2003/05/soap-envelope", "Receiver"));
            sf.setFaultNode("http://testNode");
            return new SOAPFaultException(sf);            
        } catch (SOAPException e) {
            throw new WebServiceException(e);
        }
    }
}
