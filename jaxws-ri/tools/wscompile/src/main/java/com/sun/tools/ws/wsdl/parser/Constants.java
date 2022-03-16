/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.parser;

/**
 * An interface defining constants needed to read and write WSDL documents.
 *
 * @author WS Development Team
 */
public interface Constants {
    // WSDL element tags
    String TAG_BINDING = "binding";
    String TAG_DEFINITIONS = "definitions";
    String TAG_DOCUMENTATION = "documentation";
    String TAG_MESSAGE = "message";
    String TAG_PART = "part";
    String TAG_PORT_TYPE = "portType";
    String TAG_TYPES = "types";
    String TAG_OPERATION = "operation";
    String TAG_INPUT = "input";
    String TAG_OUTPUT = "output";
    String TAG_FAULT = "fault";
    String TAG_SERVICE = "service";
    String TAG_PORT = "port";
    String TAG_ = "";

    // WSDL attribute names
    String ATTR_ELEMENT = "element";
    String ATTR_NAME = "name";
    String ATTR_REQUIRED = "required";
    String ATTR_TARGET_NAMESPACE = "targetNamespace";
    String ATTR_TYPE = "type";
    String ATTR_MESSAGE = "message";
    String ATTR_BINDING = "binding";
    String ATTR_LOCATION = "location";
    String ATTR_TRANSPORT = "transport";
    String ATTR_STYLE = "style";
    String ATTR_USE = "use";
    String ATTR_NAMESPACE = "namespace";
    String ATTR_ENCODING_STYLE = "encodingStyle";
    String ATTR_PART = "part";
    String ATTR_PARTS = "parts";
    String ATTR_SOAP_ACTION = "soapAction";
    String ATTR_PARAMETER_ORDER = "parameterOrder";
    String ATTR_VERB = "verb";

    // schema attribute names
    String ATTR_ID = "id";
    String ATTR_VERSION = "version";
    String ATTR_ATTRIBUTE_FORM_DEFAULT = "attributeFormDefault";
    String ATTR_BLOCK_DEFAULT = "blockDefault";
    String ATTR_ELEMENT_FORM_DEFAULT = "elementFormDefault";
    String ATTR_FINAL_DEFAULT = "finalDefault";
    String ATTR_ABSTRACT = "abstract";
    String ATTR_NILLABLE = "nillable";
    String ATTR_DEFAULT = "default";
    String ATTR_FIXED = "fixed";
    String ATTR_FORM = "form";
    String ATTR_BLOCK = "block";
    String ATTR_FINAL = "final";
    String ATTR_REF = "ref";
    String ATTR_SUBSTITUTION_GROUP = "substitutionGroup";
    String ATTR_MIN_OCCURS = "minOccurs";
    String ATTR_MAX_OCCURS = "maxOccurs";
    String ATTR_PROCESS_CONTENTS = "processContents";
    String ATTR_MIXED = "mixed";
    String ATTR_BASE = "base";
    String ATTR_VALUE = "value";
    String ATTR_XPATH = "xpath";
    String ATTR_SCHEMA_LOCATION = "schemaLocation";
    String ATTR_REFER = "refer";
    String ATTR_ITEM_TYPE = "itemType";
    String ATTR_PUBLIC = "public";
    String ATTR_SYSTEM = "system";
    String ATTR_MEMBER_TYPES = "memberTypes";
    String ATTR_ = "";

    // WSDL attribute values
    String ATTRVALUE_RPC = "rpc";
    String ATTRVALUE_DOCUMENT = "document";
    String ATTRVALUE_LITERAL = "literal";
    String ATTRVALUE_ENCODED = "encoded";

    // schema attribute values
    String ATTRVALUE_QUALIFIED = "qualified";
    String ATTRVALUE_UNQUALIFIED = "unqualified";
    String ATTRVALUE_ALL = "#all";
    String ATTRVALUE_SUBSTITUTION = "substitution";
    String ATTRVALUE_EXTENSION = "extension";
    String ATTRVALUE_RESTRICTION = "restriction";
    String ATTRVALUE_LIST = "list";
    String ATTRVALUE_UNION = "union";
    String ATTRVALUE_UNBOUNDED = "unbounded";
    String ATTRVALUE_PROHIBITED = "prohibited";
    String ATTRVALUE_OPTIONAL = "optional";
    String ATTRVALUE_REQUIRED = "required";
    String ATTRVALUE_LAX = "lax";
    String ATTRVALUE_SKIP = "skip";
    String ATTRVALUE_STRICT = "strict";
    String ATTRVALUE_ANY = "##any";
    String ATTRVALUE_LOCAL = "##local";
    String ATTRVALUE_OTHER = "##other";
    String ATTRVALUE_TARGET_NAMESPACE = "##targetNamespace";
    String ATTRVALUE_ = "";

    // namespace URIs
    String NS_XML = "http://www.w3.org/XML/1998/namespace";
    String NS_XMLNS = "http://www.w3.org/2000/xmlns/";
    String NS_WSDL = "http://schemas.xmlsoap.org/wsdl/";
    String NS_WSDL_SOAP = "http://schemas.xmlsoap.org/wsdl/soap/";
    String NS_WSDL_SOAP12 = "http://schemas.xmlsoap.org/wsdl/soap12/";
    String NS_WSDL_HTTP = "http://schemas.xmlsoap.org/wsdl/http/";
    String NS_WSDL_MIME = "http://schemas.xmlsoap.org/wsdl/mime/";
    String NS_XSD = "http://www.w3.org/2001/XMLSchema";
    String NS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    String NS_ = "";

    // other constants
    String XMLNS = "xmlns";
    String TRUE = "true";
    String FALSE = "false";
}
