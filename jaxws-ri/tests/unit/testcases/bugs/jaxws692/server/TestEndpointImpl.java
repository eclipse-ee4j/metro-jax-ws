/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws692.server;

import jakarta.jws.WebService;

/**
 * @author Martin Grebac
 */
@WebService(endpointInterface="bugs.jaxws692.server.ServicePort")
public class TestEndpointImpl implements ServicePort {
    public OperationResponse operation(OperationRequest parameters) {
        OperationResponse resp = new OperationResponse();
        if (parameters.getRequest().equals("request111")) {
            resp.setResponse("response111");
        }
        return resp;
    }

}
