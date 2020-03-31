/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.exception_mapping.client;

import junit.framework.TestCase;

import jakarta.xml.ws.Endpoint;

import com.sun.xml.ws.model.RuntimeModelerException;


/**
 * @author Rama Pulavarthi
 */
public class DynamicExceptionMappingTest extends TestCase {

    public void testDynamicExceptionMapping() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new EchoImpl());
        endpoint.publish(address);
        endpoint.stop();
    }

    public void testNegativeDynamicExceptionMapping() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new NegativeEchoImpl());
        try {
            endpoint.publish(address);
            fail("Should have thrown exception");
        } catch (RuntimeModelerException e) {
            assertTrue(e.getMessage().contains("Oneway operation should not throw any checked exceptions"));
        } finally {
            endpoint.stop();
        }


    }


}
