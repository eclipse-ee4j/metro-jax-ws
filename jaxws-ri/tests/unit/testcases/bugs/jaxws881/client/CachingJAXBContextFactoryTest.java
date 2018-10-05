/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws881.client;

/**
 * Testcase for reusing JAXBContext
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
import com.sun.xml.ws.developer.UsesJAXBContextFeature;
import junit.framework.TestCase;

import javax.xml.namespace.QName;
import java.net.URL;

public class CachingJAXBContextFactoryTest extends TestCase {

    private UsesJAXBContextFeature feature;
    private EchoImplService service;

    public void setUp() {
        this.service = new EchoImplService();
        this.feature = new UsesJAXBContextFeature(new CachingJAXBContextFactory());
    }

    // works
    public void testCreateOnce() {
        this.service.getEchoImplPort(this.feature);
    }

    public void testCreateTwice() {
        this.service.getEchoImplPort(this.feature);

        // reusing JAXBContext - NPE occured before JAXB fix:
        this.service.getEchoImplPort(this.feature);
    }
}
