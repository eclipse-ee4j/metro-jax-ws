/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.parser;

import javax.xml.namespace.QName;


interface MIMEConstants {

    // namespace URIs
    static final String NS_WSDL_MIME = "http://schemas.xmlsoap.org/wsdl/mime/";

    // QNames
    static final QName QNAME_CONTENT = new QName(NS_WSDL_MIME, "content");
    static final QName QNAME_MULTIPART_RELATED = new QName(NS_WSDL_MIME, "multipartRelated");
    static final QName QNAME_PART = new QName(NS_WSDL_MIME, "part");
    static final QName QNAME_MIME_XML = new QName(NS_WSDL_MIME, "mimeXml");
}
