/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.w3c.fromjava.addressing_required.client;

import junit.framework.TestCase;

/**
 * @author Rama Pulavarthi
 */
public class AddressingTest extends TestCase {
    public AddressingTest(String name) {
            super(name);
        }

        private AddNumbers createStub() throws Exception {
            return new AddNumbersService().getAddNumbersPort();
        }

        public void testBasic() throws Exception {
            int result = createStub().addNumbers(10, 10);
            assertEquals(20, result);
        }

}
