/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.spi.db;

import java.util.Map;

import com.oracle.webservices.api.databinding.Databinding;
import com.oracle.webservices.api.databinding.WSDLGenerator;

import com.sun.xml.ws.api.databinding.DatabindingConfig;

public interface DatabindingProvider {
	//We will need this for ServiceFinder
	boolean isFor(String databindingMode);
	void init(Map<String, Object> properties);
	Databinding create(DatabindingConfig config);
    WSDLGenerator wsdlGen(DatabindingConfig config);
}
