/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.wscompile;

import com.sun.tools.ws.wscompile.WsimportTool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

import junit.framework.TestCase;

/**
 * Drives wsimport in process, which is what the other wsimport tests do not do - they fork a JVM
 * through Ant, so nothing covers running the tool while com.sun.tools.ws.wscompile is a named
 * module on the module path.
 */
public class WsimportGenerateCodeTest extends TestCase {

    private static final String WSDL_RESOURCE = "/com/sun/tools/ws/test/ant/resources/hello.wsdl";

    /**
     * generateCode() looks GeneratorBase implementations up with ServiceLoader, which fails with a
     * ServiceConfigurationError unless the module declares that it uses the service. The failure
     * comes after the service class has already been written, so it can only be observed by
     * letting the tool run to completion.
     */
    public void testGenerateCodeCompletesInProcess() throws Exception {
        URL wsdl = WsimportGenerateCodeTest.class.getResource(WSDL_RESOURCE);
        assertNotNull("cannot find " + WSDL_RESOURCE + " on the test classpath", wsdl);

        Path out = Files.createTempDirectory("wsimport-generate-code");
        try {
            ByteArrayOutputStream toolOutput = new ByteArrayOutputStream();
            boolean generated;
            try (PrintStream toolStream = new PrintStream(toolOutput)) {
                generated = new WsimportTool(toolStream).run(new String[]{
                        "-quiet",
                        "-extension",
                        "-Xnocompile",
                        "-s", out.toString(),
                        wsdl.toExternalForm()});
            }
            assertTrue("wsimport failed: " + toolOutput, generated);
            assertTrue("no @WebServiceClient class was generated under " + out, generatedServiceClass(out));
        } finally {
            delete(out);
        }
    }

    private static boolean generatedServiceClass(Path root) throws IOException {
        try (Stream<Path> files = Files.walk(root)) {
            return files.filter(Files::isRegularFile)
                    .map(WsimportGenerateCodeTest::read)
                    .anyMatch(source -> source.contains("@WebServiceClient"));
        }
    }

    private static String read(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void delete(Path root) throws IOException {
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException failure) throws IOException {
                if (failure != null) {
                    throw failure;
                }
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
