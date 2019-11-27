/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding.policy;

import javax.xml.namespace.QName;

/**
 * File holding all encoding constants
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public final class EncodingConstants {    
    /** Prevents creation of new EncodingConstants instance */
    private EncodingConstants() {
    }
    
    public static final String SUN_FI_SERVICE_NS = "http://java.sun.com/xml/ns/wsit/2006/09/policy/fastinfoset/service";
    public static final QName OPTIMIZED_FI_SERIALIZATION_ASSERTION = new QName(SUN_FI_SERVICE_NS, "OptimizedFastInfosetSerialization");
    
    public static final String SUN_ENCODING_CLIENT_NS = "http://java.sun.com/xml/ns/wsit/2006/09/policy/encoding/client";
    public static final QName SELECT_OPTIMAL_ENCODING_ASSERTION = new QName(SUN_ENCODING_CLIENT_NS, "AutomaticallySelectOptimalEncoding");    
    
    public static final String OPTIMIZED_MIME_NS = "http://schemas.xmlsoap.org/ws/2004/09/policy/optimizedmimeserialization";
    public static final QName OPTIMIZED_MIME_SERIALIZATION_ASSERTION = new QName(OPTIMIZED_MIME_NS, "OptimizedMimeSerialization");
    
    public static final String ENCODING_NS = "http://schemas.xmlsoap.org/ws/2004/09/policy/encoding";
    public static final QName UTF816FFFE_CHARACTER_ENCODING_ASSERTION = new QName(ENCODING_NS, "Utf816FFFECharacterEncoding");
}
