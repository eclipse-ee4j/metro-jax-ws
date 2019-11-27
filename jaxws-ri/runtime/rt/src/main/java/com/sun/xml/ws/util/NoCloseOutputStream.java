/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * {@link OutputStream} that cannot be closed.
 * 
 * @author Kohsuke Kawaguchi
 */
public class NoCloseOutputStream extends FilterOutputStream {
    public NoCloseOutputStream(OutputStream out) {
        super(out);
    }

    @Override
    public void close() throws IOException {
        // Intentionally left empty. use closeOutput() to close
    }

    public void doClose() throws IOException {
        super.close();
    }
}
