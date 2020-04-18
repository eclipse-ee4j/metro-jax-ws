/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.webmethod.privateValidation.server;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.rmi.Remote;

/**
 * 'non public' methods are not web methods and aren't validated
 */
@WebService
public class TestImpl {

    @WebMethod
    public String method1(String str) {
        return str;
    }

    // Not a web method
    private Remote method2() {
        return null;
    }

    // Not a web method
    Remote method3() {
        return null;
    }

    // Not a web method
    protected Remote method4() {
        return null;
    }
}
