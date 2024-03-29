/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService(targetNamespace="http://www.oracle.com/webservices/tests")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
public class CollectionMapImpl {
	public List<String> echoListOfString(List<String> l) {
		return l;
	}
    public Map<Integer, String> echoMapOfString(Map<String, Integer> m) {
        HashMap<Integer, String> r = new HashMap<Integer, String>(); 
        for(String k : m.keySet()) {
            r.put(m.get(k), k);
        }
        return r;
    }
}
