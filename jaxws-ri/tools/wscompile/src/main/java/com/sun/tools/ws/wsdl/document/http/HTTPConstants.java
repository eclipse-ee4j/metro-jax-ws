/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document.http;

import javax.xml.namespace.QName;

/**
 * Interface defining HTTP-extension-related constants.
 *
 * @author WS Development Team
 */
public interface HTTPConstants {

    // namespace URIs
    static final String NS_WSDL_HTTP = "http://schemas.xmlsoap.org/wsdl/http/";

    // QNames
    static final QName QNAME_ADDRESS = new QName(NS_WSDL_HTTP, "address");
    static final QName QNAME_BINDING = new QName(NS_WSDL_HTTP, "binding");
    static final QName QNAME_OPERATION = new QName(NS_WSDL_HTTP, "operation");
    static final QName QNAME_URL_ENCODED = new QName(NS_WSDL_HTTP, "urlEncoded");
    static final QName QNAME_URL_REPLACEMENT = new QName(NS_WSDL_HTTP, "urlReplacement");
}
