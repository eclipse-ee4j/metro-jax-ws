/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.mbyte.server;

import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import java.util.Date;


@WebService(
name="Hello",
serviceName="Hello\u00EEService",
targetNamespace="http://server.mbyte.fromjava/"
)
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class TestService {

    public String echo(String arg) {
        return arg;
    }
}
