/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.test;

import static jakarta.jws.soap.SOAPBinding.Style.RPC;
import static jakarta.jws.soap.SOAPBinding.Use.LITERAL;

import java.util.List;
import java.util.Map;

import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService(targetNamespace="http://www.oracle.com/webservices/tests")
@SOAPBinding(style = RPC, use = LITERAL)
public interface CollectionMap {
	List<String> echoListOfString(List<String> l);
    Map<Integer, String> echoMapOfString(Map<String, Integer> l);
}
