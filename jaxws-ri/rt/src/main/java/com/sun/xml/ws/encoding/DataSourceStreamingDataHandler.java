/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding;

import javax.activation.DataSource;
import java.io.*;

import com.sun.xml.ws.developer.StreamingDataHandler;

/**
 * @author Jitendra Kotamraju
 */
public class DataSourceStreamingDataHandler extends StreamingDataHandler {

    public DataSourceStreamingDataHandler(DataSource ds) {
        super(ds);
    }

    @Override
    public InputStream readOnce() throws IOException {
        return getInputStream();
    }

    @Override
    public void moveTo(File file) throws IOException {
        InputStream in = getInputStream();
        OutputStream os = new FileOutputStream(file);
        try {
            byte[] temp = new byte[8192];
            int len;
            while((len=in.read(temp)) != -1) {
                os.write(temp, 0, len);
            }
            in.close();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    @Override
    public void close() throws IOException {
        // nothing to do here
    }

}
