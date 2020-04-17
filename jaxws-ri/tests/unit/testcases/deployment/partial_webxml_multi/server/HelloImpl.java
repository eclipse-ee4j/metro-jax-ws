/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package deployment.partial_webxml_multi.server;

import jakarta.jws.WebService;

/**
 * @author Rama Pulavarthi
 */
@WebService(targetNamespace = "http://com.example.hello", portName = "EchoPort")
public class HelloImpl {
    public String hello(String str) {
        return "Hello " +str;    
    }
}
