/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * Defines tools for JAX-WS classes and WSDL generation,
 * including the <em>{@index wsgen wsgen tool}</em>
 * and <em>{@index wsimport wsimport tool}</em> tools.
 *
 *
 * @since 2.4.0
 */
module com.sun.tools.ws {
    requires com.sun.xml.ws;
    requires javax.jws;
    requires com.sun.istack.runtime;
    requires com.sun.istack.tools;
    requires com.sun.codemodel;
    requires com.sun.tools.jxc;
    requires com.sun.tools.xjc;
    requires com.sun.xml.xsom;
    requires com.sun.xml.ws.policy;
    requires java.compiler;
    requires java.logging;
    requires java.rmi;
    requires java.xml;
    requires java.xml.bind;
    requires java.xml.ws;

    exports com.sun.tools.ws;
    exports com.sun.tools.ws.ant;
    exports com.sun.tools.ws.api;
    exports com.sun.tools.ws.spi;
    exports com.sun.tools.ws.wscompile;

    uses com.sun.tools.ws.wscompile.Plugin;

    provides com.sun.tools.ws.wscompile.Plugin with
            com.sun.tools.ws.wscompile.plugin.at_generated.PluginImpl;
}
