/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Writes all the source files using the specified Filer.
 *
 * @author WS Development Team
 */
public class FilerCodeWriter extends CodeWriter {

    /** The Filer used to create files. */
    private final Filer filer;

    private Writer w;

    public FilerCodeWriter(Options options) throws IOException {
        this.filer = options.filer;
    }

    @Override
    public OutputStream openBinary(JPackage pkg, String fileName) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

    public Writer openSource(JPackage pkg, String fileName) throws IOException {
        String tmp = fileName.substring(0, fileName.length()-5);
        if (pkg.name() != null && ! "".equals(pkg.name())) {
        	w = filer.createSourceFile(pkg.name() + "." + tmp).openWriter();
        } else {
        	w = filer.createSourceFile(tmp).openWriter();
        }
        return w;
    }


    public void close() throws IOException {
        if (w != null)
            w.close();
        w = null;
    }
}
