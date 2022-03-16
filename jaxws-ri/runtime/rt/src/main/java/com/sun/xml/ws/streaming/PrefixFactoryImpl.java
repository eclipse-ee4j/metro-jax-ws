/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.streaming;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> A prefix factory that caches the prefixes it creates. </p>
 *
 * @author WS Development Team
 */
public class PrefixFactoryImpl implements PrefixFactory {

    public PrefixFactoryImpl(String base) {
        _base = base;
        _next = 1;
    }

    public String getPrefix(String uri) {
        String prefix = null;

        if (_cachedUriToPrefixMap == null) {
            _cachedUriToPrefixMap = new HashMap();
        } else {
            prefix = (String) _cachedUriToPrefixMap.get(uri);
        }

        if (prefix == null) {
            prefix = _base + _next++;
            _cachedUriToPrefixMap.put(uri, prefix);
        }

        return prefix;
    }

    private String _base;
    private int _next;
    private Map _cachedUriToPrefixMap;
}
