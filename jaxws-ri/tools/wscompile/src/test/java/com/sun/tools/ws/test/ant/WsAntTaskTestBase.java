/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.ant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.module.ModuleFinder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author Lukas Jungmann
 */
public abstract class WsAntTaskTestBase extends TestCase {

    protected File projectDir;
    protected File apiDir;
    protected File libDir;
    protected File srcDir;
    protected File buildDir;
    protected File script;
    protected boolean tryDelete = false;

    public abstract String getBuildScript();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        projectDir = new File(System.getProperty("java.io.tmpdir"), getClass().getSimpleName() + "-" + getName());
        apiDir = new File(projectDir, "api");
        libDir = new File(projectDir, "lib");
        srcDir = new File(projectDir, "src");
        buildDir = new File(projectDir, "build");
        assertFalse("project dir exists", projectDir.exists() && projectDir.isDirectory());
        assertTrue("project dir created", projectDir.mkdirs());
        script = copy(projectDir, getBuildScript(), WsAntTaskTestBase.class.getResourceAsStream("resources/" + getBuildScript()));

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (tryDelete) {
            delDirs(apiDir, srcDir, buildDir, libDir);
            script.delete();
            assertTrue("project dir exists", projectDir.delete());
        }
    }

    protected static File copy(File dest, String name, InputStream is) throws FileNotFoundException, IOException {
        return copy(dest, name, is, null);
    }

    protected static File copy(File dest, String name, InputStream is, String targetEncoding)
            throws FileNotFoundException, IOException {
        File destFile = new File(dest, name);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(destFile));
        Writer w = targetEncoding != null ?
                new OutputStreamWriter(os, targetEncoding) : new OutputStreamWriter(os);
        byte[] b = new byte[4096];
        int len = -1;
        while ((len = is.read(b)) > 0) {
            w.write(new String(b), 0, len);
        }
        w.flush();
        w.close();
        is.close();
        return destFile;
    }

    protected static List<String> listDirs(File... dirs) {
        List<String> existingFiles = new ArrayList<>();
        for (File dir : dirs) {
            if (!dir.exists() || dir.isFile()) {
                continue;
            }
            existingFiles.addAll(Arrays.asList(dir.list()));
        }
        return existingFiles;
    }

    protected static void delDirs(File... dirs) {
        for (File dir : dirs) {
            if (!dir.exists()) {
                continue;
            }
            if (dir.isDirectory()) {
                for (File f : dir.listFiles()) {
                    delDirs(f);
                }
                dir.delete();
            } else {
                dir.delete();
            }
        }
    }

    protected boolean isOldJDK() {
        try {
            float version = Float.parseFloat(System.getProperty("java.specification.version"));
            return version < 1.6;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    protected boolean isAntPre18() {
        try {
            Properties p = new Properties();
            p.load(WsAntTaskTestBase.class.getResourceAsStream("/org/apache/tools/ant/version.txt"));
            String vString = p.getProperty("VERSION");
            int version = Integer.parseInt(vString.substring(2, vString.indexOf('.', 2)));
            return version < 8;
        } catch (Exception e) {
            Logger.getLogger(WsAntTaskTestBase.class.getName()).warning("Cannot detect Ant version.");
            return true;
        }
    }

    static boolean is9() {
        return true;
    }
}
