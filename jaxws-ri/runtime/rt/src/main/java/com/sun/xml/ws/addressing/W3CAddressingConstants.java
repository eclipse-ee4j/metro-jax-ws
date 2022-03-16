/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing;

import javax.xml.namespace.QName;

import com.sun.xml.ws.api.addressing.AddressingVersion;

/**
 * Constants for W3C WS-Addressing version
 *
 * @author Arun Gupta
 */
public interface W3CAddressingConstants {
    String WSA_NAMESPACE_NAME = "http://www.w3.org/2005/08/addressing";
    String WSA_NAMESPACE_WSDL_NAME = "http://www.w3.org/2006/05/addressing/wsdl";

    String WSAW_SERVICENAME_NAME = "ServiceName";
    String WSAW_INTERFACENAME_NAME = "InterfaceName";
    String WSAW_ENDPOINTNAME_NAME = "EndpointName";

    String WSA_REFERENCEPROPERTIES_NAME = "ReferenceParameters";
    QName WSA_REFERENCEPROPERTIES_QNAME = new QName(WSA_NAMESPACE_NAME, WSA_REFERENCEPROPERTIES_NAME);

    String WSA_REFERENCEPARAMETERS_NAME = "ReferenceParameters";
    QName WSA_REFERENCEPARAMETERS_QNAME = new QName(WSA_NAMESPACE_NAME, WSA_REFERENCEPARAMETERS_NAME);

    String WSA_METADATA_NAME = "Metadata";
    QName WSA_METADATA_QNAME = new QName(WSA_NAMESPACE_NAME, WSA_METADATA_NAME);

    String WSA_ADDRESS_NAME = "Address";
    QName WSA_ADDRESS_QNAME = new QName(WSA_NAMESPACE_NAME, WSA_ADDRESS_NAME);

    String WSA_ANONYMOUS_ADDRESS = WSA_NAMESPACE_NAME + "/anonymous";
    String WSA_NONE_ADDRESS = WSA_NAMESPACE_NAME + "/none";

    String WSA_DEFAULT_FAULT_ACTION = WSA_NAMESPACE_NAME + "/fault";

    String WSA_EPR_NAME = "EndpointReference";
    QName WSA_EPR_QNAME = new QName(WSA_NAMESPACE_NAME, WSA_EPR_NAME);


    String WSAW_USING_ADDRESSING_NAME = "UsingAddressing";
    QName WSAW_USING_ADDRESSING_QNAME = new QName(WSA_NAMESPACE_WSDL_NAME, WSAW_USING_ADDRESSING_NAME);

    QName INVALID_MAP_QNAME = new QName(WSA_NAMESPACE_NAME, "InvalidAddressingHeader");
    QName MAP_REQUIRED_QNAME = new QName(WSA_NAMESPACE_NAME, "MessageAddressingHeaderRequired");
    QName DESTINATION_UNREACHABLE_QNAME = new QName(WSA_NAMESPACE_NAME, "DestinationUnreachable");
    QName ACTION_NOT_SUPPORTED_QNAME = new QName(WSA_NAMESPACE_NAME, "ActionNotSupported");
    QName ENDPOINT_UNAVAILABLE_QNAME = new QName(WSA_NAMESPACE_NAME, "EndpointUnavailable");

    String ACTION_NOT_SUPPORTED_TEXT = "The \"%s\" cannot be processed at the receiver";
    String DESTINATION_UNREACHABLE_TEXT = "No route can be determined to reach %s";
    String ENDPOINT_UNAVAILABLE_TEXT = "The endpoint is unable to process the message at this time";
    String INVALID_MAP_TEXT = "A header representing a Message Addressing Property is not valid and the message cannot be processed";
    String MAP_REQUIRED_TEXT = "A required header representing a Message Addressing Property is not present";

    QName PROBLEM_ACTION_QNAME = new QName(WSA_NAMESPACE_NAME, "ProblemAction");
    QName PROBLEM_HEADER_QNAME_QNAME = new QName(WSA_NAMESPACE_NAME, "ProblemHeaderQName");
    QName FAULT_DETAIL_QNAME = new QName(WSA_NAMESPACE_NAME, "FaultDetail");

    // Fault subsubcode when an invalid address is specified.
    QName INVALID_ADDRESS_SUBCODE = new QName(WSA_NAMESPACE_NAME, "InvalidAddress",
                                                                  AddressingVersion.W3C.getPrefix());

    // Fault subsubcode when an invalid header was expected to be EndpointReference but was not valid.
    QName INVALID_EPR = new QName(WSA_NAMESPACE_NAME, "InvalidEPR", AddressingVersion.W3C.getPrefix());

    // Fault subsubcode when greater than expected number of the specified header is received.
    QName INVALID_CARDINALITY = new QName(WSA_NAMESPACE_NAME, "InvalidCardinality",
                                                              AddressingVersion.W3C.getPrefix());

    // Fault subsubcode when an invalid header was expected to be EndpointReference but did not contain address.
    QName MISSING_ADDRESS_IN_EPR = new QName(WSA_NAMESPACE_NAME, "MissingAddressInEPR",
                                                                 AddressingVersion.W3C.getPrefix());

    // Fault subsubcode when a header contains a message id that was a duplicate of one already received.
    QName DUPLICATE_MESSAGEID = new QName(WSA_NAMESPACE_NAME, "DuplicateMessageID",
                                                              AddressingVersion.W3C.getPrefix());

    // Fault subsubcode when <code>Action</code> and <code>SOAPAction</code> for the mesage did not match.
    QName ACTION_MISMATCH = new QName(WSA_NAMESPACE_NAME, "ActionMismatch",
                                                          AddressingVersion.W3C.getPrefix());

    // Fault subsubcode when the only address supported is the anonymous address.
    QName ONLY_ANONYMOUS_ADDRESS_SUPPORTED = new QName(WSA_NAMESPACE_NAME, "OnlyAnonymousAddressSupported",
                                                                           AddressingVersion.W3C.getPrefix());

    //Fault subsubcode when anonymous address is not supported.
    QName ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED = new QName(WSA_NAMESPACE_NAME, "OnlyNonAnonymousAddressSupported",
                                                                               AddressingVersion.W3C.getPrefix());

    String ANONYMOUS_EPR = "<EndpointReference xmlns=\"http://www.w3.org/2005/08/addressing\">\n" +
            "    <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>\n" +
            "</EndpointReference>";
}
