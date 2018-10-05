/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.soapaction.common;

/**
 * @author Arun Gupta
 */
public interface TestConstants {
    public static final String ADD_NUMBERS_IN_ACTION = "bindingSOAPAction";
    public static final String ADD_NUMBERS2_IN_ACTION = "add2InAction";
    public static final String ADD_NUMBERS3_IN_ACTION = "http://example.com/AddNumbersPortType/addNumbers3Request";
    public static final String ADD_NUMBERS4_IN_ACTION = "http://example.com/AddNumbersPortType/addNumbers4Request";

    public static final String ADD_NUMBERS_OUT_ACTION = "http://example.com/AddNumbersPortType/addNumbersResponse";
    public static final String ADD_NUMBERS2_OUT_ACTION = "add2OutAction";
    public static final String ADD_NUMBERS3_OUT_ACTION = "http://example.com/AddNumbersPortType/addNumbers3Response";
    public static final String ADD_NUMBERS4_OUT_ACTION = "http://example.com/AddNumbersPortType/addNumbers4Response";
}
