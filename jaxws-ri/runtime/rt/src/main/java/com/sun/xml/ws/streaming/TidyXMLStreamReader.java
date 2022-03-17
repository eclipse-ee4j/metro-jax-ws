/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.streaming;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.util.xml.XMLStreamReaderFilter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import jakarta.xml.ws.WebServiceException;
import java.io.Closeable;
import java.io.IOException;

/**
 * Wrapper over XMLStreamReader. It will be used primarily to
 * clean up the resources such as closure on InputStream/Reader.
 *
 * @author Vivek Pandey
 */
public class TidyXMLStreamReader extends XMLStreamReaderFilter {
    private final Closeable closeableSource;

    public TidyXMLStreamReader(@NotNull XMLStreamReader reader, @Nullable Closeable closeableSource) {
        super(reader);
        this.closeableSource = closeableSource;
    }

    @Override
    public void close() throws XMLStreamException {
        super.close();
        try {
            if(closeableSource != null)
                closeableSource.close();
        } catch (IOException e) {
            throw new WebServiceException(e);
        }
    }
}
