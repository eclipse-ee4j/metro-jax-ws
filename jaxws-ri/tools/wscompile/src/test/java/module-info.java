/*
 * Copyright (c) 2018, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

module com.sun.tools.ws.wscompile.test {
    requires java.xml;
    requires transitive com.sun.tools.ws.wscompile;
    requires java.jws;
    requires java.xml.bind;
    requires java.xml.ws;
    requires java.rmi;
    requires java.compiler;
    requires java.logging;
    requires com.sun.tools.xjc;
    requires transitive junit;
    requires ant;

    exports com.sun.tools.ws.test.ant;
    exports com.sun.tools.ws.test.processor;
    exports com.sun.tools.ws.test.processor.modeler;
    exports com.sun.tools.ws.test.processor.modeler.annotation;
    exports com.sun.tools.ws.test.util;
    exports com.sun.tools.ws.test.wscompile;
    exports com.sun.tools.ws.test.wsdl.parser;

    uses com.sun.tools.ws.api.WsgenExtension;
    uses com.sun.tools.ws.api.wsdl.TWSDLExtensionHandler;
    uses com.sun.tools.ws.wscompile.Plugin;
}
