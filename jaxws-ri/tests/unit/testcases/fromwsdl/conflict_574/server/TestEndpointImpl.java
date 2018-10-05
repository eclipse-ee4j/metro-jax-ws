/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.conflict_574.server;

import java.util.List;

/**
 * @author Jitendra Kotamraju
 */
@javax.jws.WebService(endpointInterface = "fromwsdl.conflict_574.server.HelloImpl")
public class TestEndpointImpl implements HelloImpl {
    public HelloResponse hello(Hello req) {
        HelloResponse resp = new HelloResponse();
        resp.setName("vivek");
        resp.setArgument(req.getArgument());
        resp.setExtra(req.getExtra());
        return resp;
    }
}
