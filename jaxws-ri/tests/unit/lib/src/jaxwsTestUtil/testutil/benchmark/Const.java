/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
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
    public final String string = "hello there!";
    public final String[] stringArray = { "hello there!", "hello where ?" };
    public final int intNumber = 10;
    public final Integer[] intArray = { 10, 20 };
    public final float floatNumber = 10.0f;
    public final Float[] floatArray = { 10.0f, 20.0f };

    public final byte[] base64Binary = Util.fillArray(new byte[10240]);
    public final Calendar date = Calendar.getInstance();
    public final BigDecimal decimal = new BigDecimal("1234.56");

}
