/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document.schema;

import javax.xml.namespace.QName;

/**
 *
 * @author WS Development Team
 */
public interface SchemaConstants {

    // namespace URIs
    String NS_XMLNS = "http://www.w3.org/2000/xmlns/";
    String NS_XSD = "http://www.w3.org/2001/XMLSchema";
    String NS_XSI = "http://www.w3.org/2001/XMLSchema-instance";

    // QNames
    QName QNAME_ALL = new QName(NS_XSD, "all");
    QName QNAME_ANNOTATION = new QName(NS_XSD, "annotation");
    QName QNAME_ANY = new QName(NS_XSD, "any");
    QName QNAME_ANY_ATTRIBUTE = new QName(NS_XSD, "anyAttribute");
    QName QNAME_ATTRIBUTE = new QName(NS_XSD, "attribute");
    QName QNAME_ATTRIBUTE_GROUP = new QName(NS_XSD, "attributeGroup");
    QName QNAME_CHOICE = new QName(NS_XSD, "choice");
    QName QNAME_COMPLEX_CONTENT = new QName(NS_XSD, "complexContent");
    QName QNAME_COMPLEX_TYPE = new QName(NS_XSD, "complexType");
    QName QNAME_ELEMENT = new QName(NS_XSD, "element");
    QName QNAME_ENUMERATION = new QName(NS_XSD, "enumeration");
    QName QNAME_EXTENSION = new QName(NS_XSD, "extension");
    QName QNAME_FIELD = new QName(NS_XSD, "field");
    QName QNAME_FRACTION_DIGITS = new QName(NS_XSD, "fractionDigits");
    QName QNAME_GROUP = new QName(NS_XSD, "group");
    QName QNAME_IMPORT = new QName(NS_XSD, "import");
    QName QNAME_INCLUDE = new QName(NS_XSD, "include");
    QName QNAME_KEY = new QName(NS_XSD, "key");
    QName QNAME_KEYREF = new QName(NS_XSD, "keyref");
    QName QNAME_LENGTH = new QName(NS_XSD, "length");
    QName QNAME_LIST = new QName(NS_XSD, "list");
    QName QNAME_MAX_EXCLUSIVE = new QName(NS_XSD, "maxExclusive");
    QName QNAME_MAX_INCLUSIVE = new QName(NS_XSD, "maxInclusive");
    QName QNAME_MAX_LENGTH = new QName(NS_XSD, "maxLength");
    QName QNAME_MIN_EXCLUSIVE = new QName(NS_XSD, "minExclusive");
    QName QNAME_MIN_INCLUSIVE = new QName(NS_XSD, "minInclusive");
    QName QNAME_MIN_LENGTH = new QName(NS_XSD, "minLength");
    QName QNAME_NOTATION = new QName(NS_XSD, "notation");
    QName QNAME_RESTRICTION = new QName(NS_XSD, "restriction");
    QName QNAME_PATTERN = new QName(NS_XSD, "pattern");
    QName QNAME_PRECISION = new QName(NS_XSD, "precision");
    QName QNAME_REDEFINE = new QName(NS_XSD, "redefine");
    QName QNAME_SCALE = new QName(NS_XSD, "scale");
    QName QNAME_SCHEMA = new QName(NS_XSD, "schema");
    QName QNAME_SELECTOR = new QName(NS_XSD, "selector");
    QName QNAME_SEQUENCE = new QName(NS_XSD, "sequence");
    QName QNAME_SIMPLE_CONTENT =
        new QName(NS_XSD, "simpleContent");
    QName QNAME_SIMPLE_TYPE = new QName(NS_XSD, "simpleType");
    QName QNAME_TOTAL_DIGITS = new QName(NS_XSD, "totalDigits");
    QName QNAME_UNIQUE = new QName(NS_XSD, "unique");
    QName QNAME_UNION = new QName(NS_XSD, "union");
    QName QNAME_WHITE_SPACE = new QName(NS_XSD, "whiteSpace");

    // QNames for built-in XSD types
    QName QNAME_TYPE_STRING = new QName(NS_XSD, "string");
    QName QNAME_TYPE_NORMALIZED_STRING = new QName(NS_XSD, "normalizedString");
    QName QNAME_TYPE_TOKEN = new QName(NS_XSD, "token");
    QName QNAME_TYPE_BYTE = new QName(NS_XSD, "byte");
    QName QNAME_TYPE_UNSIGNED_BYTE = new QName(NS_XSD, "unsignedByte");
    QName QNAME_TYPE_BASE64_BINARY = new QName(NS_XSD, "base64Binary");
    QName QNAME_TYPE_HEX_BINARY = new QName(NS_XSD, "hexBinary");
    QName QNAME_TYPE_INTEGER = new QName(NS_XSD, "integer");
    QName QNAME_TYPE_POSITIVE_INTEGER = new QName(NS_XSD, "positiveInteger");
    QName QNAME_TYPE_NEGATIVE_INTEGER = new QName(NS_XSD, "negativeInteger");
    QName QNAME_TYPE_NON_NEGATIVE_INTEGER = new QName(NS_XSD, "nonNegativeInteger");
    QName QNAME_TYPE_NON_POSITIVE_INTEGER = new QName(NS_XSD, "nonPositiveInteger");
    QName QNAME_TYPE_INT = new QName(NS_XSD, "int");
    QName QNAME_TYPE_UNSIGNED_INT = new QName(NS_XSD, "unsignedInt");
    QName QNAME_TYPE_LONG = new QName(NS_XSD, "long");
    QName QNAME_TYPE_UNSIGNED_LONG = new QName(NS_XSD, "unsignedLong");
    QName QNAME_TYPE_SHORT = new QName(NS_XSD, "short");
    QName QNAME_TYPE_UNSIGNED_SHORT = new QName(NS_XSD, "unsignedShort");
    QName QNAME_TYPE_DECIMAL = new QName(NS_XSD, "decimal");
    QName QNAME_TYPE_FLOAT = new QName(NS_XSD, "float");
    QName QNAME_TYPE_DOUBLE = new QName(NS_XSD, "double");
    QName QNAME_TYPE_BOOLEAN = new QName(NS_XSD, "boolean");
    QName QNAME_TYPE_TIME = new QName(NS_XSD, "time");
    QName QNAME_TYPE_DATE_TIME = new QName(NS_XSD, "dateTime");
    QName QNAME_TYPE_DURATION = new QName(NS_XSD, "duration");
    QName QNAME_TYPE_DATE = new QName(NS_XSD, "date");
    QName QNAME_TYPE_G_MONTH = new QName(NS_XSD, "gMonth");
    QName QNAME_TYPE_G_YEAR = new QName(NS_XSD, "gYear");
    QName QNAME_TYPE_G_YEAR_MONTH = new QName(NS_XSD, "gYearMonth");
    QName QNAME_TYPE_G_DAY = new QName(NS_XSD, "gDay");
    QName QNAME_TYPE_G_MONTH_DAY = new QName(NS_XSD, "gMonthDay");
    QName QNAME_TYPE_NAME = new QName(NS_XSD, "Name");
    QName QNAME_TYPE_QNAME = new QName(NS_XSD, "QName");
    QName QNAME_TYPE_NCNAME = new QName(NS_XSD, "NCName");
    QName QNAME_TYPE_ANY_URI = new QName(NS_XSD, "anyURI");
    QName QNAME_TYPE_ID = new QName(NS_XSD, "ID");
    QName QNAME_TYPE_IDREF = new QName(NS_XSD, "IDREF");
    QName QNAME_TYPE_IDREFS = new QName(NS_XSD, "IDREFS");
    QName QNAME_TYPE_ENTITY = new QName(NS_XSD, "ENTITY");
    QName QNAME_TYPE_ENTITIES = new QName(NS_XSD, "ENTITIES");
    QName QNAME_TYPE_NOTATION = new QName(NS_XSD, "NOTATION");
    QName QNAME_TYPE_NMTOKEN = new QName(NS_XSD, "NMTOKEN");
    QName QNAME_TYPE_NMTOKENS = new QName(NS_XSD, "NMTOKENS");

    QName QNAME_TYPE_LANGUAGE = new QName(NS_XSD, "language");

    // QNames for special types
    QName QNAME_TYPE_URTYPE = new QName(NS_XSD, "anyType");
    QName QNAME_TYPE_SIMPLE_URTYPE = new QName(NS_XSD, "anySimpleType");
}
