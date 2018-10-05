/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package schema_validation.client;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import com.sun.xml.ws.developer.SchemaValidationFeature;

/**
 * Schema Validation sample
 *
 * @author Jitendra Kotamraju
 */

public class AddNumbersClient {

    public static void main (String[] args) {
        AddNumbersService service = new AddNumbersService();
        testServerValidationFailure(service);
        testClientValidationFailure(service);
        testClientServerValidationPass(service);
    }

    public static void testServerValidationFailure(AddNumbersService service) {
        AddNumbersPortType port = service.getAddNumbersPort ();
        int number1 = 10001;     // more than 4 digits, so doesn't pass validation
        int number2 = 20;
        try {
            int result = port.addNumbers (number1, number2);
            throw new RuntimeException("Server Schema Validation didn't work");
        } catch(SOAPFaultException se) {
            System.out.println("Success: Server side validation failed as expected");
        }
    }

    private static void testClientValidationFailure(AddNumbersService service) {

        SchemaValidationFeature feature = new SchemaValidationFeature();
        AddNumbersPortType port = service.getAddNumbersPort(feature);
        int number1 = 10001;     // more than 4 digits, so doesn't pass validation
        int number2 = 20;
        try {
            int result = port.addNumbers (number1, number2);
            throw new RuntimeException("Client Schema Validation didn't work");
        } catch(SOAPFaultException se) {
            throw new RuntimeException("Client Schema Validation didn't work");
        } catch(WebServiceException se) {
            System.out.println("Success: Client side validation failed as expected");
        }
    }

    private static void testClientServerValidationPass(AddNumbersService service) {
        SchemaValidationFeature feature = new SchemaValidationFeature();
        AddNumbersPortType port = service.getAddNumbersPort(feature);
        int number1 = 1000;
        int number2 = 20;
        int result = port.addNumbers (number1, number2);
        System.out.println("Success: Both Client and Server validation passed.");
    }

}
