/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document.soap;

import javax.xml.namespace.QName;

/**
 * Interface defining SOAP-related constants.
 *
 * @author WS Development Team
 */
public interface SOAPConstants {

    // namespace URIs
    String URI_ENVELOPE =
        "http://schemas.xmlsoap.org/soap/envelope/";
    String NS_WSDL_SOAP =
        "http://schemas.xmlsoap.org/wsdl/soap/";
    String NS_SOAP_ENCODING = "http://schemas.xmlsoap.org/soap/encoding/";

    // other URIs
    String URI_SOAP_TRANSPORT_HTTP =
        "http://schemas.xmlsoap.org/soap/http";

    // QNames
    QName QNAME_ADDRESS =
        new QName(NS_WSDL_SOAP, "address");
    QName QNAME_BINDING =
        new QName(NS_WSDL_SOAP, "binding");
    QName QNAME_BODY = new QName(NS_WSDL_SOAP, "body");
    QName QNAME_FAULT = new QName(NS_WSDL_SOAP, "fault");
    QName QNAME_HEADER = new QName(NS_WSDL_SOAP, "header");
    QName QNAME_HEADERFAULT =
        new QName(NS_WSDL_SOAP, "headerfault");
    QName QNAME_OPERATION =
        new QName(NS_WSDL_SOAP, "operation");
    QName QNAME_MUSTUNDERSTAND =
        new QName(URI_ENVELOPE, "mustUnderstand");   


    // SOAP encoding QNames
    QName QNAME_TYPE_ARRAY =
        new QName(NS_SOAP_ENCODING, "Array");
    QName QNAME_ATTR_GROUP_COMMON_ATTRIBUTES =
        new QName(NS_SOAP_ENCODING, "commonAttributes");
    QName QNAME_ATTR_ARRAY_TYPE =
        new QName(NS_SOAP_ENCODING, "arrayType");
    QName QNAME_ATTR_OFFSET =
        new QName(NS_SOAP_ENCODING, "offset");
    QName QNAME_ATTR_POSITION =
        new QName(NS_SOAP_ENCODING, "position");

    QName QNAME_TYPE_BASE64 =
        new QName(NS_SOAP_ENCODING, "base64");

    QName QNAME_ELEMENT_STRING =
        new QName(NS_SOAP_ENCODING, "string");
    QName QNAME_ELEMENT_NORMALIZED_STRING =
        new QName(NS_SOAP_ENCODING, "normalizedString");
    QName QNAME_ELEMENT_TOKEN =
        new QName(NS_SOAP_ENCODING, "token");
    QName QNAME_ELEMENT_BYTE =
        new QName(NS_SOAP_ENCODING, "byte");
    QName QNAME_ELEMENT_UNSIGNED_BYTE =
        new QName(NS_SOAP_ENCODING, "unsignedByte");
    QName QNAME_ELEMENT_BASE64_BINARY =
        new QName(NS_SOAP_ENCODING, "base64Binary");
    QName QNAME_ELEMENT_HEX_BINARY =
        new QName(NS_SOAP_ENCODING, "hexBinary");
    QName QNAME_ELEMENT_INTEGER =
        new QName(NS_SOAP_ENCODING, "integer");
    QName QNAME_ELEMENT_POSITIVE_INTEGER =
        new QName(NS_SOAP_ENCODING, "positiveInteger");
    QName QNAME_ELEMENT_NEGATIVE_INTEGER =
        new QName(NS_SOAP_ENCODING, "negativeInteger");
    QName QNAME_ELEMENT_NON_NEGATIVE_INTEGER =
        new QName(NS_SOAP_ENCODING, "nonNegativeInteger");
    QName QNAME_ELEMENT_NON_POSITIVE_INTEGER =
        new QName(NS_SOAP_ENCODING, "nonPositiveInteger");
    QName QNAME_ELEMENT_INT =
        new QName(NS_SOAP_ENCODING, "int");
    QName QNAME_ELEMENT_UNSIGNED_INT =
        new QName(NS_SOAP_ENCODING, "unsignedInt");
    QName QNAME_ELEMENT_LONG =
        new QName(NS_SOAP_ENCODING, "long");
    QName QNAME_ELEMENT_UNSIGNED_LONG =
        new QName(NS_SOAP_ENCODING, "unsignedLong");
    QName QNAME_ELEMENT_SHORT =
        new QName(NS_SOAP_ENCODING, "short");
    QName QNAME_ELEMENT_UNSIGNED_SHORT =
        new QName(NS_SOAP_ENCODING, "unsignedShort");
    QName QNAME_ELEMENT_DECIMAL =
        new QName(NS_SOAP_ENCODING, "decimal");
    QName QNAME_ELEMENT_FLOAT =
        new QName(NS_SOAP_ENCODING, "float");
    QName QNAME_ELEMENT_DOUBLE =
        new QName(NS_SOAP_ENCODING, "double");
    QName QNAME_ELEMENT_BOOLEAN =
        new QName(NS_SOAP_ENCODING, "boolean");
    QName QNAME_ELEMENT_TIME =
        new QName(NS_SOAP_ENCODING, "time");
    QName QNAME_ELEMENT_DATE_TIME =
        new QName(NS_SOAP_ENCODING, "dateTime");
    QName QNAME_ELEMENT_DURATION =
        new QName(NS_SOAP_ENCODING, "duration");
    QName QNAME_ELEMENT_DATE =
        new QName(NS_SOAP_ENCODING, "date");
    QName QNAME_ELEMENT_G_MONTH =
        new QName(NS_SOAP_ENCODING, "gMonth");
    QName QNAME_ELEMENT_G_YEAR =
        new QName(NS_SOAP_ENCODING, "gYear");
    QName QNAME_ELEMENT_G_YEAR_MONTH =
        new QName(NS_SOAP_ENCODING, "gYearMonth");
    QName QNAME_ELEMENT_G_DAY =
        new QName(NS_SOAP_ENCODING, "gDay");
    QName QNAME_ELEMENT_G_MONTH_DAY =
        new QName(NS_SOAP_ENCODING, "gMonthDay");
    QName QNAME_ELEMENT_NAME =
        new QName(NS_SOAP_ENCODING, "Name");
    QName QNAME_ELEMENT_QNAME =
        new QName(NS_SOAP_ENCODING, "QName");
    QName QNAME_ELEMENT_NCNAME =
        new QName(NS_SOAP_ENCODING, "NCName");
    QName QNAME_ELEMENT_ANY_URI =
        new QName(NS_SOAP_ENCODING, "anyURI");
    QName QNAME_ELEMENT_ID =
        new QName(NS_SOAP_ENCODING, "ID");
    QName QNAME_ELEMENT_IDREF =
        new QName(NS_SOAP_ENCODING, "IDREF");
    QName QNAME_ELEMENT_IDREFS =
        new QName(NS_SOAP_ENCODING, "IDREFS");
    QName QNAME_ELEMENT_ENTITY =
        new QName(NS_SOAP_ENCODING, "ENTITY");
    QName QNAME_ELEMENT_ENTITIES =
        new QName(NS_SOAP_ENCODING, "ENTITIES");
    QName QNAME_ELEMENT_NOTATION =
        new QName(NS_SOAP_ENCODING, "NOTATION");
    QName QNAME_ELEMENT_NMTOKEN =
        new QName(NS_SOAP_ENCODING, "NMTOKEN");
    QName QNAME_ELEMENT_NMTOKENS =
        new QName(NS_SOAP_ENCODING, "NMTOKENS");

    QName QNAME_TYPE_STRING =
        new QName(NS_SOAP_ENCODING, "string");
    QName QNAME_TYPE_NORMALIZED_STRING =
        new QName(NS_SOAP_ENCODING, "normalizedString");
    QName QNAME_TYPE_TOKEN =
        new QName(NS_SOAP_ENCODING, "token");
    QName QNAME_TYPE_BYTE =
        new QName(NS_SOAP_ENCODING, "byte");
    QName QNAME_TYPE_UNSIGNED_BYTE =
        new QName(NS_SOAP_ENCODING, "unsignedByte");
    QName QNAME_TYPE_BASE64_BINARY =
        new QName(NS_SOAP_ENCODING, "base64Binary");
    QName QNAME_TYPE_HEX_BINARY =
        new QName(NS_SOAP_ENCODING, "hexBinary");
    QName QNAME_TYPE_INTEGER =
        new QName(NS_SOAP_ENCODING, "integer");
    QName QNAME_TYPE_POSITIVE_INTEGER =
        new QName(NS_SOAP_ENCODING, "positiveInteger");
    QName QNAME_TYPE_NEGATIVE_INTEGER =
        new QName(NS_SOAP_ENCODING, "negativeInteger");
    QName QNAME_TYPE_NON_NEGATIVE_INTEGER =
        new QName(NS_SOAP_ENCODING, "nonNegativeInteger");
    QName QNAME_TYPE_NON_POSITIVE_INTEGER =
        new QName(NS_SOAP_ENCODING, "nonPositiveInteger");
    QName QNAME_TYPE_INT =
        new QName(NS_SOAP_ENCODING, "int");
    QName QNAME_TYPE_UNSIGNED_INT =
        new QName(NS_SOAP_ENCODING, "unsignedInt");
    QName QNAME_TYPE_LONG =
        new QName(NS_SOAP_ENCODING, "long");
    QName QNAME_TYPE_UNSIGNED_LONG =
        new QName(NS_SOAP_ENCODING, "unsignedLong");
    QName QNAME_TYPE_SHORT =
        new QName(NS_SOAP_ENCODING, "short");
    QName QNAME_TYPE_UNSIGNED_SHORT =
        new QName(NS_SOAP_ENCODING, "unsignedShort");
    QName QNAME_TYPE_DECIMAL =
        new QName(NS_SOAP_ENCODING, "decimal");
    QName QNAME_TYPE_FLOAT =
        new QName(NS_SOAP_ENCODING, "float");
    QName QNAME_TYPE_DOUBLE =
        new QName(NS_SOAP_ENCODING, "double");
    QName QNAME_TYPE_BOOLEAN =
        new QName(NS_SOAP_ENCODING, "boolean");
    QName QNAME_TYPE_TIME =
        new QName(NS_SOAP_ENCODING, "time");
    QName QNAME_TYPE_DATE_TIME =
        new QName(NS_SOAP_ENCODING, "dateTime");
    QName QNAME_TYPE_DURATION =
        new QName(NS_SOAP_ENCODING, "duration");
    QName QNAME_TYPE_DATE =
        new QName(NS_SOAP_ENCODING, "date");
    QName QNAME_TYPE_G_MONTH =
        new QName(NS_SOAP_ENCODING, "gMonth");
    QName QNAME_TYPE_G_YEAR =
        new QName(NS_SOAP_ENCODING, "gYear");
    QName QNAME_TYPE_G_YEAR_MONTH =
        new QName(NS_SOAP_ENCODING, "gYearMonth");
    QName QNAME_TYPE_G_DAY =
        new QName(NS_SOAP_ENCODING, "gDay");
    QName QNAME_TYPE_G_MONTH_DAY =
        new QName(NS_SOAP_ENCODING, "gMonthDay");
    QName QNAME_TYPE_NAME =
        new QName(NS_SOAP_ENCODING, "Name");
    QName QNAME_TYPE_QNAME =
        new QName(NS_SOAP_ENCODING, "QName");
    QName QNAME_TYPE_NCNAME =
        new QName(NS_SOAP_ENCODING, "NCName");
    QName QNAME_TYPE_ANY_URI =
        new QName(NS_SOAP_ENCODING, "anyURI");
    QName QNAME_TYPE_ID = new QName(NS_SOAP_ENCODING, "ID");
    QName QNAME_TYPE_IDREF =
        new QName(NS_SOAP_ENCODING, "IDREF");
    QName QNAME_TYPE_IDREFS =
        new QName(NS_SOAP_ENCODING, "IDREFS");
    QName QNAME_TYPE_ENTITY =
        new QName(NS_SOAP_ENCODING, "ENTITY");
    QName QNAME_TYPE_ENTITIES =
        new QName(NS_SOAP_ENCODING, "ENTITIES");
    QName QNAME_TYPE_NOTATION =
        new QName(NS_SOAP_ENCODING, "NOTATION");
    QName QNAME_TYPE_NMTOKEN =
        new QName(NS_SOAP_ENCODING, "NMTOKEN");
    QName QNAME_TYPE_NMTOKENS =
        new QName(NS_SOAP_ENCODING, "NMTOKENS");
    QName QNAME_TYPE_LANGUAGE =
        new QName(NS_SOAP_ENCODING, "LANGUAGE");

    // SOAP attributes with non-colonized names
    QName QNAME_ATTR_ID = new QName("", "id");
    QName QNAME_ATTR_HREF = new QName("", "href");
    
}
