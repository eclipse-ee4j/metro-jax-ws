/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
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
module com.sun.tools.ws.wscompile {

    requires java.compiler;
    requires java.logging;
    requires java.rmi;

    requires com.sun.xml.ws.rt;
    requires com.sun.xml.ws.fi;
    requires static com.sun.xml.ws.httpspi.servlet;
    requires static com.sun.xml.ws.servlet;

    requires com.sun.tools.jxc;
    requires com.sun.tools.xjc;

    exports com.sun.tools.ws;
    exports com.sun.tools.ws.ant;
    exports com.sun.tools.ws.api;
    exports com.sun.tools.ws.api.wsdl;
    exports com.sun.tools.ws.processor;
    exports com.sun.tools.ws.processor.modeler;
    exports com.sun.tools.ws.processor.modeler.annotation;
    exports com.sun.tools.ws.processor.modeler.wsdl;
    exports com.sun.tools.ws.spi;
    exports com.sun.tools.ws.util;
    exports com.sun.tools.ws.util.xml;
    exports com.sun.tools.ws.wscompile;
    exports com.sun.tools.ws.wsdl.document;
    exports com.sun.tools.ws.wsdl.parser;

    uses com.sun.tools.ws.wscompile.Plugin;

    provides com.sun.tools.ws.wscompile.Plugin with
            com.sun.tools.ws.wscompile.plugin.at_generated.PluginImpl;
}
