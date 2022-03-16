/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing.v200408;

import javax.xml.namespace.QName;

/**
 * Constants for Member Submission WS-Addressing version
 *
 * @author Arun Gupta
 */
public interface MemberSubmissionAddressingConstants {
    String WSA_NAMESPACE_NAME = "http://schemas.xmlsoap.org/ws/2004/08/addressing";
    String WSA_NAMESPACE_WSDL_NAME = WSA_NAMESPACE_NAME;
    String WSA_NAMESPACE_POLICY_NAME = "http://schemas.xmlsoap.org/ws/2004/08/addressing/policy";

    QName  WSA_ACTION_QNAME = new QName(WSA_NAMESPACE_NAME,"Action");

    String WSA_SERVICENAME_NAME = "ServiceName";
    String WSA_PORTTYPE_NAME = "PortType";
    String WSA_PORTNAME_NAME = "PortName";

    String WSA_ADDRESS_NAME = "Address";
    QName WSA_ADDRESS_QNAME = new QName(WSA_NAMESPACE_NAME, WSA_ADDRESS_NAME);
    
    String WSA_EPR_NAME = "EndpointReference";
    QName WSA_EPR_QNAME = new QName(WSA_NAMESPACE_NAME, WSA_EPR_NAME);

    String WSA_ANONYMOUS_ADDRESS = WSA_NAMESPACE_NAME + "/role/anonymous";
    String WSA_NONE_ADDRESS = "";

    String WSA_DEFAULT_FAULT_ACTION = WSA_NAMESPACE_NAME + "/fault";

    QName INVALID_MAP_QNAME = new QName(WSA_NAMESPACE_NAME, "InvalidMessageInformationHeader");
    QName MAP_REQUIRED_QNAME = new QName(WSA_NAMESPACE_NAME, "MessageInformationHeaderRequired");
    QName DESTINATION_UNREACHABLE_QNAME = new QName(WSA_NAMESPACE_NAME, "DestinationUnreachable");
    QName ACTION_NOT_SUPPORTED_QNAME = new QName(WSA_NAMESPACE_NAME, "ActionNotSupported");
    QName ENDPOINT_UNAVAILABLE_QNAME = new QName(WSA_NAMESPACE_NAME, "EndpointUnavailable");

    String ACTION_NOT_SUPPORTED_TEXT = "The \"%s\" cannot be processed at the receiver.";
    String DESTINATION_UNREACHABLE_TEXT = "No route can be determined to reach the destination role defined by the WS-Addressing To.";
    String ENDPOINT_UNAVAILABLE_TEXT = "The endpoint is unable to process the message at this time.";
    String INVALID_MAP_TEXT = "A message information header is not valid and the message cannot be processed.";
    String MAP_REQUIRED_TEXT = "A required message information header, To, MessageID, or Action, is not present.";

    QName PROBLEM_ACTION_QNAME = new QName(WSA_NAMESPACE_NAME, "ProblemAction");
    QName PROBLEM_HEADER_QNAME_QNAME = new QName(WSA_NAMESPACE_NAME, "ProblemHeaderQName");
    QName FAULT_DETAIL_QNAME = new QName(WSA_NAMESPACE_NAME, "FaultDetail");

    String ANONYMOUS_EPR = "<EndpointReference xmlns=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\">\n"+
        "    <Address>http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous</Address>\n"+
        "</EndpointReference>";

    QName MEX_METADATA = new QName("http://schemas.xmlsoap.org/ws/2004/09/mex", "Metadata","mex");
    QName MEX_METADATA_SECTION = new QName("http://schemas.xmlsoap.org/ws/2004/09/mex", "MetadataSection","mex");
    String MEX_METADATA_DIALECT_ATTRIBUTE = "Dialect";
    String MEX_METADATA_DIALECT_VALUE = "http://schemas.xmlsoap.org/wsdl/";
}
