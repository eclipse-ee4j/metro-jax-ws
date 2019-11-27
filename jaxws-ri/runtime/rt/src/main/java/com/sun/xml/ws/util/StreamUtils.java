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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jitendra Kotamraju
 */
public class StreamUtils {

    /*
     * Finds if the stream has some content or not
     *
     * @return null if there is no data
     *         else stream to be used
     */
    public static InputStream hasSomeData(InputStream in) {
        if (in != null) {
            try {
                if (in.available() < 1) {
                    if (!in.markSupported()) {
                        in = new BufferedInputStream(in);
                    }
                    in.mark(1);
                    if (in.read() != -1) {
                        in.reset();
                    } else {
                        in = null;          // No data
                    }
                }
            } catch(IOException ioe) {
                in = null;
            }
        }
        return in;
    }

}
