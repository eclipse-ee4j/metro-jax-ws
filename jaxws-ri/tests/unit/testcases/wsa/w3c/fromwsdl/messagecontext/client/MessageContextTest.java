/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.w3c.fromwsdl.messagecontext.client;

import junit.framework.TestCase;

import jakarta.xml.ws.BindingProvider;

/**
 * Tests WSA porpeties in MessageContext with Addressing enabled.
 * @author Rama Pulavarthi
 */
public class MessageContextTest extends TestCase {
    public MessageContextTest(String name) throws Exception {
        super(name);
    }

    AddNumbersPortType getStub() throws Exception {
        return new AddNumbersService().getAddNumbersPort();
    }

    private String getEndpointAddress() throws Exception{

        BindingProvider bp = ((BindingProvider) getStub());
        return
            (String) bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
    }

   public void testRequestResponse() throws Exception {
        AddNumbersPortType myport = getStub();
        myport.addNumbers(10, 10);
    }
}
