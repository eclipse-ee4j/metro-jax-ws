/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.client_from_sei.server;

import javax.jws.WebService;

/**
 * @author Jitendra Kotamraju
 */
@WebService(endpointInterface="fromwsdl.client_from_sei.server.Hello")
public class TestEndpointImpl implements Hello {
    public HelloResponse hello(Hello_Type req) {
        HelloResponse resp = new HelloResponse();
        resp.setResult("result");
        return resp;
    }
}
