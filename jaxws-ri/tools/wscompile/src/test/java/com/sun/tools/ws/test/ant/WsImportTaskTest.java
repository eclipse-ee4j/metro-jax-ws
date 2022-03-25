/*
 * Copyright (c) 2011, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.ant;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Lukas Jungmann
 */
public class WsImportTaskTest extends WsAntTaskTestBase {

    private File wsdl;
    private File pkg;
    private File metainf;
   
    @Override
    public String getBuildScript() {
        return "wsimport.xml";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        pkg = new File(srcDir, "test");
        metainf = new File(buildDir, "META-INF");
        wsdl = copy(projectDir, "hello.wsdl", WsImportTaskTest.class.getResourceAsStream("resources/hello.wsdl"));
        assertTrue(pkg.mkdirs());
        assertTrue(metainf.mkdirs());
    }

    public void testEncoding() throws IOException {
        //this fails because one task uses invalid attributte
        assertEquals(1, AntExecutor.exec(script, "wsimport-client-encoding"));
        //UTF-8
        File f = new File(buildDir, "client/utf8/Hello.java");
        FileInputStream fis = new FileInputStream(f);
        byte[] in = new byte[11];
        fis.read(in);
        fis.close();
        String inStr = new String(in, "UTF-8");
        assertTrue("Got: '" + inStr + "'", inStr.contains("package c"));

        //UTF-16LE
        f = new File(buildDir, "client/utf16LE/Hello.java");
        fis = new FileInputStream(f);
        in = new byte[22];
        fis.read(in);
        fis.close();
        inStr = new String(in, "UTF-16LE");
        assertTrue("Got: '" + inStr + "'", inStr.contains("package c"));

        //UTF-74
        assertFalse(new File(buildDir, "client/invalid").exists());
    }

    public void testPlugin() throws IOException {
        assertEquals(0, AntExecutor.exec(script, "wsimport-plugin"));
        File f = new File(buildDir, "test/Hello_Service.java");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        boolean found = false;
        while ((line = br.readLine()) != null) {
            if (line.contains("@Generated(value = \"com.sun.tools.ws.wscompile.WsimportTool\", ")) {
                found = true;
                break;
            }
        }
        br.close();
        assertFalse("Plugin invoked", found);
        f = new File(srcDir, "test/Hello_Service.java");
        br = new BufferedReader(new FileReader(f));
        while ((line = br.readLine()) != null) {
            if (line.contains("@Generated(value = \"com.sun.tools.ws.wscompile.WsimportTool\", ")) {
                found = true;
                break;
            }
        }
        br.close();
        assertTrue("Plugin not invoked", found);
    }

    public void testFork() throws FileNotFoundException, IOException {
        copy(pkg,  "MyExtension.java", WsImportTaskTest.class.getResourceAsStream("resources/MyExtension.java_"));
        copy(buildDir,  "META-INF/com.sun.tools.ws.api.wsdl.TWSDLExtensionHandler", WsImportTaskTest.class.getResourceAsStream("resources/TWSDLExtensionHandler"));
        assertEquals(0, AntExecutor.exec(script, "wsimport-fork"));
    }

    public void testJavac() throws IOException {
        assertEquals(0, AntExecutor.exec(script, "wsimport-javac"));
        //wsimport compiled classes should be valid for java 5
        File f = new File(buildDir, "test/types/HelloType.class");
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        assertEquals(0xcafebabe, in.readInt());
        assertEquals(0, in.readUnsignedShort());
        assertEquals(52, in.readUnsignedShort());

        f = new File(buildDir, "test/Hello.class");
        in = new DataInputStream(new FileInputStream(f));
        assertEquals(0xcafebabe, in.readInt());
        assertEquals(0, in.readUnsignedShort());
        assertEquals(52, in.readUnsignedShort());
    }

}
