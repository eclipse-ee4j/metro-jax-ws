/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.wsdl_patcher_699.server;

import jakarta.jws.WebService;

/**
 * Tests if wsa:Address is patched correctly
 *
 * @author Jitendra Kotamraju
 */
@WebService(endpointInterface = "epr.wsdl_patcher_699.server.Hello")
public class TestEndpointImpl implements Hello {
    public HelloResponse hello(Hello_Type req) {
        HelloResponse resp = new HelloResponse();
        resp.setName("sun");
        return resp;
    }
}
