/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.util;

import com.sun.tools.ws.util.ForkEntityResolver;
import java.io.IOException;
import junit.framework.TestCase;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author lukas
 */
public class ForkEntityResolverTest extends TestCase {

    public ForkEntityResolverTest(String testName) {
        super(testName);
    }

    public void testResolve() throws SAXException, IOException {
        EntityResolver a = new ER("A-public", "A-system");
        EntityResolver b = new ER("B-public", "B-system");
        EntityResolver fork = new ForkEntityResolver(a, b);
        assertNull(fork.resolveEntity("A-public", "A-public"));
        assertNull(fork.resolveEntity("B-public", "B-public"));
        assertNotNull(fork.resolveEntity("A-public", "A-system"));
        assertNotNull(fork.resolveEntity("B-public", "B-system"));
    }

    private class ER implements EntityResolver {

        private final String sId;
        private final String pId;

        public ER(String publicId, String systemId) {
            sId = systemId;
            pId = publicId;
        }

        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            if (sId.equals(systemId) && pId.equals(publicId)) {
                return new InputSource();
            }
            return null;
        }
    }
}
