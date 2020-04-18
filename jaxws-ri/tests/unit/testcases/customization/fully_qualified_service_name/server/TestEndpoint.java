/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package customization.fully_qualified_service_name.server;

import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;

/**
 * Exception class name customized with fully qualified class name
 *
 * @author Jitendra Kotamraju
 */
@WebService(serviceName="CustomService", endpointInterface="customization.fully_qualified_service_name.server.Hello")
public class TestEndpoint {

    public String foo(String fooChild1, String fooChild2) throws FooFaultMessage {
        return fooChild1+fooChild2;
    }

}
