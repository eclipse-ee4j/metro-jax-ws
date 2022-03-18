/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint.client;

import java.io.File;
import java.io.InputStream;
import jakarta.xml.ws.Endpoint;

import testutil.PortAllocator;

import junit.framework.TestCase;

/**
 * @author lingling guo
 */
public class DynamicPublishTest extends TestCase{
	
    //Publish a webservice when data-binding files are generated dynamically.
    public void testDeployFail() throws Exception {
        Endpoint endpoint = Endpoint.create(new GenericWithEnums());
        endpoint.publish("http://localhost:" + PortAllocator.getFreePort() + "/GenericWithEnumsservice.org/GenericWithEnums");        
        endpoint.stop();
    }
}
