/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.ant;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Lukas Jungmann
 */
public class WsGenTaskTest extends WsAntTaskTestBase {

    private File pkg;
    private File generated;

    @Override
    public String getBuildScript() {
        return "wsgen.xml";
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        pkg = new File(srcDir, "test");
        generated = new File(projectDir, "generated");
        assertTrue(pkg.mkdirs());
        assertTrue(generated.mkdirs());
    }

    @Override
    protected void tearDown() throws Exception {
        if (tryDelete) {
            delDirs(generated);
        }
        super.tearDown();
    }

    public void testWsGenLockJars() throws IOException, URISyntaxException {
        if (isOldJDK()) {
            Logger.getLogger(WsGenTaskTest.class.getName()).warning("Old JDK - 6+ is required - skipping jar locking test");
            return;
        }
        if (is9()) {
            Logger.getLogger(WsGenTaskTest.class.getName()).warning("New JDK - <9 is required - skipping jar locking test");
            return;
        }
        if (isAntPre18()) {
            Logger.getLogger(WsGenTaskTest.class.getName()).warning("Old Ant - 1.8+ is required - skipping jar locking test");
            return;
        }
        tryDelete = true;
        copy(pkg, "TestWs.java", WsGenTaskTest.class.getResourceAsStream("resources/TestWs.java_"));
        assertEquals(0, AntExecutor.exec(script, apiDir, "wsgen-server", "clean"));
        List<String> files = listDirs(apiDir, libDir);
        assertTrue("Locked jars: " + files, files.isEmpty());
    }

    public void testEncoding() throws IOException, URISyntaxException {
        //UTF-16BE
        String enc = "UTF-16BE";
        copy(pkg, "TestWs.java", WsGenTaskTest.class.getResourceAsStream("resources/TestWs.java_"), enc);
        assertEquals(0, AntExecutor.exec(script, apiDir, "wsgen-server-utf16be"));
        File f = new File(srcDir, "test/jaxws/Hello.java");
        FileInputStream fis = new FileInputStream(f);
        byte[] in = new byte[22];
        fis.read(in);
        fis.close();
        String inStr = new String(in, enc);
        assertTrue("Got: '" + inStr + "'", inStr.contains("package t"));
    }

    public void testInvalidEncoding() throws IOException, URISyntaxException {
        assertEquals(1, AntExecutor.exec(script, apiDir, "wsgen-server-encoding-invalid"));
    }

    public void testMemoryArgs() throws IOException, URISyntaxException {
        copy(pkg, "TestWs.java", WsGenTaskTest.class.getResourceAsStream("resources/TestWs.java_"));
        assertEquals(0, AntExecutor.exec(script, apiDir, "wsgen-server-memory"));
    }

    public void testFork() throws IOException, URISyntaxException {
        copy(pkg, "TestWs.java", WsGenTaskTest.class.getResourceAsStream("resources/TestWs.java_"));
        assertEquals(0, AntExecutor.exec(script, apiDir, "wsgen-fork"));
    }

    public void testAddmodules() throws IOException, URISyntaxException {
        if (!WsAntTaskTestBase.is9()) {
            Logger.getLogger(WsGenTaskTest.class.getName()).warning("Old JDK - >=9 is required - skipping jar test");
            return;
        }
        copy(srcDir, "module-info.java", WsGenTaskTest.class.getResourceAsStream("resources/module-info-ws.java_"));
        File ws = new File(pkg, "ws");
        ws.mkdirs();
        copy(ws, "TestWs2.java", WsGenTaskTest.class.getResourceAsStream("resources/TestWs2.java_"));
        assertEquals(0, AntExecutor.exec(script, apiDir, "wsgen-addmodules"));
    }

    public void testJavac() throws IOException, URISyntaxException {
        copy(pkg, "TestWs.java", WsGenTaskTest.class.getResourceAsStream("resources/TestWs.java_"));
        assertEquals(0, AntExecutor.exec(script, apiDir, "wsgen-javac"));
        //wsgen compiled classes should be valid for java 5
        File f = new File(buildDir, "test/jaxws/Hello.class");
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        assertEquals(0xcafebabe, in.readInt());
        assertEquals(0, in.readUnsignedShort());
        assertEquals(50, in.readUnsignedShort());

        //ws class is compiled by default javac (6+)
        f = new File(srcDir, "test/TestWs.class");
        in = new DataInputStream(new FileInputStream(f));
        assertEquals(0xcafebabe, in.readInt());
        in.readUnsignedShort();
        assertTrue(50 != in.readUnsignedShort());
    }
}
