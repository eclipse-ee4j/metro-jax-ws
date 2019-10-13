/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wscompile;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JPackage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import javax.tools.JavaFileObject;

/**
 * Writes all the source files using the specified Filer.
 *
 * @author WS Development Team
 */
public class FilerCodeWriter extends CodeWriter {

    /** The Filer used to create files. */
    private final Options options;

    private Writer w;

    public FilerCodeWriter(Options options) throws IOException {
        this.options = options;
    }

    @Override
    public OutputStream openBinary(JPackage pkg, String fileName) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Writer openSource(JPackage pkg, String fileName) throws IOException {
        String tmp = fileName.substring(0, fileName.length()-5);
        String name = isDefaultPackage(pkg)
                ? tmp
                : pkg.name() + "." + tmp;
        JavaFileObject jfo = options.filer.createSourceFile(name);
        options.addGeneratedFile(jfo);
        w = jfo.openWriter();
        return w;
    }


    @Override
    public void close() throws IOException {
        if (w != null)
            w.close();
        w = null;
    }

    private static boolean isDefaultPackage(JPackage pkg) {
        return pkg.name() == null || "".equals(pkg.name());
    }
}
