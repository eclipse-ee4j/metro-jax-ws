/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model.jaxb;

/**
 * @author Kohsuke Kawaguchi
 */
class Util {
    /**
     * Replaces the marcros in the first string by the actual given arguments.
     */
    static String replace( String macro, String... args ) {
        int len = macro.length();
        StringBuilder buf = new StringBuilder(len);
        for( int i=0; i<len; i++ ) {
            char ch = macro.charAt(i);
            if(ch=='=' && i+2<len) {
                char tail = macro.charAt(i+1);
                char ch2 = macro.charAt(i+2);
                if('0'<=ch2 && ch2<='9' && tail==':') {
                    buf.append(args[ch2-'0']);
                    i+=2;
                    continue;
                }
            }
            buf.append(ch);
        }
        return buf.toString();
    }

    /**
     * Creates a macro tempate so that it can be later used with {@link #replace(String, String[])}.
     */
    static String createMacroTemplate( String s ) {
        return s;
    }

    static final String MAGIC = "=:";

    static final String MAGIC0 = MAGIC+"0";
    static final String MAGIC1 = MAGIC+"1";
    static final String MAGIC2 = MAGIC+"2";
}
