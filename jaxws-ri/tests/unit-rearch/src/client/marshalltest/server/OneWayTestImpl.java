/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.marshalltest.server;


import client.marshalltest.server.*;

import jakarta.xml.ws.WebServiceException;

import jakarta.xml.soap.*;

import java.util.*;

// Service Implementation Class - as outlined in JAX-RPC Specification

import jakarta.jws.WebService;

@WebService(
    serviceName="MarshallTestService",
    endpointInterface="client.marshalltest.server.OneWayTest"
)
public class OneWayTestImpl implements OneWayTest {

    public void oneWayMethod(OneWayMessage v)  {

	   System.out.println("OneWayMessage: " + v.getStringValue());
    }
}
