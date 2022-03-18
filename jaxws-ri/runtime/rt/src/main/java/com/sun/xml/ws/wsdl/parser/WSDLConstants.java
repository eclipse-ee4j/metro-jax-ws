/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.parser;

import javax.xml.namespace.QName;


/**
 * Interface defining WSDL-related constants.
 *
 * @author WS Development Team
 */
public interface WSDLConstants {
    // namespace URIs
    String PREFIX_NS_WSDL = "wsdl";
    String NS_XMLNS = "http://www.w3.org/2001/XMLSchema";
    String NS_WSDL = "http://schemas.xmlsoap.org/wsdl/";
    String NS_SOAP11_HTTP_BINDING = "http://schemas.xmlsoap.org/soap/http";

    QName QNAME_SCHEMA = new QName(NS_XMLNS, "schema");

    // QNames
    QName QNAME_BINDING = new QName(NS_WSDL, "binding");
    QName QNAME_DEFINITIONS = new QName(NS_WSDL, "definitions");
    QName QNAME_DOCUMENTATION = new QName(NS_WSDL, "documentation");
    QName NS_SOAP_BINDING_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "address");
    QName NS_SOAP_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "binding");
    QName NS_SOAP12_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "binding");
    QName NS_SOAP12_BINDING_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "address");

    //static final QName QNAME_FAULT = new QName(NS_WSDL, "fault");
    QName QNAME_IMPORT = new QName(NS_WSDL, "import");

    //static final QName QNAME_INPUT = new QName(NS_WSDL, "input");
    QName QNAME_MESSAGE = new QName(NS_WSDL, "message");
    QName QNAME_PART = new QName(NS_WSDL, "part");
    QName QNAME_OPERATION = new QName(NS_WSDL, "operation");
    QName QNAME_INPUT = new QName(NS_WSDL, "input");
    QName QNAME_OUTPUT = new QName(NS_WSDL, "output");

    //static final QName QNAME_OUTPUT = new QName(NS_WSDL, "output");
    //static final QName QNAME_PART = new QName(NS_WSDL, "part");
    QName QNAME_PORT = new QName(NS_WSDL, "port");
    QName QNAME_ADDRESS = new QName(NS_WSDL, "address");
    QName QNAME_PORT_TYPE = new QName(NS_WSDL, "portType");
    QName QNAME_FAULT = new QName(NS_WSDL, "fault");
    QName QNAME_SERVICE = new QName(NS_WSDL, "service");
    QName QNAME_TYPES = new QName(NS_WSDL, "types");

    String ATTR_TRANSPORT = "transport";
    String ATTR_LOCATION = "location";
    String ATTR_NAME = "name";
    String ATTR_TNS = "targetNamespace";

    //static final QName QNAME_ATTR_ARRAY_TYPE = new QName(NS_WSDL, "arrayType");
}
