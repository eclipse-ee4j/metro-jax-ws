/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.fault_header.client;

import junit.framework.TestCase;

/**
 * TODO: Write some description here ...
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class ClientTest extends TestCase {
    
    public void testIt() {
        try {
            FaultHeader port = new FaultHeaderService().getFaultHeaderPort();
            port.myWebMethod();
        } catch (MyWebMethodFault_Exception e) {
        }
    }

}
