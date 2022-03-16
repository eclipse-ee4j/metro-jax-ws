/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding.policy;

import com.sun.xml.ws.policy.spi.PrefixMapper;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fabian Ritzmann
 */
public class EncodingPrefixMapper implements PrefixMapper {

    private static final Map<String, String> prefixMap = new HashMap<>();

    static {
        prefixMap.put(EncodingConstants.ENCODING_NS, "wspe");
        prefixMap.put(EncodingConstants.OPTIMIZED_MIME_NS, "wsoma");
        prefixMap.put(EncodingConstants.SUN_ENCODING_CLIENT_NS, "cenc");
        prefixMap.put(EncodingConstants.SUN_FI_SERVICE_NS, "fi");
    }
        
    public Map<String, String> getPrefixMap() {
        return prefixMap;
    }

}
