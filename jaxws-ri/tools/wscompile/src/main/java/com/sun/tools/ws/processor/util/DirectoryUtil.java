/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.util;

import com.sun.tools.ws.processor.generator.GeneratorException;
import com.sun.tools.ws.util.ClassNameInfo;

import java.io.File;
import java.io.IOException;

/**
 * Util provides static utility methods used by other wscompile classes.
 *
 * @author WS Development Team
 */
public final class DirectoryUtil  {

    private DirectoryUtil() {}

    public static File getOutputDirectoryFor(String theClass, File rootDir) throws GeneratorException {

        File outputDir = null;
        String packagePath = null;
        String packageName = ClassNameInfo.getQualifier(theClass);
        if (packageName != null && !packageName.isEmpty()) {
            packagePath = packageName.replace('.', File.separatorChar);
        }

        // Do we have a root directory?
        if (rootDir != null) {

            // Yes, do we have a package name?
            if (packagePath != null) {

                // Yes, so use it as the root. Open the directory...
                outputDir = new File(rootDir, packagePath);

                // Make sure the directory exists...
                ensureDirectory(outputDir);
            } else {

                // Default package, so use root as output dir...
                outputDir = rootDir;
            }
        } else {

            // No root directory. Get the current working directory...
            String workingDirPath = System.getProperty("user.dir");
            File workingDir = new File(workingDirPath);

            // Do we have a package name?
            if (packagePath == null) {

                // No, so use working directory...
                outputDir = workingDir;
            } else {

                // Yes, so use working directory as the root...
                outputDir = new File(workingDir, packagePath);

                // Make sure the directory exists...
                ensureDirectory(outputDir);
            }
        }

        // Finally, return the directory...
        return outputDir;
    }

    public static String getRelativePathfromCommonBase(File file, File base) throws IOException {
        String basePath = base.getCanonicalPath();
        String filePath = file.getCanonicalPath();
        return filePath.substring(basePath.length());       

    }

    private static void ensureDirectory(File dir) throws GeneratorException {
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created || !dir.exists()) {
                throw new GeneratorException("generator.cannot.create.dir",
                    dir.getAbsolutePath());
            }
        }
    }
}

