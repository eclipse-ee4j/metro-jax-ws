/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.crinterop.s11.common;

import testutil.MemberSubmissionAddressingConstants;
import testutil.WsaSOAPMessages;

/**
 * @author Arun Gupta
 */
public class TestConstants {
    public static final String NOTIFY_ACTION = "http://example.org/action/notify";
    public static final String ECHO_IN_ACTION = "http://example.org/action/echoIn";
    public static final String ECHO_OUT_ACTION = "http://example.org/action/echoOut";
    public static final WsaSOAPMessages MESSAGES = new WsaSOAPMessages(MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME);
}
