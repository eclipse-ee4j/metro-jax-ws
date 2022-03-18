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
 * non static or non final methods are web methods
 *
 * @author Jitendra Kotamraju
 */
@WebService
public class TestImpl {

    @WebMethod
    public String method1(String str) {
        return str;
    }

    // Not a web method
    @WebMethod(exclude=true)
    public static String method2(String str) {
        return str;
    }

    // Not a web method
    @WebMethod(exclude=true)
    public final String method3(String str) {
        return str;
    }

    // Not a web method
    public static String method4(String str) {
        return str;
    }

    // Not a web method
    public final String method5(String str) {
        return str;
    }

    // This is also a WebMethod since declaring class
    // has WebService
    public String method6(String str) {
        return str;
    }

    // Not a web method since it is excluded
    @WebMethod(exclude=true)
    public String method7(String str) {
        return str;
    }
}
