/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.fault.common;

/**
 * @author Arun Gupta
 */
public class TestConstants {
    public static final String ADD_NUMBERS_IN_ACTION = "http://example.com/AddNumbersPortType/add";
    public static final String ADD_NUMBERS2_IN_ACTION = "http://example.com/AddNumbersPortType/add2";
    public static final String ADD_NUMBERS3_IN_ACTION = "http://example.com/AddNumbersPortType/addNumbers3Request";
    public static final String ADD_NUMBERS4_IN_ACTION = "http://example.com/AddNumbersPortType/addNumbers4Request";
    public static final String ADD_NUMBERS5_IN_ACTION = "http://example.com/AddNumbersPortType/addNumbers5Request";
    public static final String ADD_NUMBERS6_IN_ACTION = "http://example.com/AddNumbersPortType/addNumbers6Request";

    public static final String ADD_NUMBERS_ADDNUMBERS_ACTION = "http://example.com/AddNumbersPortType/addNumbers/Fault/addFault";
    public static final String ADD_NUMBERS_TOOBIGNUMBERS_ACTION = "http://example.com/AddNumbersPortType/addNumbers/Fault/tooBigFault";
    public static final String ADD_NUMBERS2_ADDNUMBERS_ACTION = "add2fault";
    public static final String ADD_NUMBERS2_TOOBIGNUMBERS_ACTION = "toobig2fault";
    public static final String ADD_NUMBERS3_ADDNUMBERS_ACTION = "add3fault";
    public static final String ADD_NUMBERS3_TOOBIGNUMBERS_ACTION = "http://example.com/AddNumbersPortType/addNumbers3/Fault/tooBig3Fault";
    public static final String ADD_NUMBERS4_ADDNUMBERS_ACTION = "http://example.com/AddNumbersPortType/addNumbers4/Fault/add4Fault";
    public static final String ADD_NUMBERS4_TOOBIGNUMBERS_ACTION = "toobig4fault";
    public static final String ADD_NUMBERS5_ADDNUMBERS_ACTION = "fault5";
    public static final String ADD_NUMBERS6_ADDNUMBERS_ACTION = "http://example.com/AddNumbersPortType/addNumbers6/Fault/add6Fault";
}
