/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint_servermgr_941.client;

import junit.framework.TestCase;
import testutil.PortAllocator;

import javax.xml.ws.Endpoint;

/**
 * @author Iaroslav Savytskyi
 */
public class EndpointAPIWithZeroIpTest extends TestCase {

    public EndpointAPIWithZeroIpTest(String name) {
        super(name);
    }

    public void testEndpointWithZeroIp() {
        int port = PortAllocator.getFreePort();
        String address = "http://0.0.0.0:" + port + "/hello";
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
        } catch (IllegalStateException e) {
        }

        // Since it is already published, cannot publish again.
        // Expect IllegalStateException
        try {
            endpoint.publish("http://0.0.0.0");
            assertTrue(false);
        } catch (IllegalStateException e) {
        }

        endpoint.stop();

        // Since it is stopped, isPublished should return false
        if (endpoint.isPublished()) {
            assertTrue(false);
        }

        // Since it is stopped, cannot publish again.
        // Expect IllegalStateException
        try {
            endpoint.publish("http://0.0.0.0");
            assertTrue(false);
        } catch (IllegalStateException e) {
        }
    }
}

