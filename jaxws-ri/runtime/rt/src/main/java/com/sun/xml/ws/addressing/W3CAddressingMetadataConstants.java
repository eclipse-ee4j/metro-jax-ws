/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing;

import javax.xml.namespace.QName;

/**
 * Constants for W3C Addressing Metadata specification
 * @author Rama Pulavarthi
 */
public class W3CAddressingMetadataConstants {
    public static final String WSAM_NAMESPACE_NAME = "http://www.w3.org/2007/05/addressing/metadata";
    public static final String WSAM_PREFIX_NAME = "wsam";
    public static final QName  WSAM_ACTION_QNAME = new QName(WSAM_NAMESPACE_NAME,"Action",WSAM_PREFIX_NAME);

    public static final String WSAM_ADDRESSING_ASSERTION_NAME="Addressing";
    public static final String WSAM_ANONYMOUS_NESTED_ASSERTION_NAME="AnonymousResponses";
    public static final String WSAM_NONANONYMOUS_NESTED_ASSERTION_NAME="NonAnonymousResponses";

    public static final QName WSAM_ADDRESSING_ASSERTION = new QName(WSAM_NAMESPACE_NAME,
                    WSAM_ADDRESSING_ASSERTION_NAME, WSAM_PREFIX_NAME );

    public static final QName WSAM_ANONYMOUS_NESTED_ASSERTION = new QName(WSAM_NAMESPACE_NAME,
                        WSAM_ANONYMOUS_NESTED_ASSERTION_NAME, WSAM_PREFIX_NAME );

    public static final QName WSAM_NONANONYMOUS_NESTED_ASSERTION = new QName(WSAM_NAMESPACE_NAME,
                        WSAM_NONANONYMOUS_NESTED_ASSERTION_NAME, WSAM_PREFIX_NAME );

    public static final String WSAM_WSDLI_ATTRIBUTE_NAMESPACE="http://www.w3.org/ns/wsdl-instance";
    public static final String WSAM_WSDLI_ATTRIBUTE_PREFIX="wsdli";
    public static final String WSAM_WSDLI_ATTRIBUTE_LOCALNAME="wsdlLocation";

}
