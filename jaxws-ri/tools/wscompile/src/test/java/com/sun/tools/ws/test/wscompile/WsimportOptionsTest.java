/*
 * Copyright (c) 2013, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.wscompile;

import com.sun.tools.ws.wscompile.WsimportListener;
import com.sun.tools.ws.wscompile.WsimportOptions;
import com.sun.tools.xjc.api.SchemaCompiler;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author lukas
 */
public class WsimportOptionsTest extends TestCase {

    public WsimportOptionsTest(String testName) {
        super(testName);
    }

    /**
     * Test of parseArguments(String[]) method, of class WsimportOptions.
     */
    public void testParseArgument_jaxws1102() throws Exception {
        WsimportOptions options = new WsimportOptions();
        SchemaCompiler sc = options.getSchemaCompiler();
        options.parseArguments(new String[0]);
        assertNull("ws encoding set", options.encoding);
        assertNull("xjc encoding set", sc.getOptions().encoding);

        options = new WsimportOptions();
        sc = options.getSchemaCompiler();
        String[] args = {"-encoding", "UTF-8"};
        options.parseArguments(args);
        assertNotNull("ws encoding not set", options.encoding);
        assertEquals("UTF-8", options.encoding);
        assertNotNull("xjc encoding not set", sc.getOptions().encoding);
        assertEquals("UTF-8", sc.getOptions().encoding);

        options = new WsimportOptions();
        sc = options.getSchemaCompiler();
        args = new String[] {"-encoding", "UTF-8", "-B-encoding", "UTF-16"};
        options.parseArguments(args);
        assertNotNull("ws encoding not set", options.encoding);
        assertEquals("UTF-8", options.encoding);
        assertNotNull("xjc encoding not set", sc.getOptions().encoding);
        assertEquals("UTF-16", sc.getOptions().encoding);

        options = new WsimportOptions();
        sc = options.getSchemaCompiler();
        args = new String[] {"-B-encoding", "UTF-16"};
        options.parseArguments(args);
        assertNull("ws encoding set", options.encoding);
        assertNotNull("xjc encoding not set", sc.getOptions().encoding);
        assertEquals("UTF-16", sc.getOptions().encoding);
    }

    /**
     * Test of parseArguments(String[]) method, of class WsimportOptions.
     */
    public void testParseArgument_javacArgs() throws Exception {
        WsimportOptions options = new WsimportOptions();
        String[] args = new String[] {"-g", "-J-g", "-J-source=1.6", "-J-target=1.6", "-J-XprintRounds"};
        options.parseArguments(args);
        assertFalse("javac options not set", options.javacOptions.isEmpty());
        assertEquals("invalid option recognized", 4, options.javacOptions.size());
        L l = new L();
        List<String> jopts = options.getJavacOptions(new ArrayList<String>(){{add("-g");}}, l);
        assertEquals("incorrect split of javac options", 5, jopts.size());
        assertTrue(jopts.remove("-source"));
        assertTrue(jopts.remove("1.6"));
        assertTrue(jopts.remove("-target"));
        assertTrue(jopts.remove("1.6"));
        assertTrue(jopts.remove("-XprintRounds"));
        assertTrue(jopts.isEmpty());
        assertEquals(1, l.i);
    }

    private static class L extends WsimportListener {
        int i = 0;

        public L() {
        }

        @Override
        public void message(String msg) {
            i++;
        }

    }
}
