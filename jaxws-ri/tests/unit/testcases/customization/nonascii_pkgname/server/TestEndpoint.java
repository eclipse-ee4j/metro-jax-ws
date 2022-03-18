/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package customization.nonascii_pkgname.server;

import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;

/**
 * @author Rama Pulavarthi
 */
@WebService(endpointInterface = "customization.nonascii_pkgname.server.Hello"
)
public class TestEndpoint {
    public HelloOutput sayHello(Hello_Type hello) {
        HelloOutput out = new HelloOutput();
        out.setArgument(hello.getArgument()+" World!");
        out.setExtra(hello.getExtra()+" Fine!");
        return out;
    }
    
}
