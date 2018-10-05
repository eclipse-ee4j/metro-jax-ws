/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.epr_spec.server;

import javax.jws.WebService;

@WebService(endpointInterface = "epr.epr_spec.server.Hello")
public class HelloImpl implements Hello {
    public HelloResponse hello( HelloRequest parameters) {
            //dummy impl
             return null;
    }

}
