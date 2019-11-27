/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * Servlet transport for the JAX-WS RI.
 *
 * <p>
 * This package glues together the servlet API and
 * {@link com.sun.xml.ws.api.server the JAX-WS hosting API}.
 *
 * <h2>Compatibility</h2>
 * <p>
 * {@link com.sun.xml.ws.transport.http.servlet.WSServlet}
 * and {@link com.sun.xml.ws.transport.http.servlet.WSServletContextListener} class names
 * show up in the user application, so we need to be careful in changing them.
 *
 * <b>Other parts of the code, including actual definitions of the above classes,
 * are subject to change without notice.</b>
 */
package com.sun.xml.ws.transport.http.servlet;
