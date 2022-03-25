/*
 * Copyright (c) 2012, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.ant;

import com.sun.tools.ws.ant.WsGen2;
import com.sun.tools.ws.ant.WsImport2;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.Assert;
import junit.framework.TestCase;
import org.apache.tools.ant.types.CommandlineJava;

/**
 *
 * @author lukas
 */
public class WsAntToolsTest extends TestCase {

    public WsAntToolsTest(String testName) {
        super(testName);
    }

    public void testWsGenForkedCommand() {
        String method = "setupForkCommand";
        String field = "cmd";
        String arg = "";
        //API jars are somewhere on the classpath
        File tmpDir = new File(System.getProperty("java.io.tmpdir"), "wsi2test");
        WsGen2 wsg2 = new WsGen2();
        wsg2.setFork(true);
        wsg2.setDestdir(new File(tmpDir, "dest"));
        wsg2.setSourcedestdir(new File(tmpDir, "srcDest"));
        CommandlineJava cmd = (CommandlineJava) run(WsGen2.class, wsg2, method, arg, field);
        verifyCommand(cmd.describeCommand());

        //API jars are defined using CLASSPATH environment variable (= System class loader)
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader());
            wsg2 = new WsGen2();
            wsg2.setFork(true);
            wsg2.setDestdir(new File(tmpDir, "dest"));
            wsg2.setSourcedestdir(new File(tmpDir, "srcDest"));
            cmd = (CommandlineJava) run(WsGen2.class, wsg2, method, arg, field);
            verifyCommand(cmd.describeCommand());
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    public void testWsImportForkedCommand() {
        String method = "setupForkCommand";
        String field = "cmd";
        String arg = "";
        //API jars are somewhere on the classpath
        File tmpDir = new File(System.getProperty("java.io.tmpdir"), "wsi2test");
        WsImport2 wsi2 = new WsImport2();
        wsi2.setFork(true);
        wsi2.setDestdir(new File(tmpDir, "dest"));
        wsi2.setSourcedestdir(new File(tmpDir, "srcDest"));
        CommandlineJava cmd = (CommandlineJava) run(WsImport2.class, wsi2, method, arg, field);
        verifyCommand(cmd.describeCommand());

        //API jars are defined using CLASSPATH environment variable (= System class loader)
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader());
            wsi2 = new WsImport2();
            wsi2.setFork(true);
            wsi2.setDestdir(new File(tmpDir, "dest"));
            wsi2.setSourcedestdir(new File(tmpDir, "srcDest"));
            cmd = (CommandlineJava) run(WsImport2.class, wsi2, method, arg, field);
            verifyCommand(cmd.describeCommand());
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    private Object run(Class<?> c, Object i, String method, String arg, String field) {
        runVoidMethod(c, i, method, arg);
        return getField(c, i, field);
    }

    private void verifyCommand(String command) {
        Assert.assertFalse("-Xbootclasspath/p set: " + command, command.contains("-Xbootclasspath/p"));

        String v = System.getProperty("xml.bind-api.version");
        String jar = v != null ? "jakarta.xml.bind-api-" + v + ".jar" : "jakarta.xml.bind-api.jar";
        jar = fixIfSNAPSHOT(jar);
        Assert.assertTrue(jar + " not found " + command, command.contains(jar));

        v = System.getProperty("jaxws-api.version");
        jar = v != null ? "jakarta.xml.ws-api-" + v + ".jar" : "jakarta.xml.ws-api.jar";
        jar = fixIfSNAPSHOT(jar);
        Assert.assertTrue(jar + " not found " + command, command.contains(jar));
    }

    // translate maven timestamps to SNAPSHOT:
    //  jaxb-api-2.3.0-20150602.094817-2.jar
    //    >>
    //  jaxb-api-2.3.0-SNAPSHOT.jar
    private String fixIfSNAPSHOT(String jar) {
        return jar.replaceAll("(.*)-(\\d+\\.\\d+\\.\\d+)\\-?(\\d+\\.\\d+-\\d+).jar", "$1-$2-SNAPSHOT.jar");
    }


    private void runVoidMethod(Class<?> c, Object i, String name, String arg) {
        Method m = null;
        Class parent = c;
        do {
            try {
                m = parent.getDeclaredMethod(name, String.class);
                m.setAccessible(true);
                m.invoke(i, arg);
                return;
            } catch (Throwable t) {
                parent = parent.getSuperclass();
            } finally {
                if (m != null) {
                    m.setAccessible(false);
                }
            }
        } while (m == null && parent.getSuperclass() != null);
        if (m == null) {
            Assert.fail("cannot find method: '" + name + "'");
        }
    }

    private Object getField(Class<?> c, Object i, String name) {
        Field f = null;
        Class parent = c.getSuperclass();
        do {
            try {
                f = parent.getDeclaredField(name);
                f.setAccessible(true);
                return f.get(i);
            } catch (Throwable t) {
                parent = parent.getSuperclass();
            } finally {
                if (f != null) {
                    f.setAccessible(false);
                }
            }
        } while (f == null && parent.getSuperclass() != null);
        if (f == null) {
            Assert.fail("cannot find field: '" + name + "'");
        }
        return null;
    }
}
