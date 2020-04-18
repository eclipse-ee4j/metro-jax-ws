/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.schema_inline.server;

import jakarta.jws.WebService;

/**
 * Tests in-lining of generated schema in WSDL
 *
 * @author Jitendra Kotamraju
 */
@WebService(name = "Echo", serviceName = "echoService",
        targetNamespace = "http://echo.org/")
public class EchoImpl {

    public Bar echoBarList(Bar bar) throws WSDLBarException, Fault1 {
        return bar;
    }

}
