/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.seinoimpl_inherited.server;

import javax.jws.WebService;

@WebService(endpointInterface="fromjava.seinoimpl_inherited.server.EchoIF")
public class EchoImpl {
    public Bar echoBar(Bar bar) {
        return bar;
    }

    public String echoString(String str) {
        return str;
    }
}
