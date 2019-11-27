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

import com.sun.xml.ws.streaming.XMLReaderException;
import com.sun.xml.ws.streaming.XMLStreamReaderException;

import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

public class FastInfosetUtil {

    /**
     * Returns the FI parser allocated for this thread.
     */
    public static XMLStreamReader createFIStreamReader(InputStream in) {
        // Check if compatible implementation of FI was found
        if (FastInfosetReflection.fiStAXDocumentParser_new == null) {
            throw new XMLReaderException("fastinfoset.noImplementation");
        }

        try {
            // Do not use StAX pluggable layer for FI
            Object sdp = FastInfosetReflection.fiStAXDocumentParser_new.newInstance();
            FastInfosetReflection.fiStAXDocumentParser_setStringInterning.invoke(sdp, Boolean.TRUE);
            FastInfosetReflection.fiStAXDocumentParser_setInputStream.invoke(sdp, in);
            return (XMLStreamReader) sdp;
        } catch (Exception e) {
            throw new XMLStreamReaderException(e);
        }
    }

}
