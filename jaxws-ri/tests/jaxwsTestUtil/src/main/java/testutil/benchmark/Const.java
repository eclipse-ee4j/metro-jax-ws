/*
 * Copyright (c) 2018, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil.benchmark;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * Constant for tests.
 *
 * @author Arun Gupta
 */
public interface Const {
    String string = "hello there!";
    String[] stringArray = { "hello there!", "hello where ?" };
    int intNumber = 10;
    Integer[] intArray = { 10, 20 };
    float floatNumber = 10.0f;
    Float[] floatArray = { 10.0f, 20.0f };

    byte[] base64Binary = Util.fillArray(new byte[10240]);
    Calendar date = Calendar.getInstance();
    BigDecimal decimal = new BigDecimal("1234.56");

}
