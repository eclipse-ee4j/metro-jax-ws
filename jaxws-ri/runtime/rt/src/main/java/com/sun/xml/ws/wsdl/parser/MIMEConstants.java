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


interface MIMEConstants {

    // namespace URIs
    String NS_WSDL_MIME = "http://schemas.xmlsoap.org/wsdl/mime/";

    // QNames
    QName QNAME_CONTENT = new QName(NS_WSDL_MIME, "content");
    QName QNAME_MULTIPART_RELATED = new QName(NS_WSDL_MIME, "multipartRelated");
    QName QNAME_PART = new QName(NS_WSDL_MIME, "part");
    QName QNAME_MIME_XML = new QName(NS_WSDL_MIME, "mimeXml");
}
