/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.xml_java_type_adapter_currency.client;

import junit.framework.TestCase;

/**
 * @author Jitendra Kotamraju
 */
public class CurrencyTest extends TestCase {

    public CurrencyTest(String name) throws Exception{
        super(name);
    }

    public void testCurrency() throws Exception {
        Echo echoPort = new EchoService().getEchoPort(); 
        String currency = echoPort.getCurrency();
        assertEquals("USD", currency);
    }

}
