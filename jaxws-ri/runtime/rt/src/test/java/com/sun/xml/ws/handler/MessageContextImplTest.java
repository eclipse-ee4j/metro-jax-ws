/*
 * Copyright (c) 2014, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.handler;

import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.model.wsdl.WSDLDirectProperties;
import junit.framework.TestCase;

/**
 * Simple test to reproduce PIT failure. The problem is that Distributed
 */
public class MessageContextImplTest extends TestCase {

    private static final String DIRECT_CHILD = "com.sun.xml.ws.client.ContentNegotiation";
    private static final String SATELLITES_CHILD = "javax.xml.ws.wsdl.service";

    public void test() {

        try {
            Packet packet = new Packet();
            packet.addSatellite(new WSDLDirectProperties(null, null));
            MessageContextImpl ctx = new MessageContextImpl(packet);
            System.out.println(ctx.keySet());
            System.out.println(ctx.entrySet());

            checkProperty(ctx, DIRECT_CHILD);

            // this property wasn't found before fix
            checkProperty(ctx, SATELLITES_CHILD);

            // check all ...
            checkAll(ctx);

        } catch(Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }

    }

    private void checkAll(MessageContextImpl ctx) {
        for (String key : ctx.keySet()) {
            checkProperty(ctx, key);
        }
    }

    private void checkProperty(MessageContextImpl ctx, String key) {
        // key known by the MessageContextImpl object
        System.out.println("\n=== property =====================");
        System.out.println("key = " + key);
        System.out.println("value = " + ctx.get(key));

        // keySet knows the key, although it is reported as not in the context?! It seems weird to me ...
        System.out.println("ctx.containsKey(key) = " + ctx.containsKey(key));

        // this regularly fails ...
        System.out.println("ctx.getScope(key) = " + ctx.getScope(key));
    }

}
