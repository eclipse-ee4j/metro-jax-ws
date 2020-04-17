/*
 * Copyright (c) 2003, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.marshalltest.server;


import jakarta.xml.ws.WebServiceException;
import jakarta.xml.soap.Name;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPException;

import jakarta.jws.WebService;

@WebService(
    serviceName="MarshallTestService",
    endpointInterface="client.marshalltest.server.CompoundTest"
)
public class CompoundTestImpl implements CompoundTest {
    public EchoEmployeeResponse echoEmployee(EchoEmployeeRequest employee) 
						 {
        EchoEmployeeResponse employeeResp = new EchoEmployeeResponse();
        employeeResp.setEmployee(employee.getEmployee());
        return employeeResp;
    }

    public EchoPersonResponse echoPerson(EchoPersonRequest person) 
						 {
        EchoPersonResponse personResp = new EchoPersonResponse();
        personResp.setPerson(person.getPerson());
        return personResp;
    }
	
    public Document echoDocument(Document document)  {
	return document;
    }
}
