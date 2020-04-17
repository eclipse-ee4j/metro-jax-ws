/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.generics;

import jakarta.jws.WebService;

/**
 * Web Service uses generics.
 */
@WebService(endpointInterface="fromjava.generics.EchoIF", portName="EchoPort")
public class EchoImpl implements EchoIF<Bar> {
    public Bar echoBar(Bar bar) {
        return bar;
    }

    public String echoString(String str) {
        return str;
    }
}
