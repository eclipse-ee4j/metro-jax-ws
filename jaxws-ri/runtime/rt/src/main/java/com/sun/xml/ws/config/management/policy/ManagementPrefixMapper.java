/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.config.management.policy;

import com.sun.xml.ws.policy.PolicyConstants;
import com.sun.xml.ws.policy.spi.PrefixMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Provide the default prefix for the configuration management namespace.
 *
 * @author Fabian Ritzmann
 */
public class ManagementPrefixMapper implements PrefixMapper {

    private static final Map<String, String> prefixMap = new HashMap<>();

    static {
        prefixMap.put(PolicyConstants.SUN_MANAGEMENT_NAMESPACE, "sunman");
    }

    public Map<String, String> getPrefixMap() {
        return prefixMap;
    }

}
