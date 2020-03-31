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

/**
 * @author Jitendra Kotamraju
 */
@WebService
public class TestImplBaseBase {

    // This is a WebMethod since legacy computation is set
    // and no @WebMethod's
    public String method1(String str) {
        return str;
    }

    // This is a WebMethod since legacy computation is set
    // and no @WebMethod's
    @Override
    public String toString() {
        return "TestImplBaseBase";
    }
}
