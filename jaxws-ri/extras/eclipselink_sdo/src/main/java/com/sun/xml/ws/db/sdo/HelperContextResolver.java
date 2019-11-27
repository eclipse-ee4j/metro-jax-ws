/*
 * Copyright (c) 2012, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.sdo;

import java.util.Map;

import javax.xml.namespace.QName;

import commonj.sdo.helper.HelperContext;

public interface HelperContextResolver {
    HelperContext getHelperContext(boolean isClient, QName serviceName,
            Map<String, Object> properties);
}
