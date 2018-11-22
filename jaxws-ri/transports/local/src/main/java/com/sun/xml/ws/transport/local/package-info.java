/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * Transport implementations that work inside the single JVM.
 * Useful for testing.
 *
 * <p>
 * Transports implemented in this package work off the exploded war file
 * image in the file system &mdash; it should have the same file layout
 * that you deploy into, say, Tomcat. They then look for <code>WEB-INF/sun-jaxws.xml</code>
 * to determine what services are in the application, and then deploy
 * them in a servlet-like environment.
 *
 * <p>
 * This package comes with two transports. One is the legacy
 * {@link com.sun.xml.ws.transport.local.LocalTransportFactory "local" transport}, which effectively
 * deploys a new service instance every time you create a new proxy/dispatch.
 * This is not only waste of computation, but it prevents services of the same
 * application from talking with each other.
 *
 * <p>
 * {@link com.sun.xml.ws.transport.local.InVmTransportFactory The "in-vm" transport} is the modern version
 * of the local transport that fixes this problem. You first deploy a new
 * application by using {@link com.sun.xml.ws.transport.local.InVmServer},
 * {@link com.sun.xml.ws.transport.local.InVmServer#getAddress() obtain its address}, configure the JAX-WS RI
 * with that endpoint, then use that to talk to the running service.
 */
package com.sun.xml.ws.transport.local;
