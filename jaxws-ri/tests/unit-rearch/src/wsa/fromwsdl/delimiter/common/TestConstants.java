/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.delimiter.common;

/**
 * @author Arun Gupta
 */
public class TestConstants {
    public static final String ADD_NUMBERS_IN_ACTION = "urn:example.com:AddNumbersPortType:add";
    public static final String ADD_NUMBERS2_IN_ACTION = "urn:addNumbers";

    public static final String ADD_NUMBERS_OUT_ACTION = "urn:example.com:AddNumbersPortType:addResponse";
    public static final String ADD_NUMBERS_ADDNUMBERS_ACTION = "urn:example.com:AddNumbersPortType:addNumbers:Fault:addFault";
    public static final String ADD_NUMBERS_TOOBIGNUMBERS_ACTION = "urn:example.com:AddNumbersPortType:addNumbers:Fault:tooBigFault";
    public static final String ADD_NUMBERS2_OUT_ACTION = "urn:addNumbersResponse";
    public static final String ADD_NUMBERS2_ADDNUMBERS_ACTION = "urn:addNumbersFault";
    public static final String ADD_NUMBERS2_TOOBIGNUMBERS_ACTION = "urn:tooBigNumbersFault";
}
