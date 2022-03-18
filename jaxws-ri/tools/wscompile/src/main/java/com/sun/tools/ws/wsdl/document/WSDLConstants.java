/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document;

import javax.xml.namespace.QName;

/**
 * Interface defining WSDL-related constants.
 *
 * @author WS Development Team
 */
public interface WSDLConstants {

    // namespace URIs
    String NS_XMLNS = "http://www.w3.org/2000/xmlns/";
    String NS_WSDL = "http://schemas.xmlsoap.org/wsdl/";

    // QNames
    QName QNAME_BINDING = new QName(NS_WSDL, "binding");
    QName QNAME_DEFINITIONS = new QName(NS_WSDL, "definitions");
    QName QNAME_DOCUMENTATION = new QName(NS_WSDL, "documentation");
    QName QNAME_FAULT = new QName(NS_WSDL, "fault");
    QName QNAME_IMPORT = new QName(NS_WSDL, "import");
    QName QNAME_INPUT = new QName(NS_WSDL, "input");
    QName QNAME_MESSAGE = new QName(NS_WSDL, "message");
    QName QNAME_OPERATION = new QName(NS_WSDL, "operation");
    QName QNAME_OUTPUT = new QName(NS_WSDL, "output");
    QName QNAME_PART = new QName(NS_WSDL, "part");
    QName QNAME_PORT = new QName(NS_WSDL, "port");
    QName QNAME_PORT_TYPE = new QName(NS_WSDL, "portType");
    QName QNAME_SERVICE = new QName(NS_WSDL, "service");
    QName QNAME_TYPES = new QName(NS_WSDL, "types");

    QName QNAME_ATTR_ARRAY_TYPE = new QName(NS_WSDL, "arrayType");
}
