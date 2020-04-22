/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.spi;

import java.util.Map;

/**
 * Maps an XML prefix to a namespace.
 * 
 * This class allows policy domains to configure to which XML prefix an XML
 * namespace is mapped.
 *
 * @author Fabian Ritzmann
 */
public interface PrefixMapper {

    /**
     * Returns a map of XML prefixes to namespaces for the domain.
     * 
     * The keys of the map must be a name for an XML prefix, e.g. "wsrmp". The
     * values must be the name of an XML namespace, e.g.
     * "http://docs.oasis-open.org/ws-rx/wsrmp/200702".
     * 
     * @return A map of XML prefixes to namespaces for the domain.
     */
    Map<String, String> getPrefixMap();
}
