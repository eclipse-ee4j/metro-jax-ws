/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wscompile;

import com.sun.codemodel.JPackage;
import com.sun.codemodel.writer.FileCodeWriter;

import java.io.File;
import java.io.IOException;

/**
 * {@link FileCodeWriter} implementation that notifies
 * JAX-WS about newly created files.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public class WSCodeWriter extends FileCodeWriter {
    private final Options options;

    public WSCodeWriter( File outDir, Options options) throws IOException {
        super(outDir, options.encoding);
        this.options = options;
    }

    @Override
    protected File getFile(JPackage pkg, String fileName ) throws IOException {
        File f = super.getFile(pkg, fileName);

        options.addGeneratedFile(f);
        // we can't really tell the file type, for we don't know
        // what this file is used for. Fortunately,
        // FILE_TYPE doesn't seem to be used, so it doesn't really
        // matter what we set.

        return f;
    }
}
