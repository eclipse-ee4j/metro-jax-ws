/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document;

/**
 * Enumeration of the supported WSDL operation styles.
 *
 * @author WS Development Team
 */
public final class OperationStyle {

    public static final OperationStyle ONE_WAY = new OperationStyle();
    public static final OperationStyle REQUEST_RESPONSE = new OperationStyle();
    public static final OperationStyle SOLICIT_RESPONSE = new OperationStyle();
    public static final OperationStyle NOTIFICATION = new OperationStyle();

    private OperationStyle() {
    }
}
