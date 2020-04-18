/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.webmethod.legacy.inheritance.server;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;

/**
 * @author Jitendra Kotamraju
 */
@WebService
public class TestImpl extends TestImplBase {

    @WebMethod
    public String method3(String str) {
        return str;
    }

    // This is not a WebMethod since legacy computation is set
    public String method4(String str) {
        return str;
    }

    // This is not a WebMethod since legacy computation is set
    public String method5(String str) {
        return str;
    }

    @WebMethod(exclude=true)
    public String method6(String str) {
        return str;
    }
}
