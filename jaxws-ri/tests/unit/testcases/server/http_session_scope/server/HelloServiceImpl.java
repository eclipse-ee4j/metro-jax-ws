/*
 * Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.http_session_scope.server;

import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.Resource;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceContext;

import com.sun.xml.ws.developer.servlet.HttpSessionScope;

/**
 * @HttpSessionScope test
 *
 * @author Jitendra Kotamraju
 */
@WebService(name="Hello", serviceName="HelloService", targetNamespace="urn:test")
@HttpSessionScope
public class HelloServiceImpl {
    int counter = 0;
    public int getCounter() {
        return counter++;
    }
}
