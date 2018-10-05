/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.seiImplInherited.server;

import javax.jws.WebService;

@WebService(endpointInterface="fromjava.seiImplInherited.server.In")
public class B extends A {
    public B boo() {
        return new B();
    }
}
