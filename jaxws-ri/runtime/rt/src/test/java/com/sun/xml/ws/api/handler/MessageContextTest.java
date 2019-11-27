/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.handler;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import junit.framework.Assert;

import junit.framework.TestCase;

import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.handler.SOAPMessageContextImpl;

public class MessageContextTest extends TestCase {

    public void test() {
        final String endpointAddress = "http://localhost:7001/test";
        Packet p = new Packet();
        p.setEndPointAddressString(endpointAddress);
        
        MessageContext context = new SOAPMessageContextImpl(null, p, null);
        
        Assert.assertEquals(endpointAddress, context.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
    }

}
