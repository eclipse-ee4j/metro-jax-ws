/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.async_client_transport;

import javax.xml.ws.WebServiceException;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Rama.Pulavarthi@sun.com
 */
public class NonAnonymousAddressAllocator {
    private static final NonAnonymousAddressAllocator INSTANCE = new NonAnonymousAddressAllocator();

    private NonAnonymousAddressAllocator() {

    }

    public static NonAnonymousAddressAllocator getInstance() {
        return INSTANCE;
    }

    private String address;

    private String getAddress() {
        if (address == null)
            address = getAvailableAddress();
        return address;
    }

    private static String getAvailableAddress() {
        try {
            ServerSocket server = new ServerSocket(0);
            String host = InetAddress.getLocalHost().getHostAddress();
            int port = server.getLocalPort();
            server.close();
            return "http://" + host + ":" + port;
        } catch (IOException e) {
            throw new WebServiceException(e);
        }
    }

    public String createNonAnonymousAddress() {
        return getAddress() + "/nonanonymous_" + UUID.randomUUID().toString();
    }
}
