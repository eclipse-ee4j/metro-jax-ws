/*
 * Copyright (c) 2009, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.server;

import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.transport.http.HttpAdapterList;

public class ServerAdapterList extends HttpAdapterList<ServerAdapter> {
    @Override
    protected ServerAdapter createHttpAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
        return new ServerAdapter(name, urlPattern, endpoint, this);
    }
}
