/*
 * Copyright (c) 2018, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * Servlet Support for JAX-WS RI.
 *
 * @since 2.4.0
 */
module com.sun.xml.ws.servlet {
    requires transitive com.sun.xml.ws.rt;
    requires java.logging;
    requires transitive jakarta.servlet;

    exports com.sun.xml.ws.developer.servlet;
    exports com.sun.xml.ws.server.servlet;
    exports com.sun.xml.ws.transport.http.servlet;
}
