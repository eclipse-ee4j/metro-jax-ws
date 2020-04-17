/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.webmethod.legacy.withWebMethod.server;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

/**
 * User: Iaroslav Savytskyi
 * Date: 01.12.11
 */
@WebService
public class TestImpl {

    @WebMethod
    public String method1(String str) {
        return str;
    }

    // This is not a WebMethod since legacy is set to "true"
    public String method2(String str) {
        return str;
    }

    @WebMethod
    @Override
    public String toString() {
        return "str2";
    }
}
