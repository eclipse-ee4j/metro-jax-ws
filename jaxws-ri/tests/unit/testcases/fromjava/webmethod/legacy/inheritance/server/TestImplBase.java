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

import jakarta.jws.WebMethod;

/**
 * @author Jitendra Kotamraju
 */
public class TestImplBase extends TestImplBaseBase {

    // Not a web method
    public String method2(String str) {
        return str;
    }

    @WebMethod(exclude=true)
    public String method5(String str) {
        return str;
    }

    @WebMethod
    public String method6(String str) {
        return str;
    }

    @WebMethod
    public String method7(String str) {
        return str;
    }
}
