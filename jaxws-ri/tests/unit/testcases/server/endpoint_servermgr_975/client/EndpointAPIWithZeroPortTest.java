/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint_servermgr_975.client;

import junit.framework.TestCase;

import javax.xml.ws.Endpoint;

/**
 * @author Adam Lee
 */
public class EndpointAPIWithZeroPortTest extends TestCase {

    public EndpointAPIWithZeroPortTest(String name) {
        super(name);
    }

    public void testEndpointWithZeroPort() {
        int port = 0;
        String address = "http://localhost:"+port+"/hello";
        Endpoint endpoint = Endpoint.publish(address, new RpcLitEndpoint());

        doAPItesting(endpoint);
    }

    /**
     * @param endpoint - Already published endpoint
     */
    private void doAPItesting(Endpoint endpoint) {

        // Since it is already published, isPublished should return true
        if (!endpoint.isPublished()) {
            assertTrue(false);
        }

        // Binding shouldn't be null
        assertTrue(endpoint.getBinding() != null);

        // Since it is already published, cannot set metadata
        // Expect IllegalStateException
        try {
            endpoint.setMetadata(null);
            assertTrue(false);
        } catch(IllegalStateException e) {
        }

        // Since it is already published, cannot publish again.
        // Expect IllegalStateException
        try {
            endpoint.publish("http://localhost");
            assertTrue(false);
        } catch(IllegalStateException e) {
        }

        endpoint.stop();

        // Since it is stopped, isPublished should return false
        if (endpoint.isPublished()) {
            assertTrue(false);
        }

        // Since it is stopped, cannot publish again.
        // Expect IllegalStateException
        try {
            endpoint.publish("http://localhost");
            assertTrue(false);
        } catch(IllegalStateException e) {
        }
    }

}

