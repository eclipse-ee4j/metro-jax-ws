/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing.policy;

import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.policy.spi.PrefixMapper;
import com.sun.xml.ws.addressing.W3CAddressingMetadataConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * This supplies the prefixes for the namespaces under Addressing domain.
 * 
 * @author Fabian Ritzmann
 * @author Rama Pulavarthi
 */
public class AddressingPrefixMapper implements PrefixMapper {

    private static final Map<String, String> prefixMap = new HashMap<>();

    static {
        prefixMap.put(AddressingVersion.MEMBER.policyNsUri, "wsap");
        prefixMap.put(AddressingVersion.MEMBER.nsUri, "wsa");
        prefixMap.put(W3CAddressingMetadataConstants.WSAM_NAMESPACE_NAME,W3CAddressingMetadataConstants.WSAM_PREFIX_NAME);
    }
        
    public Map<String, String> getPrefixMap() {
        return prefixMap;
    }
    
}
