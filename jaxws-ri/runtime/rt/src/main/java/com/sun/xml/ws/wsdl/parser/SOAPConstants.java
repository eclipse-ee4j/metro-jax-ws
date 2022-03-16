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

import com.sun.xml.ws.encoding.soap.streaming.SOAPNamespaceConstants;
import com.sun.xml.ws.encoding.soap.streaming.SOAP12NamespaceConstants;

import javax.xml.namespace.QName;

public interface SOAPConstants {

    // namespace URIs
    String URI_ENVELOPE = SOAPNamespaceConstants.ENVELOPE;
    String URI_ENVELOPE12 = SOAP12NamespaceConstants.ENVELOPE;

    String NS_WSDL_SOAP =
        "http://schemas.xmlsoap.org/wsdl/soap/";

    String NS_WSDL_SOAP12 =
        "http://schemas.xmlsoap.org/wsdl/soap12/";

    String NS_SOAP_ENCODING = "http://schemas.xmlsoap.org/soap/encoding/";

    // other URIs
    String URI_SOAP_TRANSPORT_HTTP =
        "http://schemas.xmlsoap.org/soap/http";

    // QNames
    QName QNAME_ADDRESS =
        new QName(NS_WSDL_SOAP, "address");
    QName QNAME_SOAP12ADDRESS =
        new QName(NS_WSDL_SOAP12, "address");
    QName QNAME_BINDING =
        new QName(NS_WSDL_SOAP, "binding");
    QName QNAME_BODY = new QName(NS_WSDL_SOAP, "body");
    QName QNAME_SOAP12BODY = new QName(NS_WSDL_SOAP12, "body");
    QName QNAME_FAULT = new QName(NS_WSDL_SOAP, "fault");
    QName QNAME_HEADER = new QName(NS_WSDL_SOAP, "header");
    QName QNAME_SOAP12HEADER = new QName(NS_WSDL_SOAP12, "header");
    QName QNAME_HEADERFAULT =
        new QName(NS_WSDL_SOAP, "headerfault");
    QName QNAME_OPERATION =
        new QName(NS_WSDL_SOAP, "operation");
    QName QNAME_SOAP12OPERATION =
        new QName(NS_WSDL_SOAP12, "operation"); 
    QName QNAME_MUSTUNDERSTAND =
        new QName(URI_ENVELOPE, "mustUnderstand");


}
