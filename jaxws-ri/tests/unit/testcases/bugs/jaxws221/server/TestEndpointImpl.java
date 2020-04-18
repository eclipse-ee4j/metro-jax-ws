/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws221.server;

import jakarta.jws.WebService;

@WebService(endpointInterface="bugs.jaxws221.server.TestEndpoint")
public class TestEndpointImpl {
    public void oneWayMethod(String parameter){

    }
}
