/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.schema_validation.same_tns_777.client;

import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.SOAPFaultException;
import com.sun.xml.ws.developer.SchemaValidationFeature;
import junit.framework.TestCase;

import java.net.URL;
import java.io.InputStream;

/**
 * Schema Validation sample
 *
 * @author Jitendra Kotamraju
 */
public class AddNumbersTest extends TestCase {

    private AddNumbersService service;

    public AddNumbersTest(String name) throws Exception {
        super(name);
    }

    public void setUp() {
        service = new AddNumbersService();
    }

    public void testServerValidationFailure() {
        AddNumbersPortType port = service.getAddNumbersPort ();
        int number1 = 10001;     // more than 4 digits, so doesn't pass validation
        int number2 = 20;
        try {
            port.addNumbers (number1, number2);
            fail("Server Schema Validation didn't work");
        } catch(SOAPFaultException se) {
            // Expected exception - Server side validation failed as expected"
            assertEquals("Client", se.getFault().getFaultCodeAsQName().getLocalPart());
        }
    }

    public void testClientValidationFailure() {

        SchemaValidationFeature feature = new SchemaValidationFeature();
        AddNumbersPortType port = service.getAddNumbersPort(feature);
        int number1 = 10001;     // more than 4 digits, so doesn't pass validation
        int number2 = 20;
        try {
            port.addNumbers (number1, number2);
            fail("Client Schema Validation didn't work");
        } catch(SOAPFaultException se) {
            fail("Client Schema Validation didn't work");
        } catch(WebServiceException se) {
            // Expected exception - Client side validation failed as expected
        }
    }

    public void testClientServerValidationPass() {
        SchemaValidationFeature feature = new SchemaValidationFeature();
        AddNumbersPortType port = service.getAddNumbersPort(feature);
        int number1 = 1000;
        int number2 = 20;
        int result = port.addNumbers (number1, number2);
        // Both Client and Server validation passed.
        assertEquals(1020, result);
    }

}
