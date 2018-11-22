/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.ant;

import com.sun.tools.ws.ant.AnnotationProcessingTask;
import java.io.File;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;

import junit.framework.TestCase;

public class AptTaskTest extends TestCase {

    public AptTaskTest(String testName) {
        super(testName);
    }

    public void testMultipleWs() throws Exception {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"), "testMultipleWs");
        tmpDir.mkdirs();
        File srcDir = new File(tmpDir, "src");
        srcDir.mkdirs();
        File outDir = new File(tmpDir, "out");
        outDir.mkdirs();
        WsAntTaskTestBase.copy(srcDir, "WSStyle1.java", AptTaskTest.class.getResourceAsStream("resources/WSStyle1.java_"));
        WsAntTaskTestBase.copy(srcDir, "WSStyle2.java", AptTaskTest.class.getResourceAsStream("resources/WSStyle2.java_"));

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader());

            Project project = new Project();
            project.setBasedir(tmpDir.getPath());
            project.setSystemProperties();

            DefaultLogger dl = new DefaultLogger();
            dl.setOutputPrintStream(System.out);
            dl.setErrorPrintStream(System.err);
            dl.setMessageOutputLevel(5);
            project.addBuildListener(dl);

            AnnotationProcessingTask apt = new AnnotationProcessingTask();
            apt.setProject(project);
            apt.setDestdir(outDir);
            apt.setSourceDestDir(outDir);

            Path sourcepath = new Path(project);
            sourcepath.setLocation(srcDir);
            FileSet jwsFS = new FileSet();
            jwsFS.setDir(tmpDir);
            jwsFS.createInclude().setName("src");
            sourcepath.addFileset(jwsFS);

            apt.setDebug(true);

            apt.setSrcdir(sourcepath);
            Path cp = new Path(project);
            cp.setPath(System.getProperty("java.class.path") + File.pathSeparator + System.getProperty("jdk.module.path"));
            apt.setClasspath(cp);
            apt.execute();
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println("DONE");
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

}
