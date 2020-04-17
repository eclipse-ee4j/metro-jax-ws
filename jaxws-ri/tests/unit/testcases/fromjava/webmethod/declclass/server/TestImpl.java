/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.webmethod.declclass.server;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;

/**
 * @author Jitendra Kotamraju
 */
@WebService
public class TestImpl {

    @WebMethod
    public String method1(String str) {
        return str;
    }

    // This is also a WebMethod since declaring class
    // has WebService
    public String method2(String str) {
        return str;
    }
}
