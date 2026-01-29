/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message;

import com.sun.xml.ws.api.message.Message;

/**
 * Utility code for the {@link Message} implementation.
 */
public final class Util {

    private Util() {}

    /**
     * Parses a stringthat represents a boolean into boolean.
     * This method assumes that the whilespace normalization has already taken place.
     *
     */
    public static boolean parseBool(String value) {
        if(value.length()==0)
            return false;

        char ch = value.charAt(0);
        return ch=='t' || ch=='1';
    }

}
