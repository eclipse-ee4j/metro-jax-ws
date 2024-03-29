/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.util;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * {@link EntityResolver} that delegates to two {@link EntityResolver}s.
 *
 * @author WS Development Team
 */
public class ForkEntityResolver implements EntityResolver {
    private final EntityResolver lhs;
    private final EntityResolver rhs;

    public ForkEntityResolver(EntityResolver lhs, EntityResolver rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        InputSource is = lhs.resolveEntity(publicId, systemId);
        if(is!=null)
            return is;

        if(publicId == null) {
          // WorkAround: Ant's XMLCatalog supports public IDs only, this allows us to treat it as system IDs
          publicId = systemId;
        }

        return rhs.resolveEntity(publicId, systemId);
    }
}
