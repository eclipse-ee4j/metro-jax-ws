/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.wsa.action.common;

/**
 * @author Arun Gupta
 */
public class TestConstants {
    public static final String ADD_NUMBERS_IN_NOACTION = "http://foobar.org/AddNumbers/addNumbersNoActionRequest";
    public static final String ADD_NUMBERS_IN_EMPTYACTION = "http://foobar.org/AddNumbers/addNumbersEmptyActionRequest";
    public static final String ADD_NUMBERS_IN_ACTION = "http://example.com/input";
    public static final String ADD_NUMBERS2_IN_ACTION = "http://example.com/input2";
    public static final String ADD_NUMBERS3_IN_ACTION = "http://example.com/input3";
    public static final String ADD_NUMBERS_FAULT1_IN_ACTION = "finput1";
    public static final String ADD_NUMBERS_FAULT2_IN_ACTION = "finput2";
    public static final String ADD_NUMBERS_FAULT3_IN_ACTION = "finput3";
    public static final String ADD_NUMBERS_FAULT4_IN_ACTION = "http://foobar.org/AddNumbers/addNumbersFault4Request";
    public static final String ADD_NUMBERS_FAULT5_IN_ACTION = "http://foobar.org/AddNumbers/addNumbersFault5Request";
    public static final String ADD_NUMBERS_FAULT6_IN_ACTION = "http://foobar.org/AddNumbers/addNumbersFault6Request";
    public static final String ADD_NUMBERS_FAULT7_IN_ACTION = "http://foobar.org/AddNumbers/addNumbersFault7Request";

    public static final String ADD_NUMBERS_OUT_NOACTION = "http://foobar.org/AddNumbers/addNumbersNoActionResponse";
    public static final String ADD_NUMBERS_OUT_EMPTYACTION = "http://foobar.org/AddNumbers/addNumbersEmptyActionResponse";
    public static final String ADD_NUMBERS_OUT_ACTION = "http://example.com/output";
    public static final String ADD_NUMBERS2_OUT_ACTION = "http://example.com/output2";
    public static final String ADD_NUMBERS3_OUT_ACTION = "http://foobar.org/AddNumbers/addNumbers3Response";
    public static final String ADD_NUMBERS_FAULT1_ADDNUMBERS_ACTION = "http://fault1";
    public static final String ADD_NUMBERS_FAULT2_ADDNUMBERS_ACTION = "http://fault2/addnumbers";
    public static final String ADD_NUMBERS_FAULT2_TOOBIGNUMBERS_ACTION = "http://fault2/toobignumbers";
    public static final String ADD_NUMBERS_FAULT3_ADDNUMBERS_ACTION = "http://fault3/addnumbers";
    public static final String ADD_NUMBERS_FAULT3_TOOBIGNUMBERS_ACTION = "http://foobar.org/AddNumbers/addNumbersFault3/Fault/TooBigNumbersException";
    public static final String ADD_NUMBERS_FAULT4_ADDNUMBERS_ACTION = "http://fault4/addnumbers";
    public static final String ADD_NUMBERS_FAULT4_TOOBIGNUMBERS_ACTION = "http://foobar.org/AddNumbers/addNumbersFault4/Fault/TooBigNumbersException";
    public static final String ADD_NUMBERS_FAULT5_ADDNUMBERS_ACTION = "http://foobar.org/AddNumbers/addNumbersFault5/Fault/AddNumbersException";
    public static final String ADD_NUMBERS_FAULT5_TOOBIGNUMBERS_ACTION = "http://fault5/toobignumbers";
    public static final String ADD_NUMBERS_FAULT6_ADDNUMBERS_ACTION = "http://fault6/addnumbers";
    public static final String ADD_NUMBERS_FAULT6_TOOBIGNUMBERS_ACTION = "http://fault6/toobignumbers";
    public static final String ADD_NUMBERS_FAULT7_ADDNUMBERS_ACTION = "http://foobar.org/AddNumbers/addNumbersFault7/Fault/AddNumbersException";
    public static final String ADD_NUMBERS_FAULT7_TOOBIGNUMBERS_ACTION = "http://foobar.org/AddNumbers/addNumbersFault7/Fault/TooBigNumbersException";
}
