/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

module com.sun.tools.ws.test {
    requires java.xml;
    requires transitive com.sun.tools.ws;
    requires javax.jws;
    requires java.xml.bind;
    requires java.xml.ws;
    requires java.rmi;
    requires java.compiler;
    requires java.logging;
    requires com.sun.istack.runtime;
    requires com.sun.tools.xjc;
}
