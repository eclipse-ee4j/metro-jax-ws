/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.w3c.fromwsdl.crinterop_s11.client;

import testutil.WsaW3CSOAPMessages;
import com.sun.xml.ws.api.addressing.AddressingVersion;

public class TestConstants {
    public static final String NOTIFY_ACTION = "http://example.org/action/notify";
    public static final String ECHO_IN_ACTION = "http://example.org/action/echoIn";
    public static final String ECHO_OUT_ACTION = "http://example.org/action/echoOut";
    public static final AddressingVersion ADDRESSING_VERSION = AddressingVersion.W3C;
    public static final WsaW3CSOAPMessages MESSAGES = new WsaW3CSOAPMessages(ADDRESSING_VERSION);
    public static final long PROVIDER_MAX_TIMEOUT =20L;
    public static final long CLIENT_MAX_TIMEOUT = 40L;
}
