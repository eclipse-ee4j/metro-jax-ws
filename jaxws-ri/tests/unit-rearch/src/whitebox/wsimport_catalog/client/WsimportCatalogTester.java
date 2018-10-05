/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.wsimport_catalog.client;

import junit.framework.TestCase;

public class WsimportCatalogTester extends TestCase{
    public WsimportCatalogTester(String s) {
        super(s);
    }

    /**
     * Just check if the class is loaded, meaning both -p worked and catalog resolver worked too
     */
    public void testCatalog(){
        try {
            Class cls = Class.forName("whitebox.wsimport_catalog.Hello");
            assertTrue(true);
        } catch (ClassNotFoundException e) {
            assertTrue(false);
        }
    }

}
