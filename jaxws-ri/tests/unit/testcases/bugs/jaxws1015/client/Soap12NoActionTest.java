/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1015.client;

import junit.framework.TestCase;

/**
 * Simple client just to invoke web service; the real check is in the SOAPHandler on the server side
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class Soap12NoActionTest extends TestCase {

    public void test() throws AddNumbersFault_Exception {
        AddNumbersPortType port = new AddNumbersService().getAddNumbersPort();
        int result = port.addNumbers(1, 2);
        assertEquals(3, result);
    }

}
